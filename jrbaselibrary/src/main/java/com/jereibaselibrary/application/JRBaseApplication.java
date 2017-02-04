package com.jereibaselibrary.application;

import android.app.Application;
import android.content.Context;

import com.jereibaselibrary.cache.OwnCache;
import com.jereibaselibrary.db.litepal.LitePal;
import com.jereibaselibrary.db.litepal.LitePalApplication;
import com.jereibaselibrary.tools.JRExceptionHandler;
import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by zhush on 2017/1/14.
 * E-mail zhush@jerei.com
 * PS
 */
public class JRBaseApplication extends Application {
    static JRBaseApplication context;
    //在 Application 里引用 保证 不会被回收  最多临时缓存30个对象
    private OwnCache ownCache = new OwnCache(30);
    public void onCreate() {

        context = this;
        //崩溃日志输出

        super.onCreate();
        LitePal.initialize(this);
        //初始化 下载引擎
        FileDownloader.init(this);
    }

    /**
     * 获取全局 context
     * @return
     */
    public static JRBaseApplication getContext(){
        return context;
    }

    /**
     * 崩溃日志输出
     */
    public void exceptionLogOut(){
        JRExceptionHandler crashHandler = JRExceptionHandler.getInstance();
        crashHandler.init(context);
    }

    public OwnCache getOwnCache() {
        return ownCache;
    }
}
