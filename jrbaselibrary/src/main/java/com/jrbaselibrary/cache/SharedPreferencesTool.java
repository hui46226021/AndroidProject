package com.jrbaselibrary.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.jrbaselibrary.application.JrApp;


/**
 * Created by zhush on 2016/11/28
 * E-mail 405086805@qq.com
 * PS  SharedPreferences 数据存储 封装初始化  插入和删除
 * （建议在 主项目里 继承此类 增加特定对象的 set get 方法）
 */

public class SharedPreferencesTool {

    public static SharedPreferencesTool instance;

    private SharedPreferences sharedPreferences;//数据


    private String DEFAULT="default";


    public Context context;

    public static SharedPreferencesTool newInstance() {
        if(instance==null){
        instance = new SharedPreferencesTool(JrApp.getContext());
        }
        return instance;
    }
    public SharedPreferencesTool(Context context) {
        this.context = context;
    }


    /**
     * 保存数据
     * @param key
     * @param object
     */
    public void saveData(String key,Object object){
        saveData(key,object,DEFAULT);
    }

    /**
     * 保存数据 带名字
     * @param key
     * @param object
     * @param SPNmae
     */
    public synchronized void saveData(String key,Object object,String SPNmae){

        sharedPreferences = context.getSharedPreferences(SPNmae, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(object instanceof Integer){
            editor.putInt(key, (Integer) object);
        }
        if(object instanceof Float){
            editor.putFloat(key, (Float) object);
        }
        if(object instanceof Long){
            editor.putLong(key, (Long) object);
        }
        if(object instanceof Boolean){
            editor.putBoolean(key, (Boolean) object);
        }
        if(object instanceof String ){
            editor.putString(key, (String) object);
        }

        editor.commit();

    }

    /**
     * 获取数据
     * @param key
     * @param SPNmae
     * @param classType
     * @return
     */
    public synchronized Object getData(String key,String SPNmae,Class classType){
        sharedPreferences = context.getSharedPreferences(SPNmae, context.MODE_PRIVATE);
        if(classType == Integer.class){
            return   sharedPreferences.getInt(key,0);
        }
        if(classType == Float.class){
            return sharedPreferences.getFloat(key,0.0f);
        }
        if(classType == Long.class){
            return  sharedPreferences.getLong(key,0l);
        }
        if(classType == Boolean.class){
            return   sharedPreferences.getBoolean(key,false);
        }
        if(classType == String.class){
            return   sharedPreferences.getString(key,"");
        }

        return null;
    }

    /**
     * 获取相应的类型数据
     * @param key
     * @return
     */
    public int getIntData(String key){
        return (int) getData(key,DEFAULT,Integer.class);
    }

    public float getFloatData(String key){
        return (float) getData(key,DEFAULT,Float.class);
    }

    public long getLongData(String key){
        return (long) getData(key,DEFAULT,Long.class);
    }

    public Boolean getBooleanData(String key){
        return (Boolean) getData(key,DEFAULT,Boolean.class);
    }
    public String getStringData(String key){
        return (String) getData(key,DEFAULT,String.class);
    }
}
