package com.sh.shprojectdemo.common.cache;

import com.jereibaselibrary.cache.OwnCache;
import com.sh.shprojectdemo.model.User;

/**
 * Created by zhush on 2017/1/24.
 * E-mail zhush@jerei.com
 * 临时缓存
 * PS
 */
public class TemporaryCache extends OwnCache{
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public TemporaryCache(int maxSize) {
        super(maxSize);
    }

    public static void putUserSession(User user){
        OwnCache.getInstance().putObject("user",user);
    }

    public static User getUserSession(){
       return (User) OwnCache.getInstance().getObjce("user");
    }
}
