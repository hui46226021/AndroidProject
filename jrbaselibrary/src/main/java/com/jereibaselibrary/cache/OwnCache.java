package com.jereibaselibrary.cache;

import android.support.v4.util.LruCache;

import com.jereibaselibrary.application.JRBaseApplication;


/**
 * Created by zhush on 2016/11/28
 * E-mail zhush@jerei.com
 * PS  全局缓存临时缓存
 * 最大缓存数量20个（建议在 主项目里 继承此类 增加特定对象的 put get 方法）
 */

public class OwnCache extends LruCache {



    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public OwnCache(int maxSize) {
        super(maxSize);
    }



    public static OwnCache getInstance(){
        return JRBaseApplication.getContext().getOwnCache();
    }

    /**
     * 插入缓存数据
     * @param key
     * @param object
     */
    public void  putObject(String key,Object object){
        put(key,object);
    }

    /**
     * 获取全局临时缓存
     * @param key
     * @return
     */
    public Object getObjce(String key){
      return   get(key);
    }
}
