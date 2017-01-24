package com.sh.shprojectdemo.ui;

import android.content.Intent;
import android.os.Bundle;

import com.jereibaselibrary.cache.SharedPreferencesTool;
import com.jrfunclibrary.base.activity.BaseActivity;
import com.jrfunclibrary.bootpage.BootActivity;
import com.sh.shprojectdemo.R;

public class WelcomeActivity extends BaseActivity {
    String FIRSTLOGIN = "nofirstLogin";//不是首次登陆

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(!SharedPreferencesTool.newInstance().getBooleanData(FIRSTLOGIN)){
            SharedPreferencesTool.newInstance().saveData(FIRSTLOGIN,true);
            int[] ids = new int[]{R.mipmap.guide01, R.mipmap.guide02, R.mipmap.guide03,R.mipmap.guide04};
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
