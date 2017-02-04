package com.jrfunclibrary.fileupload;

import android.content.Intent;
import android.os.Environment;

import com.jereibaselibrary.application.JRBaseApplication;
import com.jereibaselibrary.constant.SystemConfig;
import com.jereibaselibrary.tools.JRLogUtils;
import com.jereibaselibrary.tools.NotificationUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.sh.zsh.jrfunclibrary.R;

import java.io.File;


/**
 * Created by zhush on 2016/11/2.
 * E-mail zhush@jerei.com
 * 下载工具
 */

public class DownloadService {

    private static DownloadService downloaderPresenter;

    public static String STATE_KEY="state_key";
    public static String URL_KEY="url_key";
    public static String PROGRESS_KEY="progress_key";
    public static String LOCAL_KEY="local_key";
    public static String ACTION="com.jr.downloader";

    public static int WAITCONN = 0;//等待连接
    public static int CONN_SUCCESSE = 1;//链接成功
    public static int DOWNLOADING = 2;//正在下载
    public static int DOWNLOADFINIS = 3;//下载完成
    public static int DOWNLOAD_FAIL = 4;//下载失败

    public static final String DIRECTORY = "/DOWN/";


    int progress;

    /**
     * 判断文件是否本地已下载  如果已下载 返回路径
     * @param fileName
     * @param fileType
     * @param fileSize
     * @return
     */
    public String  isDownloaded( String fileName,  String fileType,long fileSize){
        String path = Environment.getExternalStorageDirectory() + DIRECTORY + fileName + fileType;
        File file = new File(path);
        if(file.exists()&&file.length()==fileSize){
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


        FileDownloader.getImpl().create(url)
                .setPath(Environment.getExternalStorageDirectory() + DIRECTORY + fileName + fileType)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        JRLogUtils.e("DownloaderUtils", "等待链接");
                        sendBroadcast(WAITCONN,task.getUrl(),0,task.getPath());
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        sendBroadcast(CONN_SUCCESSE,task.getUrl(),0,task.getPath());
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        JRLogUtils.e("hehe", task.getPath() + "进度" + soFarBytes + "/" + totalBytes);
                        progress = soFarBytes * 100 / totalBytes;
                        notificationUtils.addProgressNotification(resourceId,progress,"正在下载",fileName + "." + fileType);

                        sendBroadcast(DOWNLOADING,task.getUrl(),progress,task.getPath());
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        JRLogUtils.e("hehe", "开始下载");


                    }

                    @Override
                    protected void retry(final BaseDownloadTask task, final Throwable ex, final int retryingTimes, final int soFarBytes) {

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        JRLogUtils.e("DownloaderUtils", "完成" + task.getPath());
                        notificationUtils.addNotification(resourceId,"下载成功",fileName + fileType, true);

                        sendBroadcast(DOWNLOADFINIS,task.getUrl(),100,task.getPath());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        JRLogUtils.e("DownloaderUtils", "暂停" + task.getPath());
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        JRLogUtils.e("DownloaderUtils", "错误" + task.getPath());
                        if (progress >= 99) {
                            sendBroadcast(DOWNLOADFINIS,task.getUrl(),100,task.getPath());
                            notificationUtils.addNotification(resourceId,"下载成功",fileName + fileType, true);
                        } else {
                            sendBroadcast(DOWNLOAD_FAIL,task.getUrl(),0,task.getPath());
                            notificationUtils.addNotification(resourceId,"下载失败",fileName + fileType, true);
                        }

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        JRLogUtils.e("DownloaderUtils", "有相同任务" + task.getPath());
                    }
                }).start();
    }

    /**
     * 发送下载广播
     * @param state
     * @param url
     * @param progress
     */
        public void sendBroadcast(int state,String url,int progress,String local){
            Intent intent = new Intent();
            intent.setAction(ACTION);
            intent.putExtra(STATE_KEY,state);
            intent.putExtra(URL_KEY,url);
            intent.putExtra(LOCAL_KEY,local);
            if(progress>0){
                intent.putExtra(PROGRESS_KEY,progress);
            }
            JRBaseApplication.getContext().sendOrderedBroadcast(intent,null);
    }
}




