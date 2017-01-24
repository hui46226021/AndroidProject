package com.sh.shprojectdemo.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.jereibaselibrary.db.litepal.crud.DataSupport;
import com.jrfunclibrary.base.activity.BaseActivity;

import com.jruilibarary.widget.RefreshLayout;
import com.jruilibarary.widget.SideslipListView;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.adapter.TestAdapter;
import com.sh.shprojectdemo.model.News;
import com.xinlan.dragindicator.DragIndicatorView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ListViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        RefreshLayout.OnLoadListener{

    @InjectView(R.id.listview)
    SideslipListView listview;
    TestAdapter testAdapter;
    List<News> list;
    @InjectView(R.id.refreshlayout)
    RefreshLayout refreshlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.inject(this);

        list = DataSupport.findAll(News.class);
        testAdapter = new TestAdapter(list, this);
        testAdapter.setButtonCall(new TestAdapter.ButtonCall() {
            @Override
            public void delete(int i) {
                list.remove(i);
                testAdapter.notifyDataSetChanged();
                listview.turnToNormal();//归为
            }

            @Override
            public void read(DragIndicatorView count, int i) {
                listview.turnToNormal();//归为
                count.dismissView();
            }
        });
        listview.setAdapter(testAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listview.canClick()) {

                }

            }
        });


        refreshlayout.setOnRefreshListener(this);
        refreshlayout.setOnLoadListener(this);
        refreshlayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorAccent));
    }

    @Override
    public void onRefresh() {
        new Timer().schedule(new TimerTask() {//延时250毫秒刷新
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshlayout.setRefreshing(false);

                    }
                });
            }
        }, 1500);
    }

    @Override
    public void onLoad() {
        new Timer().schedule(new TimerTask() {//延时250毫秒刷新
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshlayout.setRefreshing(false);

                    }
                });
            }
        }, 1500);
    }
}
