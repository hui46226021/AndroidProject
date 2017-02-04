package com.sh.shprojectdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jereibaselibrary.image.JRSetImage;
import com.jereibaselibrary.tools.JRAppUtils;
import com.jrfunclibrary.base.activity.BaseActivity;
import com.jrfunclibrary.base.receiver.DownloadReceiver;
import com.jrfunclibrary.fileupload.DownloadService;
import com.jruilibarary.widget.CircleImageView;
import com.jruilibarary.widget.DownProgressDialog;
import com.jruilibarary.widget.TemplateTitleBar;
import com.sh.shprojectdemo.R;

import com.sh.shprojectdemo.model.User;
import com.sh.shprojectdemo.presenter.LoginPresenter;
import com.sh.shprojectdemo.view.LoginView;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    @InjectView(R.id.template)
    TemplateTitleBar template;
    @InjectView(R.id.image)
    CircleImageView image;
    @InjectView(R.id.account)
    EditText account;
    @InjectView(R.id.pwd)
    EditText pwd;
    @InjectView(R.id.activity_login)
    LinearLayout activityLogin;
    public static String LOGIN_USER = "login_user";
    LoginPresenter loginPresenter;
    @InjectView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        loginPresenter = new LoginPresenter(this);
        initView();
    }

    void initView() {
        template.setMoreTextContextAction("注册", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "嗯，这时候应该弹出注册页面", Toast.LENGTH_SHORT).show();
            }
        });
        String url = "http://ww2.sinaimg.cn/bmiddle/43a39d58gw1ebqjvjr5onj20ea0e1ach";
        JRSetImage.setNetWorkImage(this, url, image);
        loginPresenter.checkVersion();
    }

    @OnClick(R.id.button)
    void pageOnClick(){
        loginPresenter.login(account.getText()+"",pwd.getText()+"");
//        /**
//         * 模仿IM登录
//         */
//        String imSig= "eJxNjdFOg0AQRf9lX2t0F1hWTXwghEYQammt8W1Dy0CHUqCwYmnjvwukJM7bzJl77pV8*Ov7Jj7IqKowJs*EGZRSphtPgtyNEM4V1iCjREE9cM651r-c6BiTkZJ6PaSns8IjjLJHKoxeN8kwhkJhgqPqB3TBGVzYeZLtduV3oaTqKvgnazDtt8DZ2G5oJ7Nsf-DFJYfyy-5cbmLTWaU*U5GVJeX7fr59s9E8gtqeQjddFZY7m3ee25jaggp74YQBj9ee1*KJqSZ4XWYPuRW1XR683MpaqBssi75Qo4wzTafDkN8-TWZZFQ__";
//        String image="http://appservice.etian365.com/upload/upload/20161127/5a458f6d-d675-44a5-a463-4e24219b23e0.jpg";
//        String nickName = "赵四";
//        String id ="we3751ez1x";
//        IMHelper.newIMHelper(this).manualLogin(id,imSig,image,nickName,"铁岭");
    }
    @Override
    public void loginSuccess(User user) {


        Intent intent = getIntent().putExtra(LOGIN_USER, user);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void loginfail(String message) {
        showMessage(message);
    }

    @Override
    public void updateWindow(String message,final String url,final String version,final long fileSize) {
        showAlertDialog("版本更新:" + version, message, "现在更新", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadService downloadService = new DownloadService();
                String path = downloadService.isDownloaded(version,".apk",fileSize);
                if(path!=null){
                    JRAppUtils.installApk(LoginActivity.this,path);
                }else {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(DownloadService.ACTION);
                    registerReceiver(downloadReceiver,intentFilter);//注册下载广播
                    downloadService.downloader(0,url,version,".apk");
                    DownProgressDialog.show(LoginActivity.this,"正在下载");
                }

            }
        },"取消",null);


    }



    DownloadReceiver downloadReceiver = new DownloadReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
        }

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
            JRAppUtils.installApk(LoginActivity.this,local);
        }

        @Override
        public void downloadAFail(String state, String url) {
            DownProgressDialog.dismiss();
            showMessage(state);
        }
    };


}
