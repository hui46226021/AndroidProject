package com.sh.zsh.code.umeng_sdk;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhush on 2017/2/20
 * E-mail 405086805@qq.com
 * PS  分享弹出
 */

public class ShareDialog {

    private ShareDialog(){

    }

    Context context;
    String mesage;
    String url;
    String bitmapUrl;
    String tital;


    /**
     *
     * @param context
     * @param bibmapUrl 图片地址
     * @param mesage    内容
     * @param url       链接
     * @param tital     标题
     * @return
     */
    public static ShareDialog getInstance(Context context, String bibmapUrl, String mesage, String url,String tital) {
        ShareDialog    shareDialog = new ShareDialog();
        shareDialog.context = context;
        shareDialog.mesage = mesage;
        shareDialog.url = url;
        shareDialog.bitmapUrl = bibmapUrl;
        shareDialog.tital=tital;
        return shareDialog;
    }





    public ShareDialog setUMShareListener(UMShareListener umShareListener) {
        this.umShareListener = umShareListener;
        return this;
    }

    private Dialog dialog;
    public   void showDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.share_dialog, null);
        GridView gridview = (GridView) view.findViewById(R.id.gridview);

        Button button = (Button) view.findViewById(R.id.cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        String[] str_name = {"微信好友", "微信朋友圈", "QQ好友", "新浪微博", "QQ空间", "腾讯微博", "复制链接", "邮件"};
        Integer[] imgs = {R.drawable.share_wechat_session, R.drawable.share_wechat_timeline, R.drawable.share_qq,
                R.drawable.share_sina_weibo, R.drawable.share_qq_zone, R.drawable.share_qq_weibo, R.drawable.share_sms, R.drawable.share_email};

        GridViewAdapter gridViewAdapter = new GridViewAdapter(context, str_name, imgs);

        gridview.setAdapter(gridViewAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClick(position);
            }
        });



        dialog = new Dialog(context, R.style.transparentFrameWindowStyle);

        dialog.setContentView(view, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y =( (Activity)context).getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围消失
        dialog.setCanceledOnTouchOutside(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.show();
                    }
                });
            }
        }, 200);
    }






    void onClick(int position){
        switch (position){
            case 0:
                share(SHARE_MEDIA.WEIXIN);
                break;
            case 1:
                share(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case 2:
                share(SHARE_MEDIA.QQ);
                break;

            case 3:
                share(SHARE_MEDIA.SINA);
                break;
            case 4:
                share(SHARE_MEDIA.QZONE);
                break;
            case 5:
                share(SHARE_MEDIA.TENCENT);
                break;
            case 6:
                ClipboardManager myClipboard;
                myClipboard = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
                ClipData myClip;

                myClip = ClipData.newPlainText("text", mesage);

                myClipboard.setPrimaryClip(myClip);
                Toast toast = Toast.makeText(context,"复制成功", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,30,30);
                toast.show();
                break;
            case 7:
                share(SHARE_MEDIA.EMAIL);
                break;


        }
        dialog.cancel();
    }

    void share(SHARE_MEDIA share_media){

        UMImage image = null;
        if(TextUtils.isEmpty(bitmapUrl)||!bitmapUrl.startsWith("http")){
            Bitmap bitmapIcon= null;
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = null;
            try {
                info = pm.getApplicationInfo(context.getPackageName(), 0);
                Drawable drawable= info.loadIcon(pm);
                BitmapDrawable bd = (BitmapDrawable) drawable;
                bitmapIcon = bd.getBitmap();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            image=   new UMImage(context, bitmapIcon);
        }else {
            image=    new UMImage(context, bitmapUrl);
        }



        new ShareAction((Activity) context)
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
