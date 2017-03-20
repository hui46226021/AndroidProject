package com.sh.zsh.code.baidumap_sdk;

import android.app.Application;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.sh.zsh.code.baidumap_sdk.listenner.MyLocationListenner;

/**
 * Created by zhush on 2017/2/23.
 * E-mail 405086805@qq.com
 * PS
 */
public class BaiduMapHelper {
    /**
     * 初始化 百度地图
     * @param application
     */
    static  LocationClient mLocationClient = null;
    public static void init(Application application){
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(application);

        //初始化定位  定位信息  可以 到MyLocationListenner取 需要到值 都写成 static 只用就行  定位是一直定位 每次获取都是当前的位置
        BDLocationListener myListener = MyLocationListenner.newInstance();
        mLocationClient = new LocationClient(application);
        //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
    }

    public static void initLocation( ){

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    public static void startLocation(){
        initLocation();
        mLocationClient.start();
    }
    public static void stopLocation(){
        mLocationClient.stop();
    }
}
