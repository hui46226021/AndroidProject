package com.jruilibrary.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.jruilibrary.anim.ViewAnimUtils;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by zhush on 2017/2/22.
 * E-mail 405086805@qq.com
 * PS 封装一些 特殊的 activity 过场动画
 */
public class JRActivityUtils {
    private Activity activity;
    private int touchY;
    private int touchX;
    private Bitmap bitmap;
 static    JRActivityUtils jrActivityUtils;
    public static  JRActivityUtils getInstance(){
        if(jrActivityUtils==null){
            jrActivityUtils =new JRActivityUtils();
        }
        return jrActivityUtils;
    }

    public static void removeUtils(){
        jrActivityUtils = null;
    }

    /**
     * 跳转动画
     * @param activity
     * @param bitmap  当前页面截图  作为下个页面北京
     * @param touchX  当前点击得坐标
     * @param touchY  当前点击得坐标
     * @param intent
     */
   public  void activityAnim(Activity activity,Bitmap bitmap,int touchX,int touchY,Intent intent){

        this.activity = activity;
       this.bitmap = bitmap;
       this.touchX = touchX;
       this.touchY = touchY;
       activity.startActivity(intent);
       activity.overridePendingTransition(0,0);

   }

    /**
     * 显示当前页面
     * @param acitvity
     * @param rootView  当前页面根视图
     * @param view    要显示得布局
     */
    public void show(final Activity acitvity, final View rootView,final View view){


        rootView.setBackgroundDrawable(new BitmapDrawable(bitmap));
        view.setVisibility(View.INVISIBLE);
        new Timer().schedule(new TimerTask() {//延时250毫秒刷新
            @Override
            public void run() {
                acitvity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        createViewAnim(view);
                    }
                });
            }
        }, 150);

    }

    public void createViewAnim(final View view){
                Animator mAnimator = ViewAnimUtils.createCircularReveal(view,touchX,touchY, 0, view.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void closeViewAnim(final Activity activity,final View view) {
        Animator mAnimator = ViewAnimUtils.createCircularReveal(view,touchX,touchY, view.getHeight(),0);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                activity.finish();
                activity.overridePendingTransition(0,0);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

}
