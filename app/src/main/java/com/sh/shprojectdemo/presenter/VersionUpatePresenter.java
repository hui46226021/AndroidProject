package com.sh.shprojectdemo.presenter;

import android.content.Intent;

import com.jereibaselibrary.application.JrApp;
import com.jereibaselibrary.constant.BaseConstant;
import com.jereibaselibrary.netowrk.listen.RequestCall;
import com.jereibaselibrary.tools.JRAppUtils;
import com.jrfunclibrary.base.view.BaseView;
import com.sh.shprojectdemo.biz.UserOperationBiz;
import com.sh.shprojectdemo.model.VersionModel;

/**
 * Created by zhush on 2017/2/10.
 * E-mail zhush@jerei.com
 * PS 版本更新逻辑
 */
public class VersionUpatePresenter {
    BaseView baseView;

    UserOperationBiz userOperationBiz =new UserOperationBiz();

    public VersionUpatePresenter(BaseView baseView) {
        this.baseView = baseView;
    }

    /**
     * 检查版本更新
     */
    public void checkVersion(){
        userOperationBiz.checkVersion(new RequestCall<VersionModel>(){
            @Override
            public void success(VersionModel dataResult) {
                String version =  JRAppUtils.getAppVersionName(JrApp.getContext(), JrApp.getContext().getPackageName());
                if(version!=dataResult.getVersion()){
                    Intent intent = new Intent();
                    intent.setAction(BaseConstant.NetworkConstant.NETOWRK_BROADCAST_ACTION);
                    intent.putExtra(BaseConstant.NetworkConstant.NETWORK_STATE,BaseConstant.NetworkConstant.NEET_VERSION_UPDATE);
                    intent.putExtra(BaseConstant.UpdateConstant.UPDATE_MESSAGE,dataResult.getMessage());
                    intent.putExtra(BaseConstant.UpdateConstant.UPDATE_URL,dataResult.getUrl());
                    intent.putExtra(BaseConstant.UpdateConstant.UPDATE_VERSION,dataResult.getVersion());
                    intent.putExtra(BaseConstant.UpdateConstant.UPDATE_FILESIZE,dataResult.getSize());
                    JrApp.getContext().sendBroadcast(intent,null); //需要版本更新的广播
                }
            }

            @Override
            public void failed(String message, int errorCode) {

            }
        });
    }
}
