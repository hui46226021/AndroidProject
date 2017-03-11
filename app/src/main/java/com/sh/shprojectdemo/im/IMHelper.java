//package com.sh.shprojectdemo.im;
//
//import android.app.Activity;
//import android.app.ActivityManager;
//import android.app.Application;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.jerei.im.AppCollback;
//import com.jerei.im.IMContext;
//import com.jerei.im.presentation.business.InitBusiness;
//import com.jerei.im.presentation.business.LoginBusiness;
//import com.jerei.im.presentation.event.FriendshipEvent;
//import com.jerei.im.presentation.event.GroupEvent;
//import com.jerei.im.presentation.event.MessageEvent;
//import com.jerei.im.presentation.event.RefreshEvent;
//import com.jerei.im.presentation.presenter.SplashPresenter;
//import com.jerei.im.timchat.model.FriendshipInfo;
//import com.jerei.im.timchat.model.UserInfo;
//import com.jerei.im.timchat.ui.AddFriendActivity;
//import com.jerei.im.timchat.ui.CaptureActivity;
//import com.jerei.im.timchat.ui.ChatActivity;
//import com.jerei.im.timchat.ui.ProfileActivity;
//
//import com.sh.shprojectdemo.R;
//import com.tencent.TIMCallBack;
//import com.tencent.TIMConversation;
//import com.tencent.TIMConversationType;
//import com.tencent.TIMFriendshipManager;
//import com.tencent.TIMLogLevel;
//import com.tencent.TIMManager;
//import com.tencent.TIMOfflinePushListener;
//import com.tencent.TIMOfflinePushNotification;
//import com.tencent.TIMUserProfile;
//import com.tencent.TIMValueCallBack;
//import com.tencent.qalsdk.sdk.MsfSdkUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by zhush on 2016/8/15.
// */
//public class IMHelper implements TIMCallBack,AppCollback {
////public class IMHelper {
//
//    SplashPresenter presenter;
//    public static int LOGIN_RESULT_CODE = 100;
//    public static final int REQUEST_PHONE_PERMISSIONS = 0;
//
//
//    Context context;
//
//    static IMHelper iMHelper;
//
//
//
//
//    public IMHelper(Context context) {
//        this.context = context;
//    }
//
//    public static IMHelper newIMHelper(Context context) {
//        if (iMHelper == null) {
//            iMHelper = new IMHelper(context);
//            IMContext.setAppCollback(iMHelper);
//        }
//        return iMHelper;
//    }
//
//
//    /**
//     * 初始化
//     * @param context
//     */
//    public static void initIM(final Application context) {
//        //初始化IM
//        IMContext.initMI(context);
//        if(MsfSdkUtils.isMainProcess(context)) {
//            Log.d("MyApplication", "main process");
//            // 设置离线推送监听器
//            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
//                @Override
//                public void handleNotification(TIMOfflinePushNotification notification) {
//                    Log.d("MyApplication", "recv offline push");
//                    // 这里的doNotify是ImSDK内置的通知栏提醒，应用也可以选择自己利用回调参数notification来构造自己的通知栏提醒
//                    notification.doNotify(context.getApplicationContext(), R.mipmap.ic_launcher);
//                }
//            });
//        }
//    }
//    public void goToProfile(Activity activity,String identify,String name){
//        if (FriendshipInfo.getInstance().isFriend(identify)){
//            ProfileActivity.navToProfile(context, identify);
//        }else{
//            Intent person = new Intent(context,AddFriendActivity.class);
//            person.putExtra("id",identify);
//            person.putExtra("name","暂未");
//            context.startActivity(person);
//        }
//    }
//    /**
//     * 登录
//     * @param id    账号
//     * @param sing  签名
//     * @param headImageUrl   头像
//     * @param name   昵称
//     */
//    public void manualLogin(String id,String sing,String headImageUrl,String name,String area) {
//
//        clearNotification();
//        SharedPreferences pref = context.getSharedPreferences("imlogin", context.MODE_PRIVATE);
//        pref.edit().putString("sing",sing).commit();
//        pref.edit().putString("id",id).commit();
//        UserInfo.getInstance().setId(id);
//        UserInfo.getInstance().setUserSig(sing);
//        UserInfo.getInstance().setUrl(headImageUrl);
//        UserInfo.getInstance().setNickname(name);
//        UserInfo.getInstance().setArea(area);
//        login();
//
//    }
//
//    /**
//     * 退出
//     */
//    public void logout(){
//        //登出
//        TIMManager.getInstance().logout(new TIMCallBack() {
//            @Override
//            public void onError(int code, String desc) {
//
//                //错误码code和错误描述desc，可用于定位请求失败原因
//                //错误码code列表请参见错误码表
//
//            }
//
//            @Override
//            public void onSuccess() {
//                //登出成功
//                SharedPreferences pref = context.getSharedPreferences("imlogin", context.MODE_PRIVATE);
//                pref.edit().putString("sing","").commit();
//                clearNotification();
//            }
//        });
//    }
//
//    /**
//     *
//     * @param usern 对方的usern
//     * @return
//     */
//    public static boolean isFriend(String usern){
//        return FriendshipInfo.getInstance().isFriend(usern);
//    }
//
//    private void login(){
//        //登录之前要初始化群和好友关系链缓存
//
//
//        SharedPreferences pref = context.getSharedPreferences("data", context.MODE_PRIVATE);
//        int loglvl = pref.getInt("loglvl", TIMLogLevel.DEBUG.ordinal());
//        //初始化IMSDK
//        InitBusiness.start(context.getApplicationContext(), loglvl);
//        //初始化TLS
////        TlsBusiness.init(context.getApplicationContext());
//        //设置刷新监听
//        RefreshEvent.getInstance();
//
//
//        FriendshipEvent.getInstance().init();
//        GroupEvent.getInstance().init();
//        LoginBusiness.loginIm(UserInfo.getInstance().getId(), UserInfo.getInstance().getUserSig(), this);
//    }
//
//    /**
//     * imsdk登录失败后回调
//     */
//    @Override
//    public void onError(int i, String s) {
//        Log.e("", "login error : code " + i + " " + s);
//
//        switch (i) {
//            case 6208:
//                //离线状态下被其他终端踢下线
////                NotifyDialog dialog = new NotifyDialog();
////                dialog.show(context.getString(com.jerei.im.timchat.R.string.kick_logout), ((FragmentActivity) context).getSupportFragmentManager(), new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        login();
////                    }
////                });
//                break;
//            default:
////                Toast.makeText(context, context.getString(com.jerei.im.timchat.R.string.login_error), Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//    }
//
//    /**
//     * imsdk登录成功后回调
//     */
//    @Override
//    public void onSuccess() {
//        LoginBusiness.setAllowType(context);
//        setArea(UserInfo.getInstance().getArea());
//        setNickname(UserInfo.getInstance().getNickname());
//        setHeadImage(UserInfo.getInstance().getUrl());
//        //初始化程序后台后消息推送
//        PushUtil.getInstance();
//        //初始化消息监听
//        MessageEvent.getInstance();
//        String deviceMan = android.os.Build.MANUFACTURER;
//        //注册小米和华为推送
////        if (deviceMan.equals("Xiaomi") && shouldMiInit()){
////            MiPushClient.registerPush(getApplicationContext(), "2882303761517480335", "5411748055335");
////        }else if (deviceMan.equals("HUAWEI")){
////            PushManager.requestToken(this);
////        }
//        Log.d("", "imsdk env " + TIMManager.getInstance().getEnv());
//
//
//    }
//
//
//
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//
//
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            login();
//        } else {
//            Toast.makeText(context, context.getString(com.jerei.im.timchat.R.string.need_permission), Toast.LENGTH_SHORT).show();
//            ((Activity) context).finish();
//        }
//    }
//
//
//
//
//    /**
//     * 判断小米推送是否已经初始化
//     */
//    private boolean shouldMiInit() {
//        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
//        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
//        String mainProcessName = context.getPackageName();
//        int myPid = android.os.Process.myPid();
//        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
//            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 清楚所有通知栏通知
//     */
//    private void clearNotification() {
//        NotificationManager notificationManager = (NotificationManager) context
//                .getSystemService(context.NOTIFICATION_SERVICE);
//        notificationManager.cancelAll();
//
//    }
//    /**
//     * 设置地区
//     * @param area
//     */
//    public void setArea(String area){
//        //设置新昵称为
//        TIMFriendshipManager.getInstance().setLocation(area, new TIMCallBack(){
//            @Override
//            public void onError(int code, String desc){
//                //错误码code和错误描述desc，可用于定位请求失败原因
//                //错误码code列表请参见错误码表
//                Log.e("name",code+desc);
//
//            }
//
//            @Override
//            public void onSuccess(){
//                Log.e("name","成功");
//            }
//        });
//
//    }
//
//    /**
//     * 设置头像
//     * @param url
//     */
//    public void setHeadImage(final String url){
//        TIMFriendshipManager.getInstance().setFaceUrl(url, new TIMCallBack(){
//            @Override
//            public void onError(int code, String desc) {
//
//            }
//
//            @Override
//            public void onSuccess() {
//                UserInfo.getInstance().setUrl(url);
//
//            }
//        });
//    }
//
//
//    /**
//     * 设置昵称
//     * @param name
//     */
//    public void setNickname(String name){
//        //设置新昵称为
//        TIMFriendshipManager.getInstance().setNickName(name, new TIMCallBack(){
//            @Override
//            public void onError(int code, String desc){
//                //错误码code和错误描述desc，可用于定位请求失败原因
//                //错误码code列表请参见错误码表
//                Log.e("name",code+desc);
//
//            }
//
//            @Override
//            public void onSuccess(){
//                Log.e("name","成功");
//            }
//        });
//
//    }
//
//    /**
//     * 设置个性签名
//     * @param str
//     */
//    public void setSelfSignature(String str){
//        TIMFriendshipManager.getInstance().setSelfSignature(str, new TIMCallBack(){
//            @Override
//            public void onError(int code, String desc) {
//                //错误码code和错误描述desc，可用于定位请求失败原因
//                //错误码code列表请参见错误码表
//
//            }
//
//            @Override
//            public void onSuccess() {
//
//            }
//        });
//    }
//
//    /**
//     * 发起会话
//     * @param account  对方账号
//     * @param activity  当前的activty
//     */
//    public void startConversation(String account,Activity activity){
//        Intent intent = new Intent(activity, ChatActivity.class);
//        intent.putExtra("identify", account);
//        intent.putExtra("type", TIMConversationType.C2C);
//        activity.startActivity(intent);
//    }
//    /**
//     * 发起会话
//     * @param activity  当前的activty
//     */
//    public void saoYiSao(Activity activity){
//        activity.startActivity(new Intent(activity, CaptureActivity.class));
//    }
//
//
//    /**
//     * 添加好友
//     * @param account  对方账号
//     * @param activity  当前的activty
//     * @param name  name
//     */
//    public void addFriend(String account,String name,Activity activity){
//        Intent person = new Intent(context,AddFriendActivity.class);
//        person.putExtra("id",account);
//        person.putExtra("name",account);
//        context.startActivity(person);
//    }
//
//
//    @Override
//    public double getLontitude() {
//        return 1.0;
//    }
//
//    @Override
//    public double getLatitude() {
//        return 1.1;
//    }
//
//    @Override
//    public String getLocation() {
//        return "";
//    }
//
//    /**
//     * IM里面跳转到 自定页面
//     * @param activity
//     * @param workId
//     */
//    @Override
//    public void reportContacts(Activity activity,String workId) {
//
//
//    }
//
//
//
//
//    UnReadAmountCall unReadAmountCall;//未读回调
//    int unRead;//未读数
//    /**
//     * 消息数量回调
//     * @param ucRead
//     */
//    @Override
//    public void getUnRead(int ucRead) {
//        iMHelper.unRead = ucRead;
//        try {
//            unReadAmountCall.amount(ucRead);
//        }catch (Exception e){}
//    }
//
//    /**
//     * 新消息+1
//     */
//    public void newMessageCount() {
//        iMHelper.unRead = iMHelper.unRead +1;
//        try {
//            unReadAmountCall.amount(iMHelper.unRead);
//        }catch (Exception e){}
//    }
//
//
//    /**
//     * 查询未读数
//     * @param unReadAmountCall
//     */
//    public  void queryIMUnRead(UnReadAmountCall unReadAmountCall) {
//        iMHelper.unReadAmountCall = unReadAmountCall;
//        long unReadMessageNum = 0;
//        try {
//            List<TIMConversation> list= TIMManager.getInstance().getConversionList();
//            for(TIMConversation tIMConversation:list){
//                Log.e("会话账号:"+tIMConversation.getIdentifer(),tIMConversation.getUnreadMessageNum()+"");
//                unReadMessageNum=unReadMessageNum+ tIMConversation.getUnreadMessageNum();
//            }
//        }catch (Exception e){
//
//        }
//        try {
//            unReadAmountCall.amount((int)unReadMessageNum);
//        }catch (Exception e){}
//
//
//    }
//
//    public static interface UnReadAmountCall{
//        public void amount(int ucRead);
//
//    }
//
//
//    /**
//     * 去二维码扫描
//     */
//    public void setQRCodeScan(){
//        context.startActivity(new Intent(context, CaptureActivity.class));
//    }
//
//
//    public static interface HaveImCall{
//        public void have(String name);
//        public void no_have();
//    }
//
//    /**
//     * 查询时候有IM
//     * @param id
//     * @param haveImCall
//     */
//    public void isHaveIm(String id,final HaveImCall haveImCall){
//        List<String> users = new ArrayList<>();
//        users.add(id);
//
//
////获取用户资料
//        TIMFriendshipManager.getInstance().getUsersProfile(users, new TIMValueCallBack<List<TIMUserProfile>>(){
//            @Override
//            public void onError(int code, String desc){
//                //错误码code和错误描述desc，可用于定位请求失败原因
//                //错误码code列表请参见错误码表
//                haveImCall.no_have();
//            }
//
//            @Override
//            public void onSuccess(List<TIMUserProfile> result){
//                if(result.size()!=0){
//                    for(TIMUserProfile res : result){
//                        haveImCall.have(res.getNickName());
//                    }
//
//                }else {
//                    haveImCall.no_have();
//                }
//
//            }
//        });
//    }
//
//
//}
