package com.jrbaselibrary.netowrk.jretrofit.bean;

import com.jrbaselibrary.netowrk.listen.RequestCall;

import java.util.List;

/**
 * Created by zhush on 2017/11/13.
 * Email 405086805@qq.com
 */

public class JRequestBody {
    public static int POST=1;
    public static int GET=2;




    public String url;
    public int cache;
    public int type;
    public String reqNode;
    public RequestCall requestCall;
    private Class aClass;

    public List<ParamObject> paramObjects;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCache() {
        return cache;
    }

    public void setCache(int cache) {
        this.cache = cache;
    }

    public List<ParamObject> getParamObjects() {
        return paramObjects;
    }

    public void setParamObjects(List<ParamObject> paramObjects) {
        this.paramObjects = paramObjects;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReqNode() {
        return reqNode;
    }

    public void setReqNode(String reqNode) {
        this.reqNode = reqNode;
    }

    public RequestCall getRequestCall() {
        return requestCall;
    }

    public void setRequestCall(RequestCall requestCall) {
        this.requestCall = requestCall;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
