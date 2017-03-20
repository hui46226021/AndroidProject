package com.sh.zsh.code.baidumap_sdk.utils;

import android.app.Activity;
import android.widget.Toast;

import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.baidu.mapapi.radar.RadarUploadInfo;
import com.sh.zsh.code.baidumap_sdk.listenner.MyLocationListenner;

import static vi.com.gdi.bgl.android.java.EnvDrawText.pt;

/**
 * Created by zhush on 2017/2/24.
 * E-mail 405086805@qq.com
 * PS 周边雷达
 */
public class RadarUtils {


    /**
     * 上传位置信息
     * @param userid
     * @param userMessage
     * @param var1
     */
    public static void uploadInfo(double longitude, double latitude,String userid,String userMessage,RadarSearchListener var1){
        RadarSearchManager mManager = RadarSearchManager.getInstance();
        mManager.addNearbyInfoListener(var1);
        //周边雷达设置用户身份标识，id为空默认是设备标识
        mManager.setUserID(userid);
        //上传位置
        RadarUploadInfo info = new RadarUploadInfo();
        info.comments = userMessage;
        //定义Maker坐标点
        LatLng point = new LatLng(latitude, longitude);
        info.pt = point;
        mManager.uploadInfoRequest(info);
    }


    /**
     * 搜索附近
     * @param page
     * @param radarSearchListener
     */
    public static void searchNearby(int page,RadarSearchListener radarSearchListener){
        LatLng  pt = new LatLng(MyLocationListenner.newInstance().latitude,MyLocationListenner.newInstance().longitude);

        RadarSearchManager mManager = RadarSearchManager.getInstance();
        mManager.addNearbyInfoListener(radarSearchListener);
        //构造请求参数，其中centerPt是自己的位置坐标
        RadarNearbySearchOption option = new RadarNearbySearchOption().centerPt(pt).pageNum(page).radius(2000);
        //发起查询请求
        mManager.nearbyInfoRequest(option);

    }



}
