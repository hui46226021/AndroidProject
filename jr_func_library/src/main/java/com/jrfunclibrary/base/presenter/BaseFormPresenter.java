package com.jrfunclibrary.base.presenter;

import com.jereibaselibrary.netowrk.HttpAsynTask;
import com.jereibaselibrary.netowrk.HttpUtils;
import com.jereibaselibrary.netowrk.listen.HandleResponse;
import com.jereibaselibrary.netowrk.listen.RequestCall;
import com.jereibaselibrary.tools.JRDataResult;
import com.jrfunclibrary.base.view.FormSubmitView;

import java.util.HashMap;


/**
 * Created by zhush on 17-6-10 上午11:04
 * Email:405086805@qq.com
 * Ps: 通用提交表单  （单个对象提交） 如果参数不够 可以继承该类 从写内部方法
 */
public class BaseFormPresenter {
    public FormSubmitView formSubmitView;

    public BaseFormPresenter(FormSubmitView formSubmitView) {
        this.formSubmitView = formSubmitView;
    }

    /**
     * 提交表单
     * @param obj
     * @param url
     */
    public void subForm(Object obj,String url){
        formSubmitView.showProgress("");
        new FormBiz().subForm(obj, url, new RequestCall() {
            @Override
            public void success(Object dataResult) {
                formSubmitView.submitSuccess();
            }

            @Override
            public void failed(String message, int errorCode) {
                formSubmitView.submitfailed(message);
            }
        });
    }


    /**
     * 查询单个实体（默认返回值）
     * @param keyId
     * @param classz
     */
    public void queryInfo(int keyId,String url,Class classz){
        formSubmitView.showProgress("");
        new FormBiz().queryInfo(keyId, url, classz, new RequestCall() {
            @Override
            public void success(Object dataResult) {
                formSubmitView.closeProgress();
                formSubmitView.getDetails(dataResult);
            }

            @Override
            public void failed(String message, int errorCode) {
                formSubmitView.showMessage(message);
            }
        });
    }

    /**
     * 查询单个实体
     * @param keyId
     * @param url
     * @param classz
     * @param handleResponse
     */
    public void queryInfo(int keyId,String url,Class classz,HandleResponse handleResponse){
        formSubmitView.showProgress("");
        new FormBiz().queryInfo(keyId, url, classz, new RequestCall() {
            @Override
            public void success(Object dataResult) {
                formSubmitView.closeProgress();
                formSubmitView.getDetails(dataResult);
            }
            @Override
            public void failed(String message, int errorCode) {
                formSubmitView.showMessage(message);
            }
        },handleResponse);
    }


    class FormBiz{
        /**
         * 获取单标详细信息
         * @param keyId
         * @param url
         * @param classz
         * @param requestCall
         */
        public void queryInfo(int keyId, String url,final Class classz,RequestCall requestCall){
            HttpAsynTask httpAsynTask = new HttpAsynTask(url);
            httpAsynTask.putParam("keyId", keyId);
            httpAsynTask.setHttpRequestCall(requestCall);
            httpAsynTask.setHandleResponse(new HandleResponse() {
                @Override
                public JRDataResult putResponse(JRDataResult dataControlResult, HttpUtils client) {
                   Object objcet= client.getObject(classz,"en");
                    dataControlResult.setResultObject(objcet);
                    return dataControlResult;
                }
            });
            httpAsynTask.execute();
        }


        /**
         * 获取单标详细信息
         * @param keyId
         * @param url
         * @param classz
         * @param requestCall
         */
        public void queryInfo(int keyId, String url,final Class classz,RequestCall requestCall,HandleResponse handleResponse){
            HttpAsynTask httpAsynTask = new HttpAsynTask(url);
            httpAsynTask.putParam("keyId", keyId);
            httpAsynTask.setHttpRequestCall(requestCall);
            httpAsynTask.setHandleResponse(handleResponse);
            httpAsynTask.execute();
        }

        /**
         * 提交单表
         * @param obj
         * @param url
         * @param requestCall
         */
        public void subForm(Object obj, String url,RequestCall requestCall){
            HttpAsynTask httpAsynTask = new HttpAsynTask(url);
            httpAsynTask.putBean("en", obj);
            httpAsynTask.setHttpRequestCall(requestCall);
            httpAsynTask.setHandleResponse(new HandleResponse() {
                @Override
                public JRDataResult putResponse(JRDataResult dataControlResult, HttpUtils client) {
                    String reson=client.getMessageString();
                    dataControlResult.setResultMessage(reson);
                    dataControlResult.setResultObject(client.getObject(Object.class,"en"));
                    return dataControlResult;
                }
            });
            httpAsynTask.execute();
        }
    }
}
