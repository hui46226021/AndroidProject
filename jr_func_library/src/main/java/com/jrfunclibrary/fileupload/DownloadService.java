package com.jrfunclibrary.fileupload;

import android.content.Intent;

import com.jrbaselibrary.application.JrApp;
import com.jrbaselibrary.netowrk.DownloadUtil;
import com.jrbaselibrary.tools.JRFileUtils;
import com.jrbaselibrary.tools.JRLogUtils;
import com.jrbaselibrary.tools.NotificationUtils;

import com.sh.zsh.jrfunclibrary.R;


/**
 * Created by zhush on 2016/11/2.
 * E-mail 405086805@qq.com
 * 下载工具
 */

public class DownloadService {

    public static String STATE_KEY="state_key";
    public static String URL_KEY="url_key";
    public static String PROGRESS_KEY="progress_key";
    public static String LOCAL_KEY="local_key";
    private   String receiverAction="com.jr.downloader";

    public static int WAITCONN = 0;//等待连接
    public static int CONN_SUCCESSE = 1;//链接成功
    public static int DOWNLOADING = 2;//正在下载
    public static int DOWNLOADFINIS = 3;//下载完成
    public static int DOWNLOAD_FAIL = 4;//下载失败
    String  rootAppDirctoryPate=JRFileUtils.getRootAppDirctory(JrApp.getContext());
    String DIRECTORY = rootAppDirctoryPate+"/DOWN/";


    /**
     * 判断文件是否本地已下载  如果已下载 返回路径
     * @param fileName
     * @param fileType
     * @param fileSize
     * @return
     */
    public String  isDownloaded( String fileName,  String fileType,long fileSize){

        String path =DIRECTORY + fileName + fileType;

        if(JRFileUtils.isFileExist(path,fileSize)){
            return path;
        }
        return null;
    }
    /**
     * 封装下载
     *
     * @param url
     * @param fileName
     * @param fileType
     */
    NotificationUtils  notificationUtils = new NotificationUtils(R.drawable.design_fab_background);
    public void downloader(final int resourceId, String url, final String fileName, final String fileType) {
        if(!JRFileUtils.isSDAvailable()){
            sendBroadcast(DOWNLOAD_FAIL,url,0, JrApp.getContext().getString(R.string.func_no_sd_card));
            return;
        }


        DownloadUtil.get().download(url, DIRECTORY,fileName+fileType, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String url, String path) {
                sendBroadcast(DOWNLOADFINIS,url,100,path);
                notificationUtils.addNotification(resourceId,"下载成功",fileName + fileType, true);
            }

            @Override
            public void onDownloading(int progress, String url, String path) {
                notificationUtils.addProgressNotification(resourceId,progress,"正在下载",fileName + "." + fileType);

                sendBroadcast(DOWNLOADING,url,progress,path);
            }

            @Override
            public void onDownloadFailed(String error, String url) {
                sendBroadcast(DOWNLOAD_FAIL,url,0,"下载失败");
                notificationUtils.addNotification(resourceId,"下载失败",fileName + fileType, true);
            }


        });
    }

    /**
     * 发送下载广播
     * @param state
     * @param url
     * @param progress
     */
        public void sendBroadcast(int state,String url,int progress,String local){
            Intent intent = new Intent();
            intent.setAction(receiverAction);
            intent.putExtra(STATE_KEY,state);
            intent.putExtra(URL_KEY,url);
            intent.putExtra(LOCAL_KEY,local);//如果下载成功 这里是 文件的本地地址 如果 失败 这里是 失败原因
            if(progress>0){
                intent.putExtra(PROGRESS_KEY,progress);
            }
            JrApp.getContext().sendBroadcast(intent,null);
    }
    //设置 下载发送的广播的
    public void setReceiverActivity(String receiverAction) {
        this.receiverAction = receiverAction;
    }
}




