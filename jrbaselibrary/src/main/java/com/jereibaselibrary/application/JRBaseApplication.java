package com.jereibaselibrary.application;

import android.app.Application;
import android.content.Context;

import com.jereibaselibrary.db.litepal.LitePal;
import com.jereibaselibrary.db.litepal.LitePalApplication;
import com.jereibaselibrary.tools.JRExceptionHandler;

/**
 * Created by zhush on 2017/1/14.
 * E-mail zhush@jerei.com
 * PS
 */
public class JRBaseApplication extends Application {
    static Application context;
    public void onCreate() {

        context = this;
        //崩溃日志输出

        super.onCreate();
        LitePal.initialize(this);
    }

    /**
     * 获取全局 context
     * @return
     */
    public static Context getContext(){
        return context;
    }

    /**
     * 崩溃日志输出
     */
    public void exceptionLogOut(){
        JRExceptionHandler crashHandler = JRExceptionHandler.getInstance();
        crashHandler.init(context);
    }
}
