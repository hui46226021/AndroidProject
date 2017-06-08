package com.sh.shprojectdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.model.LatLng;
import com.sh.shprojectdemo.R;
import com.sh.zsh.code.baidumap_sdk.clusterutil.clustering.Cluster;
import com.sh.zsh.code.baidumap_sdk.clusterutil.clustering.ClusterItem;
import com.sh.zsh.code.baidumap_sdk.clusterutil.clustering.ClusterManager;
import com.sh.zsh.code.baidumap_sdk.view.JrMapView;
import com.sh.zsh.code.baidumap_sdk.view.ThumbnailView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity {


    @InjectView(R.id.bmapView)
    JrMapView jrMapView;
    @InjectView(R.id.enlarge)
    Button enlarge;
    @InjectView(R.id.narrow)
    Button narrow;
    @InjectView(R.id.rotate)
    Button rotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
        ButterKnife.inject(this);
        //设置 地图显示类型 普通 卫星 空白
        jrMapView.setMapDisplayType(BaiduMap.MAP_TYPE_NORMAL);
        //开启交通地图
//        MapViewUtils.openTraffic(mMapView);
        //开启热力地图
//        MapViewUtils.setBaiduHeatMapEnabled(mMapView);
        //地图打点
        jrMapView.addTagging(39.963175, 116.400244, R.mipmap.tag_icon, false, null);
        jrMapView.addText(39.8847290000, 116.200244, "引爆点");
        ThumbnailView thumbnailView = new ThumbnailView(this);
        thumbnailView.setImageRes(R.mipmap.timg);
        jrMapView.addTagging(39.8247290000, 116.2884170000, thumbnailView, true, null);
        addTogether();

        jrMapView.getMap().setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            /**
             * 单击地图
             */
            public void onMapClick(LatLng point) {
                Toast.makeText(MapActivity.this, "当前点击" + point.latitude + "," + point.longitude, Toast.LENGTH_SHORT).show();
            }

            /**
             * 单击地图中的POI点
             */
            public boolean onMapPoiClick(MapPoi poi) {
                Toast.makeText(MapActivity.this, "当前点击" + poi.getName() + "," + poi.getUid(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }

    int rotateAngle;//旋转角度
    int zoomlevel = 10;//缩放级别

    @OnClick({R.id.enlarge, R.id.narrow, R.id.rotate})
    void pageOnClick(View v) {
        switch (v.getId()) {
            case R.id.enlarge:
                if (zoomlevel == 21) {
                    Toast.makeText(this, "已经是最大", Toast.LENGTH_SHORT).show();
                } else {
                    zoomlevel = zoomlevel + 1;
                    jrMapView.perfomZoom(zoomlevel);
                }
                break;
            case R.id.narrow:
                if (zoomlevel == 0) {
                    Toast.makeText(this, "已经是最小", Toast.LENGTH_SHORT).show();
                } else {
                    zoomlevel = zoomlevel - 1;
                    jrMapView.perfomZoom(zoomlevel);
                }

                break;
            case R.id.rotate:
                if (rotateAngle == 180) {
                    rotateAngle = -150;
                } else {
                    rotateAngle = rotateAngle + 30;

                }
                jrMapView.perfomRotate(rotateAngle);
                break;
        }
    }

    /**
     * 添加 聚合坐标
     */
    public void addTogether() {
// 初始化点聚合管理类
        ClusterManager mClusterManager = new ClusterManager<>(this, jrMapView.getMap());

        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        jrMapView.getMap().setOnMapStatusChangeListener(mClusterManager);
        // 设置maker点击时的响应
        jrMapView.getMap().setOnMarkerClickListener(mClusterManager);

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                Toast.makeText(MapActivity.this,
                        "有" + cluster.getSize() + "个点", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
                Toast.makeText(MapActivity.this,
                        "点击单个Item", Toast.LENGTH_SHORT).show();

                return false;
            }
        });
// 向点聚合管理类中添加Marker实例
        // 添加Marker点
        LatLng llA = new LatLng(39.963175, 116.400244);
        LatLng llB = new LatLng(39.942821, 116.369199);
        LatLng llC = new LatLng(39.939723, 116.425541);
        LatLng llD = new LatLng(39.906965, 116.401394);
        LatLng llE = new LatLng(39.956965, 116.331394);
        LatLng llF = new LatLng(39.886965, 116.441394);
        LatLng llG = new LatLng(39.996965, 116.411394);
        List<MyItem> items = new ArrayList<MyItem>();
        items.add(new MyItem(llA));
        items.add(new MyItem(llB));
        items.add(new MyItem(llC));
        items.add(new MyItem(llD));
        items.add(new MyItem(llE));
        items.add(new MyItem(llF));
        items.add(new MyItem(llG));

        mClusterManager.addItems(items);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        jrMapView.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        jrMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        jrMapView.onPause();
    }


    /**
     * 每个Marker点，包含Marker点坐标以及图标
     */
    public class MyItem implements ClusterItem {
        private final LatLng mPosition;

        public MyItem(LatLng latLng) {
            mPosition = latLng;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public BitmapDescriptor getBitmapDescriptor() {
            ThumbnailView thumbnailView = new ThumbnailView(MapActivity.this);
            thumbnailView.setImageRes(R.mipmap.timg);

            return BitmapDescriptorFactory
                    .fromView(thumbnailView);
        }
    }

}
