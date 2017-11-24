package com.jrbaselibrary.netowrk.jretrofit.bean;

/**
 * Created by zhush on 2017/11/13.
 * Email 405086805@qq.com
 */

public class CaChe {
    public static final int NOT_CACHE = 3;  //无缓存
    public static final int FOREVER_CACHE = 0;  //先查询缓存  没有在查询网络
    public static final int NO_NETWORK_CACHE = 1;//有网时 查询网络，没网时候 查询缓存
}
