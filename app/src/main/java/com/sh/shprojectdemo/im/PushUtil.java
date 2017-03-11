//package com.sh.shprojectdemo.im;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.net.Uri;
//import android.support.v4.app.NotificationCompat;
//import android.text.TextUtils;
//
//import com.jerei.im.IMContext;
//import com.jerei.im.presentation.event.MessageEvent;
//import com.jerei.im.timchat.model.CustomMessage;
//import com.jerei.im.timchat.model.FriendProfile;
//import com.jerei.im.timchat.model.FriendshipInfo;
//import com.jerei.im.timchat.model.GroupInfo;
//import com.jerei.im.timchat.model.Message;
//import com.jerei.im.timchat.model.MessageFactory;
//import com.jerei.im.timchat.model.ProfileSummary;
//import com.jerei.im.timchat.utils.Foreground;
//
//import com.sh.shprojectdemo.R;
//import com.sh.shprojectdemo.ui.MainActivity;
//import com.tencent.TIMConversationType;
//import com.tencent.TIMGroupReceiveMessageOpt;
//import com.tencent.TIMManager;
//import com.tencent.TIMMessage;
//import com.tencent.TIMOfflinePushSettings;
//
//import java.util.List;
//import java.util.Observable;
//import java.util.Observer;
//
///**
// * 在线消息通知展示
// */
//public class PushUtil implements Observer {
//
//    private static int pushNum=0;
//
//    private final int pushId=1;
//
//    private static PushUtil instance = new PushUtil();
//
//    private PushUtil() {
//        MessageEvent.getInstance().addObserver(this);
//    }
//
//    public static PushUtil getInstance(){
//        //设置消息推送
//        final Uri notifyMusic = Uri.parse("android.resource://com.jrm.farmer_mobile/" + com.jerei.im.timchat.R.raw.dudulu);
//        TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
//        settings.setEnabled(true);
//        settings.setC2cMsgRemindSound(notifyMusic);
//        TIMManager.getInstance().configOfflinePushSettings(settings);
//        return instance;
//    }
//
//
//
//    private void PushNotify(TIMMessage msg){
//        //系统消息，自己发的消息，程序在前台的时候不通知
//        if (msg==null|| Foreground.get().isForeground()||
//                (msg.getConversation().getType()!= TIMConversationType.Group&&
//                        msg.getConversation().getType()!= TIMConversationType.C2C)||
//                msg.isSelf()||
//                msg.getRecvFlag() == TIMGroupReceiveMessageOpt.ReceiveNotNotify ||
//                MessageFactory.getMessage(msg) instanceof CustomMessage) return;
//        String senderStr,contentStr;
//        Message message = MessageFactory.getMessage(msg);
//        if (message == null) return;
//        senderStr = message.getSender();
//        String tital = "";
//
//            List<FriendProfile> friendsList = FriendshipInfo.getInstance().getFriends();
//            for(FriendProfile friendProfile:friendsList){
//                if(friendProfile.getIdentify().equals(senderStr)){
//                    tital = friendProfile.getName();
//                }
//            }
//            if(TextUtils.isEmpty(tital)||tital.contains("@")){
//                List<ProfileSummary> result = GroupInfo.getInstance().getGroupListByType(GroupInfo.publicGroup);
//                for(ProfileSummary profileSummary:result){
//                    if(profileSummary.getIdentify().equals(senderStr)){
//                        tital = profileSummary.getName();
//                    }
//
//                }
//            }
//
//
//
//        contentStr = message.getSummary();
//        NotificationManager mNotificationManager = (NotificationManager) IMContext.getContext().getSystemService(IMContext.getContext().NOTIFICATION_SERVICE);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(IMContext.getContext());
//        Intent notificationIntent = new Intent(IMContext.getContext(),MainActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent intent = PendingIntent.getActivity(IMContext.getContext(), 0,
//                notificationIntent, 0);
//        mBuilder.setContentTitle(tital)//设置通知栏标题
//                .setContentText(contentStr)
//                .setContentIntent(intent) //设置通知栏点击意图
////                .setNumber(++pushNum) //设置通知集合的数量
//                .setTicker(tital+":"+contentStr) //通知首次出现在通知栏，带上升动画效果的
//                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
//                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
//        Notification notify = mBuilder.build();
//        notify.flags |= Notification.FLAG_AUTO_CANCEL;
//        mNotificationManager.notify(pushId, notify);
//        //在未读消息数上加1
//        IMHelper.newIMHelper(IMContext.getContext()).newMessageCount();
//    }
//
//    public static void resetPushNum(){
//        pushNum=0;
//    }
//
//    public void reset(){
//        NotificationManager notificationManager = (NotificationManager) IMContext.getContext().getSystemService(IMContext.getContext().NOTIFICATION_SERVICE);
//        notificationManager.cancel(pushId);
//    }
//
//    /**
//     * This method is called if the specified {@code Observable} object's
//     * {@code notifyObservers} method is called (because the {@code Observable}
//     * object has been updated.
//     *
//     * @param observable the {@link Observable} object.
//     * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
//     */
//    @Override
//    public void update(Observable observable, Object data) {
//        if (observable instanceof MessageEvent){
//            TIMMessage msg = (TIMMessage) data;
//            if (msg != null){
//                PushNotify(msg);
//            }
//        }
//    }
//}
