package com.jrfunclibrary.fileupload.view;

import com.jrfunclibrary.base.view.BaseView;

/**
 * Created by zhush on 2016/11/2.
 * E-mail zhush@jerei.com
 */
public interface DownloaderView extends BaseView {
    /**
     * 下载进度
     *
     * @param progress
     */
    public void setProgress(String progressStr, int progress, String url) ;


    /**
     * 下载完成
     */
    public void downloadFinish(String path, String url);

    /**
     * 下载失败
     */
    public void downloadError(String url) ;
}
