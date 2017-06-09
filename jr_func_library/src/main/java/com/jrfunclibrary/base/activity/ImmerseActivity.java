package com.jrfunclibrary.base.activity;


import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by zhush on 17-6-8 下午2:54
 * Email:405086805@qq.com
 * Ps: 沉浸式页面
 */
public  class ImmerseActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

    }

}
