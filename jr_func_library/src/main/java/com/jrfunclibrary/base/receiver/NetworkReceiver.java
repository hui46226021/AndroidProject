package com.jrfunclibrary.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jereibaselibrary.constant.BaseConstant;
import com.jrfunclibrary.fileupload.DownloadService;

/**
 * Created by zhush on 2017/2/3.
 * E-mail 405086805@qq.com
 * PS  接受网络状态广播
 */
public abstract class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       int state = intent.getIntExtra(BaseConstant.NetworkConstant.NETWORK_STATE,0);
        switch (state){
            case BaseConstant.NetworkConstant.NOT_NETOWRK:
                noHaveNetwork();
                break;
            case BaseConstant.NetworkConstant.NOT_SESSION:
                dropped();
                break;
            case BaseConstant.NetworkConstant.NEET_VERSION_UPDATE:
                String message = intent.getStringExtra(BaseConstant.UpdateConstant.UPDATE_MESSAGE);
                String url = intent.getStringExtra(BaseConstant.UpdateConstant.UPDATE_URL);
                String version = intent.getStringExtra(BaseConstant.UpdateConstant.UPDATE_VERSION);
                long size = intent.getLongExtra(BaseConstant.UpdateConstant.UPDATE_FILESIZE,0);
                versionUpdate(message,url,version,size);
                break;
        }
    }

    /**
     * 没有网络
     */
    public abstract  void  noHaveNetwork();
    /**
     * 掉线了
     */
    public abstract  void  dropped();
    /**
     * 版本更新
     */
    public abstract  void  versionUpdate(String message,String url,String version,long fileSize);

}
