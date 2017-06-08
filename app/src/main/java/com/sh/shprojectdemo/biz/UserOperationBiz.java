package com.sh.shprojectdemo.biz;

import com.jereibaselibrary.netowrk.HttpAsynTask;
import com.jereibaselibrary.netowrk.HttpUtils;
import com.jereibaselibrary.netowrk.listen.HandleResponse;
import com.jereibaselibrary.netowrk.listen.RequestCall;
import com.jereibaselibrary.tools.JRDataResult;
import com.sh.shprojectdemo.common.constant.UrlConstant;
import com.sh.shprojectdemo.model.User;
import com.sh.shprojectdemo.model.VersionModel;

/**
 * Created by zhush on 2017/1/24.
 * E-mail 405086805@qq.com
 * PS
 */
public class UserOperationBiz {
    /**
     * 登录
     * @param account
     * @param pwd
     * @param requestCall
     */
    public void login(String account, String pwd, RequestCall requestCall) {
        HttpAsynTask httpAsynTask = new HttpAsynTask(UrlConstant.LOGGIN_URL,HttpAsynTask.GET);
        httpAsynTask.putParam("username", account);
        httpAsynTask.putParam("password", pwd);
        httpAsynTask.setHttpRequestCall(requestCall);
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

    /**
     * 检查版本
     * @param requestCall
     */
    public void checkVersion(RequestCall requestCall){
        HttpAsynTask httpAsynTask = new HttpAsynTask(UrlConstant.CHECKVERSION_URL,HttpAsynTask.GET);
        httpAsynTask.setHttpRequestCall(requestCall);
        httpAsynTask.setHandleResponse(new HandleResponse() {
            @Override
            public JRDataResult putResponse(JRDataResult dataControlResult, HttpUtils client) {
                VersionModel versionModel = client.getObject(VersionModel.class, "data");
                dataControlResult.setResultObject(versionModel);
                return dataControlResult;
            }
        });
        httpAsynTask.execute();
    }

    /**
     * 测试掉线
     * @param requestCall
     */
    public void dropTest(RequestCall requestCall){
        HttpAsynTask httpAsynTask = new HttpAsynTask(UrlConstant.DROPTEST_URL,HttpAsynTask.GET);
        httpAsynTask.setHttpRequestCall(requestCall);

        httpAsynTask.execute();
    }


}
