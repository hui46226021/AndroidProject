package com.jrbaselibrary.netowrk;

import android.util.Log;


import com.jrbaselibrary.application.JrApp;
import com.jrbaselibrary.constant.BaseConstant;
import com.jrbaselibrary.constant.SystemConfig;
import com.jrbaselibrary.netowrk.cookie.CookieJarImpl;
import com.jrbaselibrary.netowrk.cookie.PersistentCookieStore;
import com.jrbaselibrary.tools.JRLogUtils;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by zhush on 2017/1/18.
 * E-mail 405086805@qq.com
 * PS okhttp封装
 *
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
 *  getRetrofit    获取 Retrofit2.0实例
 */
public class HttpUtils {

    String responseStr = "okHttp is request error";
    FormBody.Builder formBodyBuilder;//post 参数
    static OkHttpClient mOkHttpClient;
    static OkHttpClient.Builder builder;
    Request.Builder requestBuilder;
    static String baseUrl= SystemConfig.getFullUrl();
    static {
        //设置超时
        builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(new Interceptor() {
            @Override public Response intercept(Interceptor.Chain chain) throws IOException {
                Request originalRequest = chain.request();
                //请求拦截器   在每次 发起请求的时候  在拦截器里 可以 增加  消息头
                Request authorised = originalRequest.newBuilder()
//                        .header(name, headerValue)
                        .build();
                return chain.proceed(authorised);
            }
        });

        /**
         * 同步cookie
         */
        CookieJarImpl cookieJarImpl = new CookieJarImpl(new PersistentCookieStore(JrApp.getContext()));
        builder.cookieJar(cookieJarImpl);
        //获取Client 实例

        mOkHttpClient=builder.build();
//        /**
//         * 添加证书
//         */
//        HttpsCerts.addCerts(JrApp.getContext());
//        mOkHttpClient=  HttpsCerts.createOkhttp(builder).build();
    }
    public String url;
    private StringBuilder paramSb = new StringBuilder();
    public HttpUtils(String url) {
        this.url = url;
        requestBuilder=new Request.Builder().url(baseUrl+url);
        formBodyBuilder = new FormBody.Builder();
        //公共参数
        //  formBodyBuilder.add("name","value");
    }

    /**
     * 添加请求消息头
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
            JRLogUtils.d("url",baseUrl+url);
            JRLogUtils.d("param",paramSb.toString());
            Log.e("param",paramSb.toString());
            Request request = requestBuilder.post(formBodyBuilder.build()).build();

        okhttp3.Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                responseStr = response.body().string();
            }else {
                responseStr=responseStr+":"+response.code();
            }

            JRLogUtils.json("responseStr",responseStr);
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
            JRLogUtils.d("url",baseUrl+url);
            JRLogUtils.d("param",paramSb.toString());
            //可以省略，默认是GET请求
            requestBuilder.method("GET",null);
            Request request = requestBuilder.build();
            Response response = mOkHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                responseStr = response.body().string().replace("\n","");
            }else {
                responseStr=responseStr+":"+response.code();
            }

            JRLogUtils.json("responseStr",responseStr);
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
        if(value ==null){
            return;
        }
        if(formBodyBuilder==null){
            formBodyBuilder = new FormBody.Builder();
        }
        formBodyBuilder.add(name,value.toString());
        paramSb.append(name);
        paramSb.append("==");
        paramSb.append(value.toString());
        paramSb.append("|");
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
        if(o==null){
            return;
        }
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
        if(list==null||list.size()==0){
            return;
        }
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
     * 解析json 带一层数组
     * @param clazz
     * @param name
     * @param <T>
     * @return
     */
    public <T> T getObject(Class<T> clazz,String name,Class clazz2) {
        try {
            JSONUtil jsonUtil = new JSONUtil(responseStr);
            T t = jsonUtil.getObject(clazz, name,clazz2);
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

    /**
     * 解析json 集合 二维数组
     * @param clazz
     * @param name
     * @param <T>
     * @return
     */
    public <T> List<T> getList(Class<T> clazz, String name, Class ...class2) {


            JSONUtil jsonUtil = new JSONUtil(responseStr);
            List<T> list = jsonUtil.getList(clazz, name,class2);
            return list;


    }



    public String getResponseStr() {
        return responseStr;
    }


    /**
     * 是否有错误
     * @return
     */
    public boolean hasErrors(){

//        if(!responseStr.contains("actionErrors")){
//            return true;
//        }
//
//        if(getList(String.class,"actionErrors").size()>0){
//            return true;
//        }else {
//            return false;
//            }
        return !getObject(Boolean.class,"success");
    }

    /**
     * 获取返回信息
     * @return
     */
    public String getMessageString(){
        List<String > list = getList(String.class,"actionErrors");
        if(list.size()==0){
            return "系统错误00001";
        }
        return list.toString();

//        return getObject(String.class,"message");
    }

    /**
     * 获取返回码
     * @return
     */
    public int getResultCode(){

        try {
            return getObject(Integer.class,"errorCode");
        } catch (Exception e) {
            e.printStackTrace();
            return BaseConstant.NetworkConstant.CODE_FAILURE;
        }
    }

    static Retrofit retrofit;

    /**
     * 获取 Retrofit2.0实例
     * @return
     */
    public static Retrofit getRetrofit(){
        if(retrofit!=null){

        }else {
            retrofit = new Retrofit.Builder()
                    //设置OKHttpClient
                    .client(mOkHttpClient)
                    //设置baseUrl,注意，baseUrl必须后缀"/"
                    .baseUrl(baseUrl)
                    //添加Gson转换器
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
