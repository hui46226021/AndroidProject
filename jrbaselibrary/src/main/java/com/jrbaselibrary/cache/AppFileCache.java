package com.jrbaselibrary.cache;

import com.google.gson.Gson;
import com.jrbaselibrary.application.JrApp;
import com.jrbaselibrary.tools.JRFileUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhush on 2017/2/13.
 * E-mail 405086805@qq.com
 * PS APP 本地设置缓存  应用删除后  不会随应用一起删除
 * instance 获取 AppFileCache 实例
 * putCache  存入本地数据 （String Integer Double Boolean Long）
 * getStringCache 读取本地数据String
 * getIntegerCache 读取本地数据Integer
 * getDoubleCache 读取本地数据Double
 * getLongCache 读取本地数据Boolean
 * getBooleanCache 读取本地数据Long
 */
public class AppFileCache {
    private static AppFileCache appFileCache;
    private  String paths =JRFileUtils.getRootAppDirctory(JrApp.getContext())+"/config";
    private final String CACHE_PATH_NAME=paths+"/app.config";

    public static AppFileCache instance(){
        if(appFileCache ==null){
            appFileCache = new AppFileCache();

        }

        return appFileCache;
    }

    private AppFileCache(){
        JRFileUtils.createFolder(paths);
    }
    /**
     *
     * @param key
     * @param value
     */
    public void putCache(String key,Boolean  value){
        String jsonStr = getAllCache();
        Map<String, Object> cacheMap= getMapForJson(jsonStr);
        if(cacheMap!=null){
            cacheMap.put(key,value);
            jsonStr=   mapToJson(cacheMap);
            JRFileUtils.writeFile(CACHE_PATH_NAME,jsonStr);
        }
    }

    /**
     *
     * @param key
     * @param value
     */
    public void putCache(String key,String  value){
        String jsonStr = getAllCache();
        Map<String, Object> cacheMap= getMapForJson(jsonStr);
        if(cacheMap==null){
            cacheMap = new HashMap<>();
        }
        cacheMap.put(key,value);
        jsonStr=   mapToJson(cacheMap);
        JRFileUtils.writeFile(CACHE_PATH_NAME,jsonStr);
    }

    /**
     *
     * @param key
     * @param value
     */
    public void putCache(String key,Long  value){
        String jsonStr = getAllCache();
        Map<String, Object> cacheMap= getMapForJson(jsonStr);
        if(cacheMap!=null){
            cacheMap.put(key,value);
            jsonStr=   mapToJson(cacheMap);
            JRFileUtils.writeFile(CACHE_PATH_NAME,jsonStr);
        }
    }


    /**
     *
     * @param key
     * @param value
     */
    public void putCache(String key,Double  value){
        String jsonStr = getAllCache();
        Map<String, Object> cacheMap= getMapForJson(jsonStr);
        if(cacheMap!=null){
            cacheMap.put(key,value);
            jsonStr=   mapToJson(cacheMap);
            JRFileUtils.writeFile(CACHE_PATH_NAME,jsonStr);
        }
    }

    /**
     *
     * @param key
     * @param value
     */
    public void putCache(String key,Integer  value){
        String jsonStr = getAllCache();
        Map<String, Object> cacheMap= getMapForJson(jsonStr);
        if(cacheMap!=null){
            cacheMap.put(key,value);
            jsonStr=   mapToJson(cacheMap);
            JRFileUtils.writeFile(CACHE_PATH_NAME,jsonStr);
        }
    }


    private Object getCache(String key){
        String jsonStr = getAllCache();
        Map<String, Object> cacheMap= getMapForJson(jsonStr);
        return cacheMap.get(key);
    }

    /**
     * 获取字符串类型缓存
     * @param key
     * @return
     */
    public String getStringCache(String key){
        if(getCache(key)!=null&&getCache(key) instanceof String){
            return (String) getCache(key);
        }else {
            return null;
        }
    }


    /**
     * 获取int类型缓存
     * @param key
     * @return
     */
    public Integer getIntegerCache(String key){
        if(getCache(key)!=null&&getCache(key) instanceof Integer){
            return (Integer) getCache(key);
        }else {
            return null;
        }
    }

    /**
     * 获取Double类型缓存
     * @param key
     * @return
     */
    public Double getDoubleCache(String key){
        if(getCache(key)!=null&&getCache(key) instanceof Double){
            return (Double) getCache(key);
        }else {
            return null;
        }
    }

    /**
     * 获取Boolean类型缓存
     * @param key
     * @return
     */
    public Boolean getBooleanCache(String key){
        if(getCache(key)!=null&&getCache(key) instanceof Boolean){
            return (Boolean) getCache(key);
        }else {
            return null;
        }
    }

    /**
     * 获取Long类型缓存
     * @param key
     * @return
     */
    public Long getLonggCache(String key){
        if(getCache(key)!=null&&getCache(key) instanceof Long){
            return (Long) getCache(key);
        }else {
            return null;
        }
    }

    /**
     * 获取全部缓存
     * @return
     */
    private String getAllCache(){
        File cacheFile =new File( CACHE_PATH_NAME);
        if(!cacheFile.exists()){
            JRFileUtils.createFolder(CACHE_PATH_NAME);
        }
       return JRFileUtils.readFile(CACHE_PATH_NAME);
    }

    /**
     * Json 转成 Map<>
     * @param jsonStr
     * @return
     */
    public static Map<String, Object> getMapForJson(String jsonStr){
        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(jsonStr);

            Iterator<String> keyIter= jsonObject.keys();
            String key;
            Object value ;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public  String mapToJson(Map<String, Object> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }
}
