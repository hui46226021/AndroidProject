package com.jrfunclibrary.base.activity;

import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;


import com.jrfunclibrary.base.view.RefreshLayoutView;
import com.jruilibrary.widget.RefreshLayout;
import com.sh.zsh.jrfunclibrary.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhush on 2016/11/15.
 * E-mail 405086805@qq.com
 * PS 列表页面 基类 主要是 初始化 listView 和绑定时事件
 */
public class BaseListViewActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        RefreshLayout.OnLoadListener,AdapterView.OnItemClickListener,
        RefreshLayoutView {

    ListView listview;
    RefreshLayout swiperefreshlayout;
    BaseAdapter baseAdapter;

   public   void initListView(ListView listView,final RefreshLayout swiperefreshlayout,BaseAdapter baseAdapter){
       this.listview =listView;
       this.swiperefreshlayout =swiperefreshlayout;
       this.baseAdapter =baseAdapter;

         listview.setAdapter(baseAdapter);
         listview.setOnItemClickListener(this);
         swiperefreshlayout.setOnRefreshListener(this);
         swiperefreshlayout.setOnLoadListener(this);
         swiperefreshlayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.top_tab_color));

       new Timer().schedule(new TimerTask() {//延时250毫秒刷新
           @Override
           public void run() {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       swiperefreshlayout.setRefreshing(true);
                       onRefresh();
                   }
               });
           }
       }, 500);
     }


    /**
     * 设置刷新 圈的颜色
     * @param colorsRes
     */
    public void setColorSchemeColors( @ColorRes int colorsRes){
        swiperefreshlayout.setColorSchemeColors(ContextCompat.getColor(this, colorsRes));
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
    //点击事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    //下拉刷新
    @Override
    public void onRefresh() {

    }
    //上拉加载
    @Override
    public void onLoad() {

    }
    //获取数据成功
    @Override
    public void getDataSuccess(Object object) {


        //关闭动画
        swiperefreshlayout.setRefreshing(false);
        baseAdapter.notifyDataSetChanged();
    }

    //获取数据失败
    @Override
    public void getDataFail(String failMessage) {
        //关闭动画
        swiperefreshlayout.setRefreshing(false);
        showMessage(failMessage);

    }
}
