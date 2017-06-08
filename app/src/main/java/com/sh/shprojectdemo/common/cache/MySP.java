package com.sh.shprojectdemo.common.cache;

import android.content.Context;

import com.jereibaselibrary.cache.SharedPreferencesTool;

/**
 * Created by zhush on 2017/2/10.
 * E-mail 405086805@qq.com
 * PS
 */
public class MySP extends SharedPreferencesTool {
    public MySP(Context context) {
        super(context);
    }

    /**
     * 完成首次登陆
     */
    public static void firstLoginFinish(){
        newInstance().saveData("firstLogin",true);
    }

    /**
     * 是不是首次登陆
     * @return
     */
    public static boolean isFirstLogin(){
        boolean firstLogin=newInstance().getBooleanData("firstLogin");
        if(firstLogin==true){
            return false;
        }else {
            return true;
        }
    }

}
