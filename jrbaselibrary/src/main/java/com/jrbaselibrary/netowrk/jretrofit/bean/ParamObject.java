package com.jrbaselibrary.netowrk.jretrofit.bean;

/**
 * Created by zhush on 2017/11/13.
 * Email 405086805@qq.com
 */

public class ParamObject {

    public final static int BEAN = 1;
    public final static int LISTBEAN = 2;
    public final static int PARAM = 3;
    public int type;
    private String name;

    private Object obj;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
