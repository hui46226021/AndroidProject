package com.sh.shprojectdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.sh.shprojectdemo.R;
import com.sh.zsh.code.baidumap_sdk.BaiduMapHelper;
import com.sh.zsh.code.baidumap_sdk.listenner.MyLocationListenner;
import com.sh.zsh.code.baidumap_sdk.utils.RadarUtils;
import com.sh.zsh.code.baidumap_sdk.view.JrMapView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhush on 2017/2/24
 * E-mail 405086805@qq.com
 * PS  雷达地图
 */

public class RadarActivity extends AppCompatActivity implements RadarSearchListener {

    @InjectView(R.id.bmapView)
    JrMapView bmapView;
    @InjectView(R.id.up)
    Button up;
    @InjectView(R.id.search)
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        ButterKnife.inject(this);
//        BaiduMapHelper.init(getApplication());
        BaiduMapHelper.startLocation();
        // 开启定位图层
        bmapView.getMap().setMyLocationEnabled(true);
        loadLocationData();
        bmapView.locationView(MyLocationConfiguration.LocationMode.FOLLOWING);
    }

    @OnClick({R.id.up,R.id.search,R.id.normal,R.id.following,R.id.compass})
    void pageOnClick(View v){
        switch (v.getId()){
            case R.id.up:
                RadarUtils.uploadInfo(MyLocationListenner.newInstance().longitude, MyLocationListenner.newInstance().latitude, "10045", "第五个用户", this);
                break;
            case R.id.search:
                RadarUtils.searchNearby(1,this);
                break;
            case R.id.normal://正常定位
                bmapView.locationView(MyLocationConfiguration.LocationMode.NORMAL);
                break;
            case R.id.following://跟随定位
                bmapView.locationView(MyLocationConfiguration.LocationMode.FOLLOWING);
                break;
            case R.id.compass://罗盘定位
                bmapView.locationView(MyLocationConfiguration.LocationMode.COMPASS);
                break;
        }
    }

    @Override
    public void onGetNearbyInfoList(RadarNearbyResult radarNearbyResult, RadarSearchError radarSearchError) {
// TODO Auto-generated method stub
        if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
            parseResultToMap(radarNearbyResult);
        } else {
            //获取失败
            Toast.makeText(RadarActivity.this, "查询周边失败", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onGetUploadState(RadarSearchError radarSearchError) {
        // TODO Auto-generated method stub
        if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
            //上传成功
            Toast.makeText(RadarActivity.this, "单次上传位置成功", Toast.LENGTH_LONG)
                    .show();
        } else {
            //上传失败
            Toast.makeText(RadarActivity.this, "单次上传位置失败", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onGetClearInfoState(RadarSearchError radarSearchError) {
// TODO Auto-generated method stub
        if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
            //清除成功
            Toast.makeText(RadarActivity.this, "清除位置成功", Toast.LENGTH_LONG)
                    .show();
        } else {
            //清除失败
            Toast.makeText(RadarActivity.this, "清除位置失败", Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * 更新结果地图
     *
     * @param res
     */
    public void parseResultToMap(RadarNearbyResult res) {
        bmapView.clear();
        if (res != null && res.infoList != null && res.infoList.size() > 0) {
            for (int i = 0; i < res.infoList.size(); i++) {
                LatLng pt = res.infoList.get(i).pt;

                Bundle des = new Bundle();

                    des.putString("des", res.infoList.get(i).comments);

                bmapView.addTagging(pt.longitude,pt.latitude,R.mipmap.tag_icon,false,des);
            }
        }
    }
   boolean isFirstLoc =true;
    private void loadLocationData(){
        MyLocationListenner.newInstance().setLocationViewData(new MyLocationListenner.LocationViewData() {
            @Override
            public void setData(BDLocation bdLocation) {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(bdLocation.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(100).latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude()).build();
                bmapView.getMap().setMyLocationData(locData);

                if (isFirstLoc) {
                    isFirstLoc = false;
                    LatLng ll = new LatLng(bdLocation.getLatitude(),
                            bdLocation.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    bmapView.getMap().animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }

            }
        });
    }

    @Override
    protected void onPause() {
        bmapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        bmapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        BaiduMapHelper.stopLocation();
        // 关闭定位图层
        bmapView.getMap().setMyLocationEnabled(false);
        bmapView.onDestroy();
        bmapView = null;
        MyLocationListenner.newInstance().setLocationViewData(null);
        super.onDestroy();
    }
}
