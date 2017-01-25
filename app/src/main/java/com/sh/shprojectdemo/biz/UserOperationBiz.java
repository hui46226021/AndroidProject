package com.sh.shprojectdemo.biz;

import com.jereibaselibrary.netowrk.HttpAsynTask;
import com.jereibaselibrary.netowrk.HttpUtils;
import com.jereibaselibrary.netowrk.listen.HandleResponse;
import com.jereibaselibrary.netowrk.listen.NetRequestCall;
import com.jereibaselibrary.tools.JRDataResult;
import com.sh.shprojectdemo.common.constant.UrlConstant;
import com.sh.shprojectdemo.model.User;

/**
 * Created by zhush on 2017/1/24.
 * E-mail zhush@jerei.com
 * PS
 */
public class UserOperationBiz {
    /**
     * 登录
     * @param account
     * @param pwd
     * @param netRequestCall
     */
    public void login(String account, String pwd, NetRequestCall netRequestCall) {
        HttpAsynTask httpAsynTask = new HttpAsynTask(UrlConstant.LOGGIN_URL,HttpAsynTask.GET);
        httpAsynTask.putParam("username", account);
        httpAsynTask.putParam("password", pwd);
        httpAsynTask.setHttpRequestCall(netRequestCall);
        httpAsynTask.setHandleResponse(new HandleResponse() {
            @Override
            public JRDataResult putResponse(JRDataResult dataControlResult, HttpUtils client) {
                User user = client.getObject(User.class, "data");
                dataControlResult.setResultObject(user);
                return dataControlResult;
            }
        });
        httpAsynTask.execute();

    }
}
