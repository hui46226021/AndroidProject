package com.sh.zsh.code.baidumap_sdk.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.baidu.mapapi.radar.RadarUploadInfo;
import com.sh.zsh.code.baidumap_sdk.R;
import com.sh.zsh.code.baidumap_sdk.listenner.MyLocationListenner;

import java.util.Map;


/**
 * Created by zhush on 2017/3/19.
 * E-mail 405086805@qq.com
 * PS 封装地图  为了隔离第三方SDK 便于更换
 * setMapDisplayType   设置地图显示类型
 * openTraffic         开启交通地图
 * setBaiduHeatMapEnabled  开启热力地图
 * addTagging           在地图标注 res 支援图片   view 视图
 * addText              文字覆盖
 * removeOverlay        删除点
 * perfomZoom           地图缩放
 * perfomRotate         旋转地图
 * clear                清楚地图上的点
 * locationView         定位图层
 */
public class JrMapView extends FrameLayout {
    TextureMapView mapView;
    BaiduMap baiduMap;
    public JrMapView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.jr_map, this);
        mapView = (TextureMapView) findViewById(R.id.bmap_view);
        baiduMap = mapView.getMap();
    }

    public JrMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        try {
            LayoutInflater.from(context).inflate(R.layout.jr_map, this);
            View view = (TextureMapView) findViewById(R.id.bmap_view);
            mapView = (TextureMapView) view;
        } catch (Exception e) {
            e.printStackTrace();
        }
        baiduMap = mapView.getMap();
    }

    /**
     * 获取地图
     * @return
     */
    public BaiduMap getMap(){
        if(mapView!=null){
            return mapView.getMap();
        }
        return null;
    }

    /**
     * 设置地图显示类型
     *
     * @param type 普通地图  BaiduMap.MAP_TYPE_NORMAL
     *             卫星地图  BaiduMap.MAP_TYPE_SATELLITE
     *             空白地图  BaiduMap.MAP_TYPE_NONE（基础地图瓦片将不会被渲染。在地图类型中设置为NONE，
     *             将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。）
     */
    public  void setMapDisplayType(int type) {
        baiduMap.setMapType(type);
    }


    /**
     * 开启交通地图
     *
     */
    public  void openTraffic() {
        //开启交通图
        baiduMap.setTrafficEnabled(true);
    }

    /**
     * 开启热力地图
     *
     */
    public  void setBaiduHeatMapEnabled() {
        baiduMap.setBaiduHeatMapEnabled(true);
    }

    /**
     * 清空地图上的点
     */
    public  void clear() {
        baiduMap.clear();
    }

    /**
     * 用一个图片正地图上打点
     * @param longitude  经度
     * @param latitude  纬度
     * @param res 打点图标
     * @param drag   是否可拖动
     * @param des   携带的信息
     */
    public  Overlay addTagging( double longitude, double latitude, int res, boolean drag, Bundle des) {
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(res);
        return addTagging(longitude,latitude,bitmap,drag,des);
    }

    /**
     * 用一个视图正地图上打点
     * @param longitude  经度
     * @param latitude  纬度
     * @param bitmap   打点视图
     * @param drag   是否可拖动
     */
    public  Overlay addTagging(double longitude, double latitude, BitmapDescriptor bitmap, boolean drag,Bundle des){
        //定义Maker坐标点
        LatLng point = new LatLng(longitude, latitude);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap).zIndex(0).period(10).alpha(0.95f);

        //在地图上添加Marker，并显示
        MarkerOptions ooD =((MarkerOptions)option);
        if(drag){
            ooD.draggable(true);  //设置手势拖拽
        }

//        if (animationBox.isChecked()) {
        // 生长动画
        ooD.animateType(MarkerOptions.MarkerAnimateType.grow);
//        }

        if(des!=null){
            ooD.extraInfo(des);
        }

        return   baiduMap.addOverlay(option);
    }

    /**
     * 用一个视图正地图上打点
     * @param longitude  经度
     * @param latitude  纬度
     * @param view   打点视图
     * @param drag   是否可拖动
     */
    public  Overlay addTagging(double longitude, double latitude, View view, boolean drag, Bundle des) {
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
        return addTagging(longitude,latitude,bitmap,drag,des);
    }

    /**
     * 文字覆盖
     * @param longitude
     * @param latitude
     * @param text
     */
    public  Overlay addText( double longitude, double latitude,String text){
        //定义文字所显示的坐标点
        LatLng llText = new LatLng(longitude, latitude);
//构建文字Option对象，用于在地图上添加文字
        OverlayOptions textOption = new TextOptions()
                .bgColor(0xAAFFFF00)
                .fontSize(24)
                .fontColor(0xFFFF00FF)
                .text(text)
                .rotate(-30)
                .position(llText);


        //在地图上添加该文字对象并显示
        return   baiduMap.addOverlay(textOption);
    }

    /**
     * 删除点坐标
     * @param overlay
     */
    public  void removeOverlay(Overlay overlay){
        overlay.remove();
    }


    /**
     * 地图缩放
     * @param level
     */
    public  void perfomZoom(int level){
        if(level<22&&level>0){
            MapStatusUpdate u = MapStatusUpdateFactory.zoomTo((float) level);
            baiduMap.animateMapStatus(u);
        }else {
            new RuntimeException("缩放级别是0到21 之间");
        }

    }


    /**
     * 旋转地图
     * @param rotateAngle
     */
    public  void perfomRotate(int rotateAngle){
        if(rotateAngle>-180&&rotateAngle<180){
            MapStatus ms = new MapStatus.Builder(baiduMap.getMapStatus()).rotate(rotateAngle).build();
            MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
            baiduMap.animateMapStatus(u);
        }else {
            new RuntimeException("请输入正确的旋转角度");
        }

    }

    /**
     * 定位图层
     * @param model
     */

    public  void locationView(MyLocationConfiguration.LocationMode model){

        BitmapDescriptor mCurrentMarker =null;
//        if(res!=0){
//            // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
//             mCurrentMarker = BitmapDescriptorFactory
//                    .fromResource(res);
//        }

        baiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        model, true, mCurrentMarker));



    }





    public void onDestroy() {

        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();

    }


    public void onResume() {
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }


    public void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
}