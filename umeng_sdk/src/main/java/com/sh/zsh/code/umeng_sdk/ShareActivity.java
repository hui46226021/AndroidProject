package com.sh.zsh.code.umeng_sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import net.qiujuer.genius.blur.StackBlur;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.radius;
/**
 * Created by zhush on 2017/2/20
 * E-mail 405086805@qq.com
 * PS 分享弹出页面
 */

public class ShareActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout rootLayout;
    ImageView wxhy;
    ImageView wxpyq;
    ImageView xlwb;
    ImageView qqhy;
    ImageView qqkj;
    ImageView qqwb;
    ImageButton cancel;


    static  Bitmap bitmap;
    static  String  url;
    static  String bitmapUrl;
    static  String tital;
    static  String mesage;
    /**
     * fenxiang
     * @param context
     * @param bibmapUrl
     * @param mesage
     * @param url
     * @param tital
     */
    public static void share(View view,Activity context, String bibmapUrl, String mesage, String url, String tital){
        ShareActivity.mesage = mesage;
        ShareActivity.url = url;
        ShareActivity.bitmapUrl = bibmapUrl;
        ShareActivity.tital=tital;


// 设置控件允许绘制缓存
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap=   view.getDrawingCache();

        Bitmap newBitmap = StackBlur.blurNatively(bitmap, 25, false);
        ShareActivity.bitmap= newBitmap;
        // 设置控件允许绘制缓存
        view.setDrawingCacheEnabled(false);

        context.startActivity(new Intent(context,ShareActivity.class));
        context.overridePendingTransition(R.anim.share_in,R.anim.share_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        rootLayout = (LinearLayout) findViewById(R.id.rootLayout);

        rootLayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
        wxhy = (ImageView)findViewById(R.id.wxhy);
        wxpyq = (ImageView)findViewById(R.id.wxpyq);
        xlwb = (ImageView)findViewById(R.id.xlwb);
        qqhy = (ImageView)findViewById(R.id.qqhy);
        qqkj = (ImageView)findViewById(R.id.qqkj);
        qqwb = (ImageView)findViewById(R.id.qqwb);
        cancel = (ImageButton)findViewById(R.id.cancel);

        wxhy.setOnClickListener(this);
        wxpyq.setOnClickListener(this);
        xlwb.setOnClickListener(this);
        qqhy.setOnClickListener(this);
        qqkj.setOnClickListener(this);
        qqwb.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        displayView(true);
    }



    private void displayView(Boolean display){

        JRViewUtils.viewMoveVisible(wxhy, display, false, 650, 4.0f);
        JRViewUtils.viewMoveVisible(wxpyq, display, false, 770, 4.0f);
        JRViewUtils.viewMoveVisible(xlwb, display, false, 890, 4.0f);
        JRViewUtils.viewMoveVisible(qqhy, display, false, 750, 4.0f);
        JRViewUtils.viewMoveVisible(qqkj, display, false, 900, 4.0f);
        JRViewUtils.viewMoveVisible(qqwb, display, false, 1050, 4.0f);
        JRViewUtils.viewMoveVisible(cancel, display, false, 1150, 4.0f);
    }



    @Override
    public void onBackPressed() {
        closePage();
    }

    public void closePage(){
        displayView(false);
        new Timer().schedule(new TimerTask() {//延时250毫秒刷新
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0,R.anim.share_out);
                    }
                });
            }
        }, 600);

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.wxhy){
            share(SHARE_MEDIA.WEIXIN);
        }
        if(view.getId()==R.id.wxpyq){
            share(SHARE_MEDIA.WEIXIN_CIRCLE);
        }
        if(view.getId()==R.id.xlwb){
            share(SHARE_MEDIA.SINA);
        }
        if(view.getId()==R.id.qqhy){
            share(SHARE_MEDIA.QQ);
        }
        if(view.getId()==R.id.qqkj){
            share(SHARE_MEDIA.QZONE);
        }
        if(view.getId()==R.id.qqwb){
            share(SHARE_MEDIA.TENCENT);
        }
        if(view.getId()==R.id.cancel){

            closePage();
        }

    }

    void share(SHARE_MEDIA share_media){

        UMImage image = null;
        if(TextUtils.isEmpty(bitmapUrl)||!bitmapUrl.startsWith("http")){
            Bitmap bitmapIcon= null;
            PackageManager pm = getPackageManager();
            ApplicationInfo info = null;
            try {
                info = pm.getApplicationInfo(getPackageName(), 0);
                Drawable drawable= info.loadIcon(pm);
                BitmapDrawable bd = (BitmapDrawable) drawable;
                bitmapIcon = bd.getBitmap();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            image=   new UMImage(this, bitmapIcon);
        }else {
            image=    new UMImage(this, bitmapUrl);
        }



        new ShareAction((Activity) this)
                .setPlatform(share_media)
                .setCallback(umShareListener)
                .withText(mesage)
                .withTargetUrl(url)
                .withMedia(image)
                .withTitle(mesage)
                .share();

    }

    UMShareListener umShareListener =new UMShareListener() {
        @Override
        public void onResult(final SHARE_MEDIA platform) {
            Log.i("state"," 分享成功啦");
            new Thread(new Runnable() {
                @Override
                public void run() {

                    String target = "qq";

                    if((platform+"").equals(SHARE_MEDIA.WEIXIN)||(platform+"").equals(SHARE_MEDIA.WEIXIN_CIRCLE)){
                        target = "weixin";
                    }

                    if((platform+"").equals(SHARE_MEDIA.SINA)){
                        target = "weibo";
                    }

                }
            }).start();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(context,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(context,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
