package com.sh.shprojectdemo.biz;

import com.jereibaselibrary.constant.BaseConstant;
import com.jereibaselibrary.db.litepal.crud.DataSupport;
import com.jereibaselibrary.netowrk.HttpAsynTask;
import com.jereibaselibrary.netowrk.HttpUtils;
import com.jereibaselibrary.netowrk.listen.HandleResponse;
import com.jereibaselibrary.netowrk.listen.RequestCall;
import com.jereibaselibrary.tools.JRDataResult;
import com.sh.shprojectdemo.common.constant.UrlConstant;
import com.sh.shprojectdemo.model.User;

import java.util.List;

/**
 * Created by zhush on 2017/1/25.
 * E-mail zhush@jerei.com
 * PS
 */
public class UserListBiz {
    /**
     * 查询用户列表
     * @param page
     * @param requestCall
     */
    public void queryUserList(int page, RequestCall requestCall){
        HttpAsynTask httpAsynTask = new HttpAsynTask(UrlConstant.USERLIST_URL,HttpAsynTask.GET);
        httpAsynTask.putParam("page", page);

        httpAsynTask.setHttpRequestCall(requestCall);
        //设置查询回掉解析 考虑到反射效率问题 该段代码 会在 子线程中运行
        httpAsynTask.setHandleResponse(new HandleResponse() {
            @Override
            public JRDataResult putResponse(JRDataResult dataControlResult, HttpUtils client) {
                List<User> userList = client.getList(User.class, "data");
                for(User user:userList){//查询出列表  循环保存数据库
                    user.save();
                }
                dataControlResult.setResultObject(userList);
                return dataControlResult;
            }
        });
        //设置 缓存机制 和查询规则
        httpAsynTask.setHttpCacheInterface(new HttpAsynTask.HttpCacheInterface() {
            @Override
            public void getHttpCache( JRDataResult   result ) {

                List<User> userList = DataSupport.findAll(User.class);//从数据库里查出 全部 用户
                if(userList.size()>0){
                    result.setResultCode(BaseConstant.NetworkConstant.CODE_SUCCESS);
                    result.setResultObject(userList);
                }

            }
        },HttpAsynTask.NO_NETWORK_CACHE);
        httpAsynTask.execute();
    }
}
