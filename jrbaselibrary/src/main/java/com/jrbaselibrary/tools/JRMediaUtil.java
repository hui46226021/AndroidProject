package com.jrbaselibrary.tools;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.jrbaselibrary.application.JrApp;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by zhush on 2017/2/13
 * E-mail 405086805@qq.com
 * PS  媒体播放工具
 */

public class JRMediaUtil {

    private static final String TAG = "JRMediaUtil";

    private MediaPlayer player;
    private EventListener eventListener;

    private JRMediaUtil(){
        player = new MediaPlayer();
    }

    private static JRMediaUtil instance = new JRMediaUtil();

    public static JRMediaUtil getInstance(){
        return instance;
    }

    public MediaPlayer getPlayer() {
        return player;
    }


    public void setEventListener(final EventListener eventListener) {
        if (player != null){
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    eventListener.onStop();
                }
            });
        }
        this.eventListener = eventListener;
    }

    public void play(FileInputStream inputStream){
        try{
            if (eventListener != null){
                eventListener.onStop();
            }
            player.reset();
            player.setDataSource(inputStream.getFD());
            player.prepare();
            player.start();
        }catch (IOException e){
            Log.e(TAG, "play error:" + e);
        }


    }

    public void play(String path){
        try{
            if (eventListener != null){
                eventListener.onStop();
            }
            player.reset();
            player.setDataSource(path);
            player.prepare();
            player.start();
        }catch (IOException e){
            Log.e(TAG, "play error:" + e);
        }


    }


    public void stop(){
        if (player != null && player.isPlaying()){
            player.stop();
        }
    }

    public long getDuration(String path){
        player = MediaPlayer.create(JrApp.getContext(), Uri.parse(path));
        return player.getDuration();
    }


    /**
     * 播放器事件监听
     */
    public interface EventListener{
        void onStop();
    }
}
