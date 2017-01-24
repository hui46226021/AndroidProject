package com.jrfunclibrary.base.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.jrfunclibrary.base.view.BaseView;
import com.jruilibarary.widget.IOSAlertDialog;
import com.jruilibarary.widget.MyProgressDialog;
import com.sh.zsh.jrfunclibrary.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 *
 * Created by zhush on 2016/7/8.
 */
public class BaseActivity extends AppCompatActivity implements BaseView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }


    }

    private ViewGroup.LayoutParams mNightViewParam = null;
    private View mask = null;


    /**
     * 点击空白页面收起输入法
     *
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
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

    @Override
    public void noNetwork() {

        showAlertDialog(getString(R.string.func_tip_setting_net), "是否去设置网络链接", getString(R.string.func_cancel), new View.OnClickListener() {
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
        Bitmap bitmap = null;

//        switch (requestCode) {
//            case UpLoadImageView.REQUESTCODE_PICK:
//                if (data == null || data.getData() == null) {
//                    return;
//                }
//                try {
//                    String img_path = UriUtils.uriToPath(this,data.getData());
//                    ExifInterface exifInterface=new ExifInterface(img_path);
//                    String FDateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
//                    Log.e("BaseActivity","拍摄时间="+FDateTime);
//                    String jingdu = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
//                    String weidu = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
//                    Log.e("BaseActivity","位置信息：经度="+jingdu+"   维度="+weidu);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if(bitmap!=null&&uploadImagePresenter!=null){
//                    uploadImagePresenter.uploadImage(bitmap, UpLoadImageView.upLoadImageView);
//                }
//
//                break;
//            case UpLoadImageView.REQUESTCODE_CAMERA:
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), UpLoadImageView.mOutPutFileUri);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                if(bitmap!=null&&uploadImagePresenter!=null){
//                    uploadImagePresenter.uploadImage(bitmap, UpLoadImageView.upLoadImageView);
//                }
//
//                break;
//            default:
//                break;
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
* 添加图片
*/
    public static final int REQUESTCODE_PICK = 1;
    public static final int REQUESTCODE_CAMERA = 3;
    private static String path = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + Environment.getExternalStorageDirectory().getPath();

    private String fileName;

    public void addPictrues() {
        // TODO 打开选择或者拍照的面板


//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("上传群组图片");
//        builder.setItems(new String[]{"从相册选择", "拍照", "取消"},
//                new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        switch (which) {
//                            case 0:
//                                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
//                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//                               startActivityForResult(pickIntent, REQUESTCODE_PICK);
//                                break;
//                            case 1:
//                                Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                //文件夹aaaa
//                                String path = Environment.getExternalStorageDirectory().toString() + "/aaaa";
//                                File path1 = new File(path);
//                                if (!path1.exists()) {
//                                    path1.mkdirs();
//                                }
//                                File file = new File(path1, System.currentTimeMillis() + ".jpg");
//                                UpLoadImageView.mOutPutFileUri = Uri.fromFile(file);
//                                imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, UpLoadImageView.mOutPutFileUri);
//                                startActivityForResult(imageCaptureIntent, REQUESTCODE_CAMERA);
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                });
//        builder.create().show();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
