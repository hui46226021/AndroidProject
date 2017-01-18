package com.jereibaselibrary.netowrk;

import android.util.Log;


import com.jereibaselibrary.application.JRBaseApplication;
import com.jereibaselibrary.netowrk.cookie.CookieJarImpl;
import com.jereibaselibrary.netowrk.cookie.PersistentCookieStore;
import com.sh.shjson.JSONUtil;

import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import java.util.Collections;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.Response;



/**
 * Created by zhush on 2017/1/18.
 * E-mail zhush@jerei.com
 * PS okhttp封装
 *
 * addHeaderInterceptor   添加消息头拦截器  (在所有的网络请求上 加上 次消息头)
 * addHeader             添加单次请求消息头
 * post
 * get
 * putParam   插入参数
 * putBean    插入对象
 * putList    插入集合
 * getObject  获取解析json的对象
 *  getList    获取解析List的对象
 *  getResponseStr 获取返回值
 *  hasErrors      是否有错误
 *  getMessageString  获取返回信息
 */
public class HttpUtils {

    String responseStr = "okHttp is request error";
    FormBody.Builder formBodyBuilder;//post 参数
    static OkHttpClient mOkHttpClient;
    static OkHttpClient.Builder builder;
    Request.Builder requestBuilder;

    static {
        //设置超时
        builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS);

        /**
         * 同步cookie
         */
        CookieJarImpl cookieJarImpl = new CookieJarImpl(new PersistentCookieStore(JRBaseApplication.getContext()));
        builder.cookieJar(cookieJarImpl);
        //获取Client 实例
        mOkHttpClient=builder.build();
    }

    public HttpUtils(String url) {
        requestBuilder=new Request.Builder().url(url);
    }

    /**
     * 添加消息头拦截器  (在所有的网络请求上 加上 次消息头)
     * @param name
     * @param headerValue
     */
    public void addHeaderInterceptor(final String name,final String headerValue){
        if(builder!=null){
            builder.addInterceptor(new Interceptor() {
                @Override public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request originalRequest = chain.request();

                    Request authorised = originalRequest.newBuilder()
                            .header(name, headerValue)
                            .build();
                    return chain.proceed(authorised);
                }
            });
            mOkHttpClient=builder.build();
        }
    }



    /**
     * 添加单次请求消息头
     * @param name
     * @param headerValue
     */
    public void addHeader(final String name,final String headerValue){
        requestBuilder.addHeader(name,headerValue);
    }

    /**
     * post请求
     * @return
     */
    public String post(){
        try {

            if(formBodyBuilder==null){
                formBodyBuilder = new FormBody.Builder();
            }
            Request request = requestBuilder.post(formBodyBuilder.build()).build();

        okhttp3.Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                responseStr = response.body().string();
            }
    } catch (Exception e) {
        e.printStackTrace();
    }
        return responseStr;
    }

    /**
     * get请求
     * @return
     */
    public String get(){
        try {

            //可以省略，默认是GET请求
            requestBuilder.method("GET",null);
            Request request = requestBuilder.build();
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                responseStr = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStr;
    }

    /**
     * 插入参数
     * @param name
     * @param value
     */
    public void putParam(String name, Object value) {
        if(formBodyBuilder==null){
            formBodyBuilder = new FormBody.Builder();
        }
        formBodyBuilder.add(name,value.toString());
    }

    /**
     * 插入对象
     * @param name
     * @param o
     */
    public void putBean(String name, Object o) {
        if (name == null)
            name = "";
        else
            name += ".";
        Method[] aryMethod = o.getClass().getMethods();
        for (Method method : aryMethod) {
            if(method.getName().equals("getClass"))
                continue;
            if (method.getName().startsWith("get")
                    && method.getParameterTypes().length == 0) {
                String fname = Character
                        .toLowerCase(method.getName().charAt(3))
                        + method.getName().substring(4,
                        method.getName().length());
                Object value = null;
                try {
                    value = method.invoke(o,  new  Object[]{});
                    putParam(name + fname, value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } else if (method.getName().startsWith("is")
                    && method.getParameterTypes().length == 0) {
                String fname = Character
                        .toLowerCase(method.getName().charAt(2))
                        + method.getName().substring(3,
                        method.getName().length());
                try {
                    Object value = method.invoke(o,  new  Object[]{});
                    putParam(name + fname, value);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 插入集合
     * @param name
     * @param list
     */
    public void putList(String name, List<?> list) {
        int i = 0;
        for (Object o : list) {
            String itemName = name + "[" + (i++) + "]";
            if (isBaseType(o))
                putParam(itemName, o);
            else
                putBean(itemName, o);
        }
        putParam(name + "Length", list.size());
    }

    private boolean isBaseType(Object o) {
        Class clazz = o.getClass();
        if (clazz.equals(Integer.TYPE))
            return true;
        else if (clazz.equals(Double.TYPE))
            return true;
        else if (clazz.equals(Long.TYPE))
            return true;
        else if (clazz.equals(Boolean.TYPE))
            return true;
        else if (clazz.equals(String.class))
            return true;
        else if (o instanceof File)
            return true;
        else if (o instanceof InputStream)
            return true;
        return false;
    }

    /**
     * 解析json
     * @param clazz
     * @param name
     * @param <T>
     * @return
     */
    public <T> T getObject(Class<T> clazz, String name) {
        try {
            JSONUtil jsonUtil = new JSONUtil(responseStr);
            T t = jsonUtil.getObject(clazz, name);
            return t;
        } catch (Exception e) {
            Log.w("json", e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解析json 集合
     * @param clazz
     * @param name
     * @param <T>
     * @return
     */
    public <T> List<T> getList(Class<T> clazz, String name) {
        try {
            JSONUtil jsonUtil = new JSONUtil(responseStr);
            List<T> list = jsonUtil.getList(clazz, name);
            return list;
        } catch (Exception e) {
            Log.w("json", e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    public String getResponseStr() {
        return responseStr;
    }

    /**
     * 是否有错误
     * @return
     */
    public boolean hasErrors(){
        return false;
    }

    /**
     * 获取返回信息
     * @return
     */
    public String getMessageString(){
        return "";
    }
}
