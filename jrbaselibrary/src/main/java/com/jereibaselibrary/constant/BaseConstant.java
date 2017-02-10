package com.jereibaselibrary.constant;

/**
 * Created by zhush on 2017/1/18.
 * E-mail zhush@jerei.com
 * PS
 */
public class BaseConstant {
    public static class NetworkConstant {
        public final static int CODE_FAILURE =2;
        public final static int CODE_SUCCESS =0;
        public final static int NOT_NETOWRK =-9998; //没有网络
        public final static int NOT_SESSION =-9999; //掉线
        public final static int NEET_VERSION_UPDATE =-9997; //需要版本工薪
        public final static String  NETOWRK_BROADCAST_ACTION ="com.jr.newwork";
        public final static String  NETWORK_STATE ="network_state";
    }
    public static class UpdateConstant {
        public final static String  UPDATE_MESSAGE ="update_message"; //版本更新信息
        public final static String  UPDATE_URL ="update_url";//版本更新地址
        public final static String  UPDATE_VERSION ="update_version";//要更新的版本
        public final static String  UPDATE_FILESIZE ="UPDATE_FILESIZE";//新安装包的大小

    }
}
