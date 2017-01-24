package com.jrfunclibrary.base.view;

/**
 * Created by zhush on 2016/11/15.
 * E-mail zhush@jerei.com
 * PS 可刷新列表视图
 */
public interface RefreshLayoutView extends BaseView{
    /**
     * 获取数据成功
     * @param object
     */
    public void getDataSuccess(Object object);

    /**
     * 获取数据失败
     * @param failMessage
     */
    public void getDataFail(String failMessage);
}
