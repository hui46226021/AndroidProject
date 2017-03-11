package com.sh.zsh.code.umeng_sdk;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by zhush on 2016/11/30.
 * E-mail 405086805@qq.com
 * PS  视图控件 工具类
 */
public class JRViewUtils {
    /**
     * @param view  控件
     * @param visible  是否因此
     * @param direction  移动方向 true X轴  Y false轴
     * @param time   移动时间
     * @param distance  移动距离  控件的倍数
     */
    public static void viewMoveVisible(View view ,boolean visible,boolean direction,long time,float distance){
        if(visible&&view.getVisibility()==View.VISIBLE){
            return;
        }
        if(!visible&&view.getVisibility()!=View.VISIBLE){
            return;
        }
        float x =0.0f;
        float y =0.0f;
        if(direction){
            x = distance;
        }else {
            y= distance;
        }
        if (visible) {
            TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, x,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    y, Animation.RELATIVE_TO_SELF, 0.0f);
            mShowAction.setDuration(time);
            mShowAction.setInterpolator(new AnticipateOvershootInterpolator());
            view.startAnimation(mShowAction);
            view.setVisibility(View.VISIBLE);
        } else {
            TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                    x, Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    y);
            mHiddenAction.setInterpolator(new AnticipateOvershootInterpolator());
            mHiddenAction.setDuration(time);
            view.startAnimation(mHiddenAction);
            view.setVisibility(View.GONE);
        }
    }
}
