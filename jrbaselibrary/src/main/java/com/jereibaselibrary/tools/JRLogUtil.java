package com.jereibaselibrary.tools;

import android.util.Log;

/**
 * Created by Administrator on 2016/9/19.
 */
public class JRLogUtil {



    public static boolean isDebugModel = true;// 是否输出日志

    public static void v(final Object object, final String msg)
    {
        if (isDebugModel)
        {
            Log.v(object.getClass().getName(), "--> " + msg);
        }
    }

    public static void d(final Object object, final String msg)
    {
        if (isDebugModel)
        {
            Log.d(object.getClass().getName(), "--> " + msg);
        }
    }

    public static void i(final Object object, final String msg)
    {
        if (isDebugModel)
        {
            Log.i(object.getClass().getName(), "--> " + msg);
        }
    }

    public static void w(final Object object, final String msg)
    {
        if (isDebugModel)
        {
            Log.w(object.getClass().getName(), "--> " + msg);
        }
    }
    public static void e(final Object object, final String msg)
    {
        if (isDebugModel)
        {
            Log.e(object.getClass().getName(), "--> " + msg);
        }
    }
}
