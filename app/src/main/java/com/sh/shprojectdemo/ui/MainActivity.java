package com.sh.shprojectdemo.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jereibaselibrary.cache.OwnCache;
import com.jereibaselibrary.db.litepal.crud.DataSupport;
import com.jereibaselibrary.image.JRBitmapUtils;
import com.jereibaselibrary.netowrk.HttpUtils;
import com.jereibaselibrary.netowrk.retrofit2.BaseCallModel;
import com.jereibaselibrary.netowrk.retrofit2.MyCallback;
import com.jereibaselibrary.tools.JRDensityUtil;
import com.jrfunclibrary.base.activity.BaseActivity;
import com.jruilibarary.widget.UISearchView;
import com.jruilibarary.widget.lineformview.LineFromView;
import com.jruilibarary.widget.lineformview.ViewData;
import com.sh.shprojectdemo.GitHubAPI;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.common.cache.TemporaryCache;
import com.sh.shprojectdemo.model.News;
import com.sh.shprojectdemo.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends BaseActivity {


    private int LOGIN_REQUESTCODE=10001;
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
    @InjectView(R.id.searchview)
    UISearchView searchview;
    @InjectView(R.id.work_type)
    LineFromView workType;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        user = TemporaryCache.getUserSession();
        if(user==null){
            startActivityForResult(new Intent(this,LoginActivity.class),LOGIN_REQUESTCODE);
            return;
        }

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


        ArrayList<ViewData> options1Items  = new ArrayList<>();
        options1Items.add(new ViewData("第一个",1));
        options1Items.add(new ViewData("第二个",2));
        options1Items.add(new ViewData("第三个",3));
        options1Items.add(new ViewData("第四个",4));
        options1Items.add(new ViewData("第五个",5));
        workType.setpvOptionsList(options1Items);
        workType.setSelectedListener(new LineFromView.SelectedListener() {
            @Override
            public void pvOptions(String key, Object value) {
                Toast.makeText(MainActivity.this,"key="+key+"  value="+value.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    String xsrf;

    @OnClick({R.id.one_headle, R.id.all_headle, R.id.add_param, R.id.add_bean, R.id.add_list, R.id.fs, R.id.sx, R.id.qm})
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
//                httpUtils = new HttpUtils("http://www.zhihu.com/login/email");

                searchview.setVisibility(View.GONE);
                break;
            case R.id.qm:

                // 设置控件允许绘制缓存
//                activityMain.setDrawingCacheEnabled(true);
//                // 获取控件的绘制缓存（快照）
//                Bitmap bitmap = activityMain.getDrawingCache();
//                String viewName = getClass().getSimpleName();
//                LetterListViewActivity.setViewCache(bitmap,viewName);
                // 保存截取的快照
                Intent intent= new Intent(this,LetterListViewActivity.class);
//                intent.putExtra(LetterListViewActivity.VIEW_NAME, viewName);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==LOGIN_REQUESTCODE){
            if(resultCode==RESULT_OK){
                user= (User) data.getSerializableExtra(LoginActivity.LOGIN_USER);
            }else {
                finish();
            }
        }
    }
}
