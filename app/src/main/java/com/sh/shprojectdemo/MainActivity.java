package com.sh.shprojectdemo;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jereibaselibrary.db.litepal.crud.DataSupport;


import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("demo");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("打印",getStrFromJNI());


        News news = new News();
        news.setId(5);
        news.setCommentCount(200);
        news.setTitle("标题");
        news.setContent("内容");
        news.setPublishDate(new Date());
        news.setHahahaha(4235234);
        Log.e("news1",news.toString());
        news.save();
        Log.e("news2",news.toString());
        List<News> list=DataSupport.findAll(News.class);
        Log.e("news3",list.toString());

    }
    public native String getStrFromJNI();
}
