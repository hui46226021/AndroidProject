package com.sh.shprojectdemo.presenter;

import android.text.TextUtils;

import com.jereibaselibrary.application.JRBaseApplication;
import com.jereibaselibrary.constant.BaseConstant;
import com.jereibaselibrary.netowrk.listen.RequestCall;
import com.jereibaselibrary.tools.JRAppUtils;
import com.sh.shprojectdemo.biz.UserOperationBiz;
import com.sh.shprojectdemo.common.cache.TemporaryCache;
import com.sh.shprojectdemo.model.User;
import com.sh.shprojectdemo.model.VersionModel;
import com.sh.shprojectdemo.view.LoginView;

/**
 * Created by zhush on 2017/1/24.
 * E-mail zhush@jerei.com
 * PS
 */
public class LoginPresenter {
    LoginView loginView;
    UserOperationBiz loginBiz =new UserOperationBiz();
    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    public void login(String account,String pwd){
        if(!checkParam(account,pwd)){
            return;
        }
        loginView.showProgress("正在登录");
        loginBiz.login(account, pwd, new RequestCall<User>() {

            @Override
            public void success(User dataResult) {
                dataResult.save();
                TemporaryCache.putUserSession(dataResult);
                loginView.closeProgress();
                loginView.loginSuccess(dataResult);
            }

            @Override
            public void failed(String message, int errorCode) {
                if(errorCode== BaseConstant.NetworkConstant.NOT_NETOWRK){
                    loginView.noNetwork();
                }else {
                    loginView.loginfail(message);
                }
            }
        });

    }

    public boolean checkParam(String account,String pwd){
        if(TextUtils.isEmpty(account)||TextUtils.isEmpty(pwd)){
            loginView.showMessage("请填写完整账户信息");
            return false;
        }else {
            return true;
        }

    }

    public void checkVersion(){
        loginBiz.checkVersion(new RequestCall <VersionModel>(){
            @Override
            public void success(VersionModel dataResult) {
                String version =  JRAppUtils.getAppVersionName(JRBaseApplication.getContext(),JRBaseApplication.getContext().getPackageName());
                if(version!=dataResult.getVersion()){
                    loginView.updateWindow(dataResult.getMessage(),dataResult.getUrl(),dataResult.getVersion());
                }
            }

            @Override
            public void failed(String message, int errorCode) {

            }
        });
    }

}
