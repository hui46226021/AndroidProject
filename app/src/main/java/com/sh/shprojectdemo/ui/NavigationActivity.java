package com.sh.shprojectdemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.sh.shprojectdemo.R;
import com.sh.zsh.code.baidumap_sdk.BaiduMapHelper;
import com.sh.zsh.code.baidumap_sdk.listenner.MyLocationListenner;
import com.sh.zsh.code.baidumap_sdk.view.JrMapView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NavigationActivity extends AppCompatActivity {

    @InjectView(R.id.bmapView)
    JrMapView bmapView;
    private String mSDCardPath = null;
    public static List<Activity> activityList = new LinkedList<Activity>();
    private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";
    public static final String ROUTE_PLAN_NODE = "routePlanNode";

    double toLongitude;
    double toLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.inject(this);
        intiLocation();
        initNavigation();
    }


    @OnClick(R.id.go)
    void onGo() {
        if (BaiduNaviManager.isNaviInited()) {
            routeplanToNavi();
        }
    }

    /**
     * 初始化定位
     */
    void intiLocation() {
        BaiduMapHelper.startLocation();
        // 开启定位图层
        bmapView.getMap().setMyLocationEnabled(true);
        loadLocationData();
        bmapView.locationView(MyLocationConfiguration.LocationMode.NORMAL);


        bmapView.getMap()
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.NORMAL, true, null));



        bmapView.getMap().setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            /**
             * 单击地图
             */
            public void onMapClick(LatLng point) {
                Toast.makeText(NavigationActivity.this,"当前点击"+point.latitude+","+point.longitude,Toast.LENGTH_SHORT).show();
                toLatitude = point.latitude;
                toLongitude = point.longitude;
                bmapView.addTagging( point.latitude,point.longitude, R.mipmap.tag_icon, false,null);
            }

            /**
             * 单击地图中的POI点
             */
            public boolean onMapPoiClick(MapPoi poi) {

                return false;
            }
        });

    }

    /**
     * 初始化导航
     */
    void initNavigation() {
        activityList.add(this);
        // 打开log开关
        BNOuterLogUtil.setLogSwitcher(true);


        if (initDirs()) {
            initNavi();
        }
    }

    String authinfo = null;

    private void initNavi() {

        BNOuterTTSPlayerCallback ttsCallback = null;

        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                NavigationActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(NavigationActivity.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }

            public void initSuccess() {
                Toast.makeText(NavigationActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                initSetting();
            }

            public void initStart() {
                Toast.makeText(NavigationActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
            }

            public void initFailed() {
                Toast.makeText(NavigationActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }


        }, null, ttsHandler, ttsPlayStateListener);

    }

    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
            showToastMsg("TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
            showToastMsg("TTSPlayStateListener : TTS play start");
        }
    };
    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    showToastMsg("Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void initSetting() {
        // 设置是否双屏显示
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        // 设置导航播报模式
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // 是否开启路况
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }


    boolean isFirstLoc = true;

    private void loadLocationData() {
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

                bmapView.addTagging(bdLocation.getLatitude(),bdLocation.getLongitude(), R.mipmap.icon_geo, false,null);


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
        MyLocationListenner.newInstance().setLocationViewData(null);
        bmapView = null;
        super.onDestroy();
    }

    public void showToastMsg(final String msg) {
        NavigationActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(NavigationActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void routeplanToNavi() {


        BNRoutePlanNode sNode = new BNRoutePlanNode(MyLocationListenner.newInstance().longitude, MyLocationListenner.newInstance().latitude,
                MyLocationListenner.newInstance().addr, null, BNRoutePlanNode.CoordinateType.BD09LL);
        BNRoutePlanNode eNode = new BNRoutePlanNode(toLongitude,toLatitude, "也不知道是哪", null, BNRoutePlanNode.CoordinateType.BD09LL);


        List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
        list.add(sNode);
        list.add(eNode);
        BaiduNaviManager.getInstance().launchNavigator(this, list, 1, true, new DemoRoutePlanListener(sNode));

    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
			/*
			 * 设置途径点以及resetEndNode会回调该接口
			 */

            for (Activity ac : activityList) {

                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {

                    return;
                }
            }
            Intent intent = new Intent(NavigationActivity.this, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(NavigationActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }
}
