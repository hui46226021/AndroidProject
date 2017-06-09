package com.jrfunclibrary.base.fragment;

import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jrfunclibrary.base.fragment.BaseFragment;
import com.jrfunclibrary.base.view.RefreshLayoutView;
import com.jruilibrary.widget.RefreshLayout;
import com.sh.zsh.jrfunclibrary.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/4/8.
 */
@Deprecated
public class BaseListFragment extends BaseFragment implements
        RefreshLayout.OnLoadListener,AdapterView.OnItemClickListener,
        RefreshLayoutView {

    ListView listview;
    RefreshLayout swiperefreshlayout;
    BaseAdapter baseAdapter;
    @Deprecated
    public   void initListView(ListView listView, final RefreshLayout swiperefreshlayout, BaseAdapter baseAdapter){
        this.listview =listView;
        this.swiperefreshlayout =swiperefreshlayout;
        this.baseAdapter =baseAdapter;

        listview.setAdapter(baseAdapter);
        listview.setOnItemClickListener(this);
        swiperefreshlayout.setOnLoadListener(this);
        swiperefreshlayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.top_tab_color));

        new Timer().schedule(new TimerTask() {//延时250毫秒刷新
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swiperefreshlayout.setRefreshing(true);
                        onRefresh(1);
                    }
                });
            }
        }, 500);
    }


    /**
     * 设置刷新 圈的颜色
     * @param colorsRes
     */
    @Deprecated
    public void setColorSchemeColors( @ColorRes int colorsRes){
        swiperefreshlayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), colorsRes));
    }


    @Override
    public void onStart() {
        super.onStart();

    }
    //点击事件
    @Override
    @Deprecated
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    //下拉刷新
    @Override
    @Deprecated
    public void onRefresh(int page) {

    }
    //上拉加载
    @Override
    @Deprecated
    public void onLoad(int page) {

    }
    //获取数据成功
    @Override
    @Deprecated
    public void getDataSuccess(Object object) {

        baseAdapter.notifyDataSetChanged();
        new Timer().schedule(new TimerTask() {//延时250毫秒刷新
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //关闭动画
                        swiperefreshlayout.setRefreshing(false);
                    }
                });
            }
        }, 500);
    }

    //获取数据失败
    @Override
    @Deprecated
    public void getDataFail(String failMessage) {
        //关闭动画
        swiperefreshlayout.setRefreshing(false);
        showMessage(failMessage);

    }
}
