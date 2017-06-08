package com.sh.zsh.code.baidumap_sdk.listenner;

/**
 * Created by zhush on 2017/2/24.
 * E-mail 405086805@qq.com
 * PS
 */

import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

/**
 * 定位SDK监听函数
 */
public class MyLocationListenner implements BDLocationListener {


    public  double latitude;//经度
    public  double longitude;//纬度
    public  String addr;//常量

    public  String city;//城市

    public  String county;//县街道
    public  String street;//县街道

    public  BDLocation bdLocation;

    LocationViewData locationViewData;


    public static MyLocationListenner myLocationListenner;

     public static MyLocationListenner newInstance() {

        if(myLocationListenner==null){
            myLocationListenner = new MyLocationListenner();
        }
        return myLocationListenner;
    }

    private MyLocationListenner() {

    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        bdLocation = location;
        this.latitude=location.getLatitude();
        this.longitude=location.getLongitude();
        this.addr=location.getCity()+location.getDistrict()+location.getStreet()+location.getStreetNumber();
        this.city=location.getProvince()+location.getCity();
        this.county=location.getDistrict();
        this.street=location.getStreet()+location.getStreetNumber();
        //Receive Location
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("\nerror code : ");
        sb.append(location.getLocType());
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());

        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());


        sb.append("\nradius : ");
        sb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// 单位：公里每小时
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 单位：米
            sb.append("\ndirection : ");
            sb.append(location.getDirection());// 单位度
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\ndescribe : ");
            sb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            //运营商信息
            sb.append("\noperationers : ");
            sb.append(location.getOperators());
            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
        List<Poi> list = location.getPoiList();// POI数据
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }
        if(locationViewData!=null){
            locationViewData.setData(bdLocation);

        }


    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {

    }


    public void setLocationViewData(LocationViewData locationViewData) {
        if(locationViewData==null){
            this.locationViewData = null;
        }
        if(this.locationViewData==null){
            this.locationViewData = locationViewData;
        }
    }


    public interface LocationViewData{
        public void setData(BDLocation bdLocation);
    }
}
