package com.jrbaselibrary.netowrk.jretrofit;

import android.content.Intent;
import android.text.TextUtils;


import com.jrbaselibrary.application.JrApp;
import com.jrbaselibrary.cache.DiskLruCacheUtil;
import com.jrbaselibrary.constant.BaseConstant;
import com.jrbaselibrary.netowrk.HttpUtils;
import com.jrbaselibrary.netowrk.jretrofit.annotation.Bean;
import com.jrbaselibrary.netowrk.jretrofit.annotation.Get;
import com.jrbaselibrary.netowrk.jretrofit.annotation.ListBean;
import com.jrbaselibrary.netowrk.jretrofit.annotation.Param;
import com.jrbaselibrary.netowrk.jretrofit.annotation.Post;
import com.jrbaselibrary.netowrk.jretrofit.bean.CaChe;
import com.jrbaselibrary.netowrk.jretrofit.bean.JRequestBody;
import com.jrbaselibrary.netowrk.jretrofit.bean.ParamObject;
import com.jrbaselibrary.netowrk.listen.RequestCall;
import com.jrbaselibrary.tools.JRNetworkUtils;
import com.sh.shjson.JSONUtil;



import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by zhush on 2017/11/13.
 * Email 405086805@qq.com
 */

public class JRetrofit {


    public static JRetrofit Builder() {

        return  new JRetrofit();
    }
    static ExecutorService executorService;
    private JRetrofit(){
        if(executorService==null){
            executorService = Executors.newFixedThreadPool(10);
        }
    }



    NetHandler handler = new NetHandler();

    public <T> T create(final Class<T> service) {
        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {


                    @Override
                    public Object invoke(Object proxy, Method method, Object... args)
                            throws Throwable {

                        JRequestBody jrequestBody = handleMethod(method);
                        handleParam(jrequestBody, method, args);
                        jrequestBody.setRequestCall((RequestCall) args[args.length - 1]);
                        send(jrequestBody);
                        return null;
                    }
                });
    }

    /**
     * 处理注解
     *
     * @param method
     */
    private JRequestBody handleMethod(Method method) {

        JRequestBody requestBody = new JRequestBody();

        Post post = method.getAnnotation(Post.class);
        if (post != null) {
            requestBody.setUrl(post.value());
            requestBody.setCache(post.cache());
            requestBody.setType(JRequestBody.POST);
            requestBody.setReqNode(post.reqNode());
            requestBody.setaClass(post.generic());
        }
        Get get = method.getAnnotation(Get.class);
        if (get != null) {
            requestBody.setUrl(get.value());
            requestBody.setCache(get.cache());
            requestBody.setType(JRequestBody.GET);
            requestBody.setReqNode(get.reqNode());
            requestBody.setaClass(get.generic());
        }
        return requestBody;
    }

    /**
     * 处理注解
     */
    private JRequestBody handleParam(JRequestBody jRequestBody, Method method, Object... args) {

        List<ParamObject> paramObjects = getMethodParameterNamesByAnnotation(method);
        for (int i = 0; i < paramObjects.size(); i++) {
            paramObjects.get(i).setObj(args[i]);
        }
        jRequestBody.setParamObjects(paramObjects);
        return jRequestBody;
    }


    /**
     * 获取指定方法的参数名
     *
     * @param method 要获取参数名的方法
     * @return 按参数顺序排列的参数名列表
     */
    private List<ParamObject> getMethodParameterNamesByAnnotation(Method method) {


        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return null;
        }
        List<ParamObject> paramObjectHashMap = new ArrayList<>(parameterAnnotations.length);

        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            for (Annotation annotation : parameterAnnotation) {
                if (annotation instanceof Bean) {
                    Bean bean = (Bean) annotation;
                    ParamObject paramObject = new ParamObject();
                    paramObject.setName(bean.name());
                    paramObject.setType(ParamObject.BEAN);
                    paramObjectHashMap.add(paramObject);
                }
                if (annotation instanceof ListBean) {
                    ListBean listBean = (ListBean) annotation;
                    ParamObject paramObject = new ParamObject();
                    paramObject.setName(listBean.name());
                    paramObject.setType(ParamObject.LISTBEAN);
                    paramObjectHashMap.add(paramObject);
                }
                if (annotation instanceof Param) {
                    Param param = (Param) annotation;
                    ParamObject paramObject = new ParamObject();
                    paramObject.setName(param.value());
                    paramObject.setType(ParamObject.PARAM);
                    paramObjectHashMap.add(paramObject);
                }
            }
        }
        return paramObjectHashMap;
    }


    private void send(final JRequestBody jRequestBody) {

        //请求成功
        final RequestCall requestCall = jRequestBody.getRequestCall();
        final   int cache=jRequestBody.getCache();
        String req="";
        byte[] bytes = null;
        switch (cache) {
            case CaChe.FOREVER_CACHE://始终查询缓存
                bytes = DiskLruCacheUtil.readFromDiskCache(jRequestBody.getUrl(), JrApp.getContext());
                if (bytes != null) {
                    req = new String(bytes);
                    if (!TextUtils.isEmpty(req)) {
                        JSONUtil jsonUtil = new JSONUtil(req);
                        requestCall.success(jsonUtil.getObject(jRequestBody.getaClass(), jRequestBody.getReqNode()));
                        return;
                    }
                }

                break;
            case CaChe.NO_NETWORK_CACHE: //无网络时候 查询缓存
                if (!JRNetworkUtils.isNetworkAvailable(JrApp.getContext())) {
                    bytes = DiskLruCacheUtil.readFromDiskCache(jRequestBody.getUrl(), JrApp.getContext());
                    if (bytes != null) {
                        req = new String(bytes);
                        if (!TextUtils.isEmpty(req)) {
                            JSONUtil jsonUtil = new JSONUtil(req);
                            requestCall.success(jsonUtil.getObject(jRequestBody.getaClass(), jRequestBody.getReqNode()));
                            return;
                        }
                    }


                    break;
                }

        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final HttpUtils client = new HttpUtils(jRequestBody.getUrl());
                if (jRequestBody.getType() == JRequestBody.GET) {
                    client.get();
                } else {
                    client.post();
                }

                if (client.hasErrors()) {

                    if (client.getResponseStr().contains("notLogin")) { //掉线
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                requestCall.failed("您已掉线，请重新登录",BaseConstant.NetworkConstant.NOT_SESSION);

                                Intent intent = new Intent();
                                intent.setAction(BaseConstant.NetworkConstant.NETOWRK_BROADCAST_ACTION);
                                intent.putExtra(BaseConstant.NetworkConstant.NETWORK_STATE,BaseConstant.NetworkConstant.NOT_SESSION);
                                JrApp.getContext().sendBroadcast(intent,null); //发送掉线广播

                            }
                        });
                    } else {
                        //请求失败

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                requestCall.failed(client.getMessageString(),client.getResultCode());

                            }
                        });

                    }

                } else {

                    if(cache!= CaChe.NOT_CACHE){
                        DiskLruCacheUtil.writeToDiskCache(jRequestBody.getUrl(),client.getResponseStr().getBytes(),JrApp.getContext());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            requestCall.success(client.getObject(jRequestBody.getaClass(), jRequestBody.getReqNode()));

                        }
                    });
                }
            }
        };
        executorService.execute(runnable);
    }
}
