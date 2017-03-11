package com.jrfunclibrary.base.view;




/**
 * Created by zhush on 2016/9/29.
 * E-mail 405086805@qq.com
 */
public interface FormSubmitView extends BaseView{


    /**
     * 提交成功
     */
    public void submitSuccess();

    /**
     * 提交失败
     * @param message
     */
    public void submitfailed(String message);


}
