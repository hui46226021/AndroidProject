package com.jrbaselibrary.netowrk.jretrofit.annotation;

import com.jrbaselibrary.netowrk.jretrofit.bean.CaChe;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhush on 2017/11/13.
 * Email 405086805@qq.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Post {

    String value();
    int cache() default CaChe.NOT_CACHE;
    String reqNode() default "data";
    Class generic() default Object.class;
}
