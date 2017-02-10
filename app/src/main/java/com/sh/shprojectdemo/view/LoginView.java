package com.sh.shprojectdemo.view;

import com.jrfunclibrary.base.activity.BaseActivity;
import com.jrfunclibrary.base.view.BaseView;
import com.sh.shprojectdemo.model.User;

/**
 * Created by zhush on 2017/1/24.
 * E-mail zhush@jerei.com
 * PS
 */
public interface LoginView extends BaseView{
    /**
     * 登录成功
     */
    public void loginSuccess(User user);

    /**
     * 登录失败
     * @param message
     */
    public void loginfail(String message);

}
