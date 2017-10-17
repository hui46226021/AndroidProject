package com.jrbaselibrary.tools;

import android.media.MediaRecorder;
import android.util.Log;

import com.jrbaselibrary.application.JrApp;

/**
 * 录音工具
 */
public class JRRecorderUtil {

    private static final String TAG = "RecorderUtil";

    private String mFileName = null;
    private MediaRecorder mRecorder = null;
    private long startTime;
    private long timeInterval;
    private boolean isRecording;
    private String path = JRFileUtils.getRootAppDirctory(JrApp.getContext())+"/audio/";

    public JRRecorderUtil(){
        JRFileUtils.createFolder(path);
        mFileName = path+"/"+System.currentTimeMillis()+"_audio.mp3";
    }

    /**
     * 开始录音
     */
    public void startRecording() {
        if (mFileName == null) return;
        if (isRecording){
            mRecorder.release();
            mRecorder = null;
        }
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        startTime = System.currentTimeMillis();
        try {
            mRecorder.prepare();
            mRecorder.start();
            isRecording = true;
        } catch (Exception e){
            Log.e(TAG, "prepare() failed");
        }

    }


    /**
     * 停止录音
     */
    public void stopRecording() {
        if (mFileName == null) return;
        timeInterval = System.currentTimeMillis() - startTime;
        try{
            if (timeInterval>1000){
                mRecorder.stop();
            }
            mRecorder.release();
            mRecorder = null;
            isRecording =false;
        }catch (Exception e){
            Log.e(TAG, "release() failed");
        }

    }

    /**
     * 获取录音文件地址
     */
    public String getFilePath(){
        return mFileName;
    }


    /**
     * 获取录音时长,单位秒
     */
    public long getTimeInterval() {
        return timeInterval/1000;
    }


}
