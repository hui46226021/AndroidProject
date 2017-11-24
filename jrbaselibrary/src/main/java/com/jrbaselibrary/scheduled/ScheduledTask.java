package com.jrbaselibrary.scheduled;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by zhush on 2017/11/14.
 * Email 405086805@qq.com
 */

public class ScheduledTask {
    static HashMap<String, Task> taskHashMap = new HashMap<>();

    /**
     * 启动当前定时器
     *
     * @param object
     */
    public static void start(final Object object) {
        final MyHandler myHandler = new MyHandler();
        Method[] methods = object.getClass().getMethods();
        for (final Method method : methods) {
            Scheduled scheduled = method.getAnnotation(Scheduled.class);
            if (scheduled != null) {
                Task task = new Task() {
                    @Override
                    public void run() {

                        myHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    method.invoke(object);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                };
                task.setInterval(scheduled.fixedDelay());
                task.setMax(scheduled.count());
                taskHashMap.put(object.getClass().getSimpleName() + method.getName(), task);
                task.start();
            }
        }
    }


    /**
     * 启动当前定时器
     *
     * @param object
     */
    public static void start(final Object object,String taskName) {
        final MyHandler myHandler = new MyHandler();
        Method[] methods = object.getClass().getMethods();
        for (final Method method : methods) {
            Scheduled scheduled = method.getAnnotation(Scheduled.class);

            if (scheduled != null&&scheduled.taskName().equals(taskName)) {
                Task task = new Task() {
                    @Override
                    public void run() {

                        myHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    method.invoke(object);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                };
                task.setInterval(scheduled.fixedDelay());
                task.setMax(scheduled.count());
                taskHashMap.put(object.getClass().getSimpleName() + method.getName(), task);
                task.start();
            }
        }
    }

    public static void cancel(final Object object) {
        Method[] methods = object.getClass().getMethods();
        for (final Method method : methods) {
            Scheduled scheduled = method.getAnnotation(Scheduled.class);

            if (scheduled != null) {
                Task task = taskHashMap.remove(object.getClass().getSimpleName() + method.getName());
                task.cancel();
            }
        }
    }
    public static void cancel(final Object object,String taskName) {
        Method[] methods = object.getClass().getMethods();
        for (final Method method : methods) {
            Scheduled scheduled = method.getAnnotation(Scheduled.class);

            if (scheduled != null&&scheduled.taskName().equals(taskName)) {
                Task task = taskHashMap.remove(object.getClass().getSimpleName() + method.getName());
                task.cancel();
            }
        }
    }
}