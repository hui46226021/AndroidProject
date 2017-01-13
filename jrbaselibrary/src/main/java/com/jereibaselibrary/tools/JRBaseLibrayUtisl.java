package com.jereibaselibrary.tools;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhush on 2016/11/28.
 * E-mail zhush@jerei.com
 * PS 基础框架  context 类
 *  功能包括
 *  1.获取 全局 context
 *  2.设置崩溃日志输出
 */
public class JRBaseLibrayUtisl {
    static Application context;

    /**
     * 在主程序 初始化后 要初始化基础框架
     * @param application
     */
    public static void initLibray(Application application){
        context = application;
        //崩溃日志输出
        JRExceptionHandler crashHandler = JRExceptionHandler.getInstance();
        crashHandler.init(context);
    }

    /**
     * 获取全局 context
     * @return
     */
    public static Context getContext(){
        return context;
    }
}
