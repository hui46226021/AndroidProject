package com.sh.shprojectdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jereibaselibrary.db.litepal.crud.DataSupport;
import com.jereibaselibrary.netowrk.HttpUtils;
import com.jereibaselibrary.netowrk.retrofit2.BaseCallModel;
import com.jereibaselibrary.netowrk.retrofit2.MyCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.FormBody;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("demo");
    }

    @InjectView(R.id.one_headle)
    Button oneHeadle;
    @InjectView(R.id.all_headle)
    Button allHeadle;
    @InjectView(R.id.add_param)
    Button addParam;
    @InjectView(R.id.add_bean)
    Button addBean;
    @InjectView(R.id.add_list)
    Button addList;
    @InjectView(R.id.fs)
    Button fs;
    @InjectView(R.id.activity_main)
    LinearLayout activityMain;


    HttpUtils httpUtils;
    News news;
    List<News> list;
    @InjectView(R.id.sx)
    Button sx;
    @InjectView(R.id.qm)
    Button qm;

    String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Log.e("打印", getStrFromJNI());


        news = new News();
        news.setId(5);
        news.setCommentCount(200);
        news.setTitle("标题");
        news.setContent("内容");
        news.setPublishDate(new Date());
        news.setHahahaha(4235234);
        Log.e("news1", news.toString());
        news.save();
        Log.e("news2", news.toString());
        list = DataSupport.findAll(News.class);
        Log.e("news3", list.toString());
        httpUtils = new HttpUtils("http://www.zhihu.com/login/email");
        httpUtils.putParam("username", "wangyt");
        httpUtils.putParam("password", "test1234");
    }
    String xsrf;
    @OnClick({R.id.one_headle, R.id.all_headle, R.id.add_param, R.id.add_bean, R.id.add_list, R.id.fs, R.id.sx,R.id.qm})
    public void pageOnClick(View v) {
        switch (v.getId()) {
            case R.id.one_headle:
                httpUtils.addHeader("one_headle", "one_headle");
                break;
            case R.id.add_param:
                httpUtils.putParam("add_param", "add_param");
                break;
            case R.id.add_bean:
                httpUtils.putBean("news", news);
                break;
            case R.id.add_list:
                httpUtils.putList("newslist", list);
                break;
            case R.id.fs:
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        httpUtils.post();
//                    }
//                }).start();


                GitHubAPI gitHubAPI = HttpUtils.getRetrofit().create(GitHubAPI.class);


                Call<BaseCallModel<News>> userCall = gitHubAPI.userInfo(news.getTitle());

                userCall.enqueue(new MyCallback<BaseCallModel<News>>() {
                    @Override
                    public void onSuc(Response<BaseCallModel<News>> response) {

                    }

                    @Override
                    public void onFail(String message) {

                    }

                });

                break;
            case R.id.sx:
                httpUtils = new HttpUtils("http://www.zhihu.com/login/email");



                break;
            case R.id.qm:
              final   HttpUtils   httpUtils2 = new HttpUtils("https://www.zhihu.com/#signin");
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                       String resp= httpUtils2.post();
//                        Document parse = Jsoup.parse(resp);
//                        Elements select = parse.select("input[type=hidden]");
//                        Element element = select.get(0);
//                         xsrf = element.attr("value");
                    }
                }).start();
                break;
        }
    }

    public native String getStrFromJNI();
}
