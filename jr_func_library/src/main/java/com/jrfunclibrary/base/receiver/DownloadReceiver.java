package com.jrfunclibrary.base.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jrfunclibrary.fileupload.DownloadService;

/**
 * Created by zhush on 2017/2/3.
 * E-mail zhush@jerei.com
 * PS  接受下载广播
 */
public abstract class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       int state = intent.getIntExtra(DownloadService.STATE_KEY,0);

        if(state== DownloadService.WAITCONN){
            downloadBefore("等待连接",intent.getStringExtra(DownloadService.URL_KEY));
        }
        if(state== DownloadService.CONN_SUCCESSE){
            downloadBefore("链接成功",intent.getStringExtra(DownloadService.URL_KEY));
        }
        if(state== DownloadService.DOWNLOADING){
            downloading("正在下载",intent.getStringExtra(DownloadService.URL_KEY),intent.getIntExtra(DownloadService.PROGRESS_KEY,0));
        }
        if(state== DownloadService.DOWNLOADFINIS){
            downloadASuccess("下载成功",intent.getStringExtra(DownloadService.URL_KEY));
        }
        if(state== DownloadService.DOWNLOAD_FAIL){
            downloadAFail("下载失败",intent.getStringExtra(DownloadService.URL_KEY));
        }
    }

    /**
     * 下载前
     */
    public abstract  void  downloadBefore(String state, String url);
    /**
     * 下载中
     */
    public abstract  void  downloading(String state,String url,int progress);
    /**
     * 下载后
     */
    public abstract  void  downloadASuccess(String state,String url);

    /**
     * 下载失败
     */
    public abstract  void  downloadAFail(String state,String url);
}
