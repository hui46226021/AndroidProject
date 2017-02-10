package com.jereibaselibrary.application;

import android.support.multidex.MultiDexApplication;

import com.jereibaselibrary.cache.OwnCache;
import com.jereibaselibrary.db.litepal.LitePal;
import com.jereibaselibrary.tools.JRExceptionHandler;
import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by zhush on 2017/1/14.
 * E-mail zhush@jerei.com
 * PS Application 基类
 */
public class JrApp extends MultiDexApplication {
    static JrApp context;
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
    public static JrApp getContext(){
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

    /**
     * 设置离线后 默认调转到的 页面
     * @param classz
     */
    public void setLoginPage(Class classz){
        ownCache.setLoginPage(classz);
    }
}
