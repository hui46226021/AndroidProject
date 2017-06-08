package com.sh.shprojectdemo.im;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jerei.im.presentation.event.MessageEvent;
import com.jerei.im.timchat.ui.ContactActivity;
import com.jerei.im.timchat.ui.ConversationFragment;
import com.jerei.im.ui.NotifyDialog;
import com.sh.shprojectdemo.common.cache.TemporaryCache;
import com.sh.shprojectdemo.ui.LoginActivity;


/**
 * Created by zhush on 2016/8/17.
 */
public class MyConversationFragment extends ConversationFragment {


    final int REQUESTCODE = 0;
    private LinearLayout otherNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, container, savedInstanceState);
        otherNews = (LinearLayout) view.findViewById(com.jerei.im.timchat.R.id.otherNews);
        if (TemporaryCache.getUserSession()==null){
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            reloadload();
        }

        return view;
    }


    @Override
    public void onError(int i, String s) {
        super.onError(i, s);
//        Log.e(TAG, "login error : code " + i + " " + s);

        try {
            switch (i) {
                case 6208:
                    //离线状态下被其他终端踢下线
                    NotifyDialog dialog = new NotifyDialog();
                    dialog.show(getString(com.jerei.im.timchat.R.string.kick_logout), ((FragmentActivity) getActivity()).getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            navToHome();
                        }
                    });
                    break;
                default:
                    //位置错误
                    NotifyDialog dialog2 = new NotifyDialog();
                    dialog2.show("连接服务器失败:" + i, ((FragmentActivity) getActivity()).getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            navToHome();
                        }
                    });

                    break;
            }
        } catch (Exception e) {
        }

    }

    @Override
    public void onSuccess() {
        super.onSuccess();
        //初始化程序后台后消息推送
        PushUtil.getInstance();
        //初始化消息监听
        MessageEvent.getInstance();
//        String deviceMan = android.os.Build.MANUFACTURER;
        //注册小米和华为推送
//        if (deviceMan.equals("Xiaomi") && shouldMiInit()){
//            MiPushClient.registerPush(getApplicationContext(), "2882303761517480335", "5411748055335");
//        }else if (deviceMan.equals("HUAWEI")){
//            PushManager.requestToken(this);
//        }
//        Log.d(TAG, "imsdk env " + TIMManager.getInstance().getEnv());


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE && 10000 == resultCode) {
            reloadload();
        }
    }



    //跳转 消息页面
    public void goToContact() {
        if (TemporaryCache.getUserSession()==null){
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
            return;
        }
        startActivity(new Intent(getActivity(), ContactActivity.class));
    }
}
