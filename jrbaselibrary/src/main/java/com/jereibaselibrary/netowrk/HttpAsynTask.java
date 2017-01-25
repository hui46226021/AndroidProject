package com.jereibaselibrary.netowrk;

import android.content.Context;
import android.os.AsyncTask;


import com.jereibaselibrary.R;
import com.jereibaselibrary.application.JRBaseApplication;
import com.jereibaselibrary.constant.BaseConstant;
import com.jereibaselibrary.constant.SystemConfig;
import com.jereibaselibrary.netowrk.listen.HandleResponse;
import com.jereibaselibrary.netowrk.listen.NetRequestCall;
import com.jereibaselibrary.tools.JRDataResult;
import com.jereibaselibrary.tools.JRNetworkUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 *
 * 异步请求 通用组件
 * 主要为了  解析json的过程 也在 子线程中执行  因为反射 性能特别差
 * Created by zhush on 2016/9/27.
 * E-mail zhush@jerei.com
 */
public class HttpAsynTask extends AsyncTask<Void, Integer, JRDataResult> {
    HttpUtils client;
    private NetRequestCall httpRequestCall;//响应回调
    private HandleResponse handleResponse;//组织成功返回值接口
    private HashMap<String, Object> param ;
    private HashMap<String, Object> objectParam ;
    private String url;
    private HttpCacheInterface httpCacheInterface; //缓存调用
    private int cacheTrigger;
    public static final int FOREVER_CACHE = 0;  //先查询缓存  没有在查询网络
    public static final int NO_NETWORK_CACHE = 1;//有网时 查询网络，没网时候 查询缓存

    final Context context = JRBaseApplication.getContext();

    //网络请求方式
  static   public boolean POST=false;
   static public boolean GET=true;
    private boolean requestMode;
    /**
     * 创建网络请求 默认是post
     * @param url
     */
    public HttpAsynTask(String url) {
        this.url = url;
        client = new HttpUtils(url);
    }

    /**
     * 创建网络请求 默认是post
     * @param url
     * @param requestMode
     */
    public HttpAsynTask(String url,boolean requestMode) {
        this.url = url;
         client = new HttpUtils(url);
        this.requestMode = requestMode;
    }

    public void putBean(String key,Object object){
        client.putBean(key,object);
    }
    public void putList(String key,List<?> list){
        client.putList(key,list);
    }
    public void putParam(String key,Object object){
        client.putParam(key,object);
    }

    @Override
    protected JRDataResult doInBackground(Void... voids) {

        JRDataResult  result = new JRDataResult(BaseConstant.NetworkConstant.CODE_FAILURE, context.getString(R.string.jr_base_control_net_error));
        //如果设置了缓存查询 策略先查询缓存
        if(httpCacheInterface!=null){

            switch (cacheTrigger){
                case FOREVER_CACHE://始终查询缓存
                    httpCacheInterface.getHttpCache(result);
                    break;
                case NO_NETWORK_CACHE: //无网络时候 查询缓存
                    if(!JRNetworkUtils.isNetworkAvailable(JRBaseApplication.getContext())){
                        httpCacheInterface.getHttpCache(result);
                    }
                    break;
            }

            if(result.getResultCode().equals(BaseConstant.NetworkConstant.CODE_SUCCESS)){
                return result;
            }
        }
        /**
         * 检查网络
         */
        if(!JRNetworkUtils.isNetworkAvailable(JRBaseApplication.getContext())){
            return result;
        }
        //访问网络
        try {

            if(requestMode){
                client.get();
            }else {
                client.post();
            }
            if (client.hasErrors()) {
                //请求失败
                result.setResultCode(BaseConstant.NetworkConstant.CODE_FAILURE);
                result.setResultMessage(client.getMessageString());

            } else {
                //请求成功
                result.setResultCode(BaseConstant.NetworkConstant.CODE_SUCCESS);
                result.setResultMessage(client.getMessageString());
                //处理返回值
                if(handleResponse!=null){
                    handleResponse.putResponse(result, client);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(JRDataResult dataControlResult) {

        if(dataControlResult==null){
            httpRequestCall.failed(context.getString(R.string.jr_base_control_no_network),BaseConstant.NetworkConstant.NOT_NETOWRK);
            return;

        }
        if (dataControlResult.getResultCode() == BaseConstant.NetworkConstant.CODE_SUCCESS&&httpRequestCall!=null) {
            //执行完毕
            httpRequestCall.success(dataControlResult.getResultObject());
        } else {
            httpRequestCall.failed(dataControlResult.getResultMessage(),0);
        }

    }

    /**
     * 参数
     * @param param
     */
    public void setParam(HashMap<String, Object> param) {
        this.param = param;
    }
    /**
     * 对象参数
     * @param param
     */
    public void setObjectParam(HashMap<String, Object> param) {
        this.objectParam = param;
    }

    /**
     * 回调
     * @param httpRequestCall
     */
    public void setHttpRequestCall(NetRequestCall httpRequestCall) {
        this.httpRequestCall = httpRequestCall;
    }

    /**
     * 处理响应数据 方法
     * @param handleResponse
     */
    public void setHandleResponse(HandleResponse handleResponse) {

        this.handleResponse = handleResponse;
    }


    /**
     * 查询缓存策略
     * @param httpCacheInterface
     */
    public void setHttpCacheInterface(HttpCacheInterface httpCacheInterface,int cacheTrigger) {
        this.httpCacheInterface = httpCacheInterface;
        this.cacheTrigger =cacheTrigger;
    }

    public static interface  HttpCacheInterface{
        public void getHttpCache(    JRDataResult result);
    }
}
