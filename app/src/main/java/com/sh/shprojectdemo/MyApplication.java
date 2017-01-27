package com.sh.shprojectdemo;

import android.app.Application;

import com.jereibaselibrary.application.JRBaseApplication;
import com.jereibaselibrary.db.litepal.LitePal;
import com.jereibaselibrary.db.litepal.LitePalApplication;
import com.sh.shprojectdemo.im.IMHelper;


/**
 * Created by zhush on 2017/1/13.
 * E-mail zhush@jerei.com
 * PS
 */
public class MyApplication extends JRBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //崩溃日志输出
//        exceptionLogOut();
        //初始哈IM
        IMHelper.initIM(this);
    }
}
