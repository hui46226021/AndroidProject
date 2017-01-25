package com.sh.shprojectdemo.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.jereibaselibrary.db.litepal.crud.DataSupport;
import com.jrfunclibrary.base.activity.BaseActivity;

import com.jrfunclibrary.base.activity.BaseListViewActivity;
import com.jruilibarary.widget.RefreshLayout;
import com.jruilibarary.widget.SideslipListView;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.adapter.TestAdapter;
import com.sh.shprojectdemo.model.News;
import com.sh.shprojectdemo.model.User;
import com.sh.shprojectdemo.presenter.UserListPresenter;
import com.xinlan.dragindicator.DragIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UserListActivity extends BaseListViewActivity {

    @InjectView(R.id.listview)
    SideslipListView listview;
    TestAdapter testAdapter;
    List<User> list = new ArrayList<>();
    @InjectView(R.id.refreshlayout)
    RefreshLayout refreshlayout;

    UserListPresenter userListPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.inject(this);
        userListPresenter = new UserListPresenter(list,this);
        initView();
    }

    void initView() {
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

        initListView(listview, refreshlayout, testAdapter);
        setColorSchemeColors(R.color.colorAccent);
    }

    //点击事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    //下拉刷新
    @Override
    public void onRefresh() {
        userListPresenter.onRefresh();
    }

    //上拉加载
    @Override
    public void onLoad() {
        userListPresenter.onLoad();
    }

}
