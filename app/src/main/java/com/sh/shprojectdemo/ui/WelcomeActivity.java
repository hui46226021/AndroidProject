package com.sh.shprojectdemo.ui;

import android.content.Intent;
import android.os.Bundle;

import com.jereibaselibrary.cache.SharedPreferencesTool;
import com.jrfunclibrary.base.activity.BaseActivity;
import com.jrfunclibrary.bootpage.BootActivity;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.common.cache.MySP;
import com.sh.shprojectdemo.presenter.VersionUpatePresenter;

public class WelcomeActivity extends BaseActivity {

    VersionUpatePresenter versionUpatePresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        /**
         * 查询版本更新
         */
        versionUpatePresenter = new VersionUpatePresenter(this);
        versionUpatePresenter.checkVersion();
        //判断是否是首次登陆
        if(MySP.isFirstLogin()){
            MySP.firstLoginFinish();
            int[] ids = new int[]{R.drawable.guide01, R.drawable.guide02, R.drawable.guide03,R.drawable.guide04};
            Intent intent = new Intent(this, BootActivity.class);
            intent.putExtra(BootActivity.IDS_KEY,ids);
            intent.putExtra(BootActivity.IDS_ACTIVITY_KEY,MainActivity.class);
            finish();
            startActivity(intent,Animation.N0_ANIM);
        }else {
            Intent intent = new Intent(this, MainActivity.class);
            finish();
            startActivity(intent,Animation.N0_ANIM);
        }
    }
}
