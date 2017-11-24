package com.jrbaselibrary.netowrk.jretrofit.annotation;

import com.android.volley.Cache;
import com.jrbaselibrary.netowrk.jretrofit.bean.CaChe;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhush on 2017/11/13.
 * Email 405086805@qq.com
 * value  要访问的url
 * cache 缓存策略
 * reqNode 解析返回json的 节点
 * generic 解析返回对象的泛型
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Get {

     String value() default "";
    int cache() default CaChe.NOT_CACHE;
    String reqNode() default "data";
    Class generic() default Object.class;
}
