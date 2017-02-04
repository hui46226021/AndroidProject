package com.sh.shprojectdemo.model;

/**
 * Created by zhush on 2017/2/3.
 * E-mail zhush@jerei.com
 * PS
 */
public class VersionModel {

    /**
     * version : 1.0.1
     * message : 修改一些bug
     * url : http://m.shouji.360tpcdn.com/170119/739aca8a47d2af4a65d4c81168eee6ea/com.ss.android.essay.joke_601.apk
     */

    private String version;
    private String message;
    private String url;
    private long size;
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
