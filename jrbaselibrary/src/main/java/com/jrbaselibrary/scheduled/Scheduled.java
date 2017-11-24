package com.jrbaselibrary.scheduled;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zhush on 2017/11/14.
 * Email 405086805@qq.com
 * 定时任务
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scheduled {
    public String taskName() default "default";//任务名称
    public int fixedDelay() default 1000;//间隔时间
    public int count() default 0;//循环多少次  0 代表无限循环


}
