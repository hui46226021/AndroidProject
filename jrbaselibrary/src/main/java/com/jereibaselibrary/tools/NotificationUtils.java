package com.jereibaselibrary.tools;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.jereibaselibrary.application.JrApp;

/**
 * Created by zhush on 2017/2/3.
 * E-mail 405086805@qq.com
 * PS
 *
 * addNotification   弹出一个通知
 * addProgressNotification   弹出一个带进度条的通知
 */
public class NotificationUtils {


    int icon;

    /**
     *
     * @param icon   通知图标
     */
    public NotificationUtils(int icon) {
        this.icon = icon;
    }

    NotificationManager mNotificationManager = (NotificationManager) JrApp.getContext().getSystemService(JrApp.getContext().NOTIFICATION_SERVICE);

    /**
     * 弹出一个通知
     * @param notificatId  通知ID
     * @param titel      标题
     * @param content   内容
     * @param ongoing   该通知 是否可关闭
     */
    public void addNotification(int notificatId, String titel,String content,boolean ongoing) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(JrApp.getContext());
        mBuilder.setContentTitle(titel)//设置通知栏标题
                .setContentText(content) //
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(!ongoing)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
//                Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(icon);//设置通知小ICON
        try {
            mNotificationManager.notify(notificatId, mBuilder.build());
        } catch (Exception e) {
        }

    }

    /**
     * 带进度条的通知
     * @param notificatId
     * @param progress
     * @param content
     */
    public void addProgressNotification(int notificatId, int progress, String titel, String content) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(JrApp.getContext());
        mBuilder.setContentTitle(titel)//设置通知栏标题
                .setContentText(content) //<span style="font-family: Arial;">/设置通知栏显示内容</span>
                //        .setContentIntent(acticity.getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
                //  .setNumber(number) //设置通知集合的数量
//                .setTicker(content + JrApp.getContext().getString(R.string.start_download)) //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//              .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(true)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
//                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                .setSmallIcon(icon);//设置通知小ICON
        mBuilder.setProgress(100, progress, false); //进度条
        if (progress > 99) {
            mNotificationManager.cancel(notificatId);//取消通知
        } else {
            mNotificationManager.notify(notificatId, mBuilder.build());
        }
    }
}
