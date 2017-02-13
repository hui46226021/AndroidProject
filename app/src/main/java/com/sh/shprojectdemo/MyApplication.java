package com.sh.shprojectdemo;



import com.jereibaselibrary.application.JrApp;

//import com.sh.shprojectdemo.im.IMHelper;
import com.sh.shprojectdemo.im.IMHelper;
import com.sh.shprojectdemo.ui.LoginActivity;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.LeakTrace;


/**
 * Created by zhush on 2017/1/13.
 * E-mail zhush@jerei.com
 * PS
 */
public class MyApplication extends JrApp {
    @Override
    public void onCreate() {
        super.onCreate();
        //崩溃日志输出
//        exceptionLogOut();
        //初始话IM
        IMHelper.initIM(this);
        //设置离线后 默认调转到的 页面
        setLoginPage(LoginActivity.class);
        //检查内存泄漏
//        LeakCanary.install(this);
    }
}
