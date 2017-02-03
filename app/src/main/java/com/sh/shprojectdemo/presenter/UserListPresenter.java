package com.sh.shprojectdemo.presenter;

import com.jereibaselibrary.constant.BaseConstant;
import com.jereibaselibrary.netowrk.listen.RequestCall;
import com.jrfunclibrary.base.view.RefreshLayoutView;
import com.sh.shprojectdemo.biz.UserListBiz;
import com.sh.shprojectdemo.model.User;

import java.util.List;

/**
 * Created by zhush on 2017/1/25.
 * E-mail zhush@jerei.com
 * PS
 */
public class UserListPresenter {
    UserListBiz userListBiz=  new UserListBiz();

    RefreshLayoutView refreshLayoutView;

    List<User> list;

    int page = 1;

    public UserListPresenter(List<User> list, RefreshLayoutView refreshLayoutView) {
        this.list = list;
        this.refreshLayoutView = refreshLayoutView;
    }

    public void onRefresh(){
        page=1;
        queryUserList(true);
    }

    public void onLoad(){
        page=page+1;
        queryUserList(false);
    }

    /**
     *
     * @param refresh true 刷新 ralse 加载
     */
    public void queryUserList(final boolean refresh){
        userListBiz.queryUserList(page, new RequestCall<List<User>>() {

            @Override
            public void success(List<User> dataResult) {
                if(refresh){
                    list.clear();
                }
                list.addAll(dataResult);
                refreshLayoutView.getDataSuccess(dataResult);
            }

            @Override
            public void failed(String message, int errorCode) {
                if(errorCode== BaseConstant.NetworkConstant.NOT_NETOWRK){
                    refreshLayoutView.noNetwork();
                }else {
                    refreshLayoutView.getDataFail(message);
                }

            }
        });
    }

}
