package com.jrfunclibrary.base.activity;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.jereibaselibrary.application.JrApp;
import com.jereibaselibrary.cache.OwnCache;
import com.jereibaselibrary.constant.BaseConstant;
import com.jereibaselibrary.image.JRBitmapUtils;
import com.jereibaselibrary.tools.JRAppUtils;
import com.jereibaselibrary.tools.JRFileUtils;
import com.jereibaselibrary.tools.JRNetworkUtils;
import com.jrfunclibrary.activity.ImageViewPageActivity;
import com.jrfunclibrary.base.receiver.DownloadReceiver;
import com.jrfunclibrary.base.receiver.NetworkReceiver;
import com.jrfunclibrary.base.view.BaseView;
import com.jrfunclibrary.fileupload.DownloadService;
import com.jruilibrary.widget.DownProgressDialog;
import com.jruilibrary.widget.IOSAlertDialog;
import com.jruilibrary.widget.MyProgressDialog;
import com.sh.zsh.jrfunclibrary.R;

import java.io.File;
import java.io.IOException;


/**
 *
 * Created by zhush on 2016/7/8.
 */
public  class BaseActivity extends AppCompatActivity implements BaseView {

    public static int touchX;
    public static int touchY;
    private Activity activity;
    boolean isCut;//是否图像 截取
    boolean isActive;//当前activity是否活跃
    protected int LOGIN_REQUESTCODE = 10001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        activity = this;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BaseConstant.NetworkConstant.NETOWRK_BROADCAST_ACTION);
        registerReceiver(networkReceiver,intentFilter);//注册网络状态广播
    }
    @Override
    protected void onResume() {
        super.onResume();
        isActive=true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        isActive=false;
    }
    /**
     * 点击空白页面收起输入法
     *
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        touchX=   (int) event.getX();
        touchY=   (int) event.getY();
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }


    public boolean dispatchTouchEvent(MotionEvent event){
        touchX=   (int) event.getX();
        touchY=   (int) event.getY();
       return super.dispatchTouchEvent(event);
    }


    @Override
    public void showProgress(String message) {
        showProgressDialog(message);
    }

    @Override
    public void closeProgress() {
        closeProgressDialog();
    }

    @Override
    public void showMessage(String message) {
        closeProgressDialog();
        setToastMessage(message,Toast.LENGTH_LONG);
    }
    /**
     * Activity之间相互切换的动画枚举
     */
    public enum Animation {
        PUSH_DOWN,
        PUSH_UP,
        FADE,
        SLIDE_IN,
        SLIDE_LEFT,
        SLIDE_RIGHT,
        SLIDE_OUT,
        N0_ANIM;
    }
    @Override
    public void startActivity(Intent intent) {
        startActivity(intent, Animation.SLIDE_RIGHT);
    }

    /**
     * 帶动画 切换
     *
     * @param intent
     * @param nimation
     */
    public void startActivity(Intent intent, Animation nimation) {
        super.startActivity(intent);
        startAnimation(nimation);
    }
    public void finish(int i) {
        if(i==0){
            super.finish();
        }else {
            super.finish();
            overridePendingTransition(0, 0);
        }

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.lift_out_small, R.anim.left_out);
    }
    /**
     * 帶动画 切换
     *
     * @param intent
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode, Animation.SLIDE_RIGHT);
    }
    public void startActivityForResult(Intent intent, int requestCode, Animation nimation) {
            super.startActivityForResult(intent, requestCode);
        startAnimation(nimation);
    }
    private void startAnimation(Animation nimation) {
        switch (nimation) {
            case FADE:
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case PUSH_UP:
                overridePendingTransition(R.anim.push_up_in_animation, R.anim.push_up_out_animation);
                break;
            case PUSH_DOWN:
                overridePendingTransition(R.anim.push_down_in_animation, R.anim.push_down_out_animation);
                break;
            case SLIDE_IN:
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            case SLIDE_LEFT:
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
                break;
            case SLIDE_RIGHT:
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                break;
            case N0_ANIM:
                overridePendingTransition(0, 0);
                break;
        }
    }

    /**
     * 显示进度对话框
     */
    public void showProgressDialog(String message) {
        showProgressDialog(message,true);
    }

    /**
     * 显示进度对话框
     */
    public void showProgressDialog(String message,boolean cancelable) {
        MyProgressDialog.show(this, message,cancelable);
    }

    /**
     * 关闭进度对话框
     */
    public void closeProgressDialog() {
            MyProgressDialog.dismiss();
    }

    /**
     * 自定义对话框
     *
     * @param tital
     * @param message
     * @param button1
     * @param listener
     * @param button2
     * @param listener2
     */
    public void showAlertDialog(String tital, String message, String button1, View.OnClickListener listener, String button2, View.OnClickListener listener2) {

        IOSAlertDialog iosAlertDialog=   new IOSAlertDialog(this).builder().setTitle(tital)
                .setMsg(message)
                .setPositiveButton(button1, listener);

        if(!TextUtils.isEmpty(button2)){
            iosAlertDialog  .setNegativeButton(button2, listener2);
        }
        iosAlertDialog.show();
    }

    /**
     * 弹出吐丝
     *
     * @param message
     */
    public void setToastMessage(String message, int i) {
        if(!TextUtils.isEmpty(message)){
            Toast toast = Toast.makeText(this, message, i);
//        toast.setGravity(Gravity.CENTER,30,30);
            toast.show();
            closeProgressDialog();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                if(isCut){
                    //截取图像
                    startPhotoZoom(data.getData());
                    return;
                }
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    bitmap =    JRBitmapUtils.compressByBitmapSize(bitmap);
                    getPhonePhoto(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case REQUESTCODE_CAMERA:
                if(isCut){
                    //截取图像
                    startPhotoZoom(iamgeUri);
                    return;
                }
                try {
                    Bitmap bitmap  = MediaStore.Images.Media.getBitmap(this.getContentResolver(),iamgeUri);
                    bitmap =    JRBitmapUtils.compressByBitmapSize(bitmap);
                    getPhonePhoto(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = extras.getParcelable("data");
                        bitmap =    JRBitmapUtils.compressByBitmapSize(bitmap);
                        getPhonePhoto(bitmap);
                    }

                }
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    //格式化图片
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /*
* 添加图片
*/
    public static final int REQUESTCODE_PICK = 9001;
    public static final int REQUESTCODE_CAMERA = 9003;
    private static final int REQUESTCODE_CUTTING = 9004;

    String path = JRFileUtils.getRootAppDirctory(JrApp.getContext());

    public static Uri iamgeUri;

    /**
     * 弹出 照片选择器
     * @param isCut 获取照片后 是否 截取图片
     * @param bitmap 当前控件上的图片 没有可以传null
     */
    public void addPictrues(boolean isCut,final Bitmap bitmap) {
        this.isCut =isCut;
        String[] items=null;
        if(bitmap == null){
            items= new String[]{"从相册选择", "拍照", "取消"};
        }else {
            items= new String[]{"从相册选择", "拍照","查看图片", "取消"};
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("上传群组图片");
        builder.setItems(items,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                               startActivityForResult(pickIntent, REQUESTCODE_PICK);
                                break;
                            case 1:
                                Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                if(JRFileUtils.isSDAvailable()){
                                    File path1 = new File(path);
                                    File file = new File(path1, System.currentTimeMillis() + ".jpg");
                                    iamgeUri = Uri.fromFile(file);
                                    imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, iamgeUri);
                                    startActivityForResult(imageCaptureIntent, REQUESTCODE_CAMERA);
                                }else {
                                    showMessage(getString(R.string.func_no_sd_card));
                                }

                                break;
                            case 2:
                                if(bitmap!=null){ //打开图片
                                    ImageViewPageActivity.bitmap = bitmap;
                                    Intent intent=new Intent(activity, ImageViewPageActivity.class);
                                    startActivity(intent);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * 获取到 手机照片（压缩后的）
     * @param bitmap
     */
    public void getPhonePhoto(Bitmap bitmap){

    }

    /**
     * 网络状态广播
     */
    NetworkReceiver networkReceiver = new NetworkReceiver() {
        @Override
        public void noHaveNetwork() {
            if(isActive){
                noNetwork();
            }
        }

        @Override
        public void dropped() {
            if(isActive){
                offLine();
            }
        }

        @Override
        public void versionUpdate(String message, String url, String version, long fileSize) {
            if(isActive){
                update(message,url,version,fileSize,false);
            }
        }
    };

    /**
     * 创建版本更新广播
     * @return
     */
    DownloadReceiver updateDownloadReceiver;
    DownloadReceiver initRersionUpdatedownloadReceiver(){
        updateDownloadReceiver = new DownloadReceiver() {
            @Override
            public void downloadBefore(String state, String url) {
                DownProgressDialog.setProgress(0);
            }
            @Override
            public void downloading(String state, String url, int progress) {
                DownProgressDialog.setProgress(progress);
            }
            @Override
            public void downloadASuccess(String state, String url,String local) {
                DownProgressDialog.dismiss();
                JRAppUtils.installApk(BaseActivity.this,local);
            }
            @Override
            public void downloadAFail(String state, String url) {
                DownProgressDialog.dismiss();
                showMessage(state+":"+url);
            }
        };
       return updateDownloadReceiver;
    }


    /**
     * 没有网络
     */
    public void noNetwork() {

        showAlertDialog(getString(R.string.func_tip_setting_net), "是否去设置网络链接", getString(R.string.func_confirm), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                // 先判断当前系统版本
                if(Build.VERSION.SDK_INT > 10){  // 3.0以上
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                }else{
                    intent = new Intent();
                    intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
                }
                startActivity(intent);
            }
        }, getString(R.string.general_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /**
     * 离线
     */
    public void offLine() {

        showAlertDialog(getString(R.string.func_hint), "您的账号已在其他设备上登录", getString(R.string.func_login_again), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_again();
            }
        }, getString(R.string.func_sign_out), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeProgram();
            }
        });
    }

    /**
     *
     * @param message 更新信息
     * @param url  下载地址
     * @param version 版本号
     * @param fileSize apk大小
     * @param background 不弹出更新对话框 在wifi状态下 偷偷下载 安装包 现在好后再提示
     */
    public void update(final String message,final String url,final String version,final long fileSize,boolean background){
      final   DownloadService downloadService = new DownloadService();
        if(background){
            //如果当前是wifi网络 并且没有现在安装包
            if(JRNetworkUtils.isWifi(this)&&downloadService.isDownloaded(version,".apk",fileSize)!=null){
                downloadService.downloader(0,url,version,".apk");
                return;
            }


        }
        showAlertDialog("版本更新:" + version, message, "现在更新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = downloadService.isDownloaded(version,".apk",fileSize);
                if(path!=null){
                    JRAppUtils.installApk(BaseActivity.this,path);
                }else {
                    String receiverAction = "com.jr.version.update";
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(receiverAction);
                    registerReceiver(initRersionUpdatedownloadReceiver(),intentFilter);//注册下载广播
                    downloadService.setReceiverActivity(receiverAction);
                    downloadService.downloader(0,url,version,".apk");
                    DownProgressDialog.show(BaseActivity.this,"正在下载");
                }

            }
        },"取消",null);
    }

    /**
     * 重新登录 可在子类 重写
     */
    public  void login_again(){
        if(OwnCache.getInstance().getLoginPage()!=null){
            startActivityForResult(new Intent(this, OwnCache.getInstance().getLoginPage()), LOGIN_REQUESTCODE);
        }
    };
    /**
     * 关闭程序 可在子类重写
     */
    public  void closeProgram(){};

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册的广播
        unregisterReceiver(networkReceiver);
        if(updateDownloadReceiver!=null){
            unregisterReceiver(updateDownloadReceiver);
        }

    }
}
