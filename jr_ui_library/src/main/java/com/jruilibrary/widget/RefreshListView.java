package com.jruilibrary.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.sh.zsh.jr_ui_library.R;

/**
 * Created by jerei on 2017/5/17.
 */

public class RefreshListView extends FrameLayout implements RefreshLayout.OnLoadListener,SwipeRefreshLayout.OnRefreshListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    public RefreshListView(Context context) {
        super(context);
    }

    int page=1;

    RefreshListViewListener refreshListViewListener;

    RefreshLayout refreshLayout;
    ListView listView;

    public RefreshListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_refresh_listview, this);
        refreshLayout = (RefreshLayout) findViewById(R.id.refesh_layout);
        listView = (ListView) findViewById(R.id.listview);
        initVidget();
    }

    void initVidget(){
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadListener(this);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
    }
    public RefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public ListView getListView() {
        return listView;
    }

    public void setRefreshing(boolean bo){
        refreshLayout.setRefreshing(bo);
    }
    /**
     * 设置刷新 圈的颜色
     * @param colorsRes
     */
    public void setColorSchemeColors( @ColorRes int colorsRes){
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), colorsRes));
    }

    public void setAdapter(ListAdapter adapter){
        listView.setAdapter(adapter);

    }

    //刷新
    @Override
    public void onRefresh() {
        if(refreshListViewListener!=null){
            page=1;
            refreshListViewListener.onRefresh(page);
        }
    }
    //加载
    @Override
    public void onLoad() {
        if(refreshListViewListener!=null){
            page=page+1;
            refreshListViewListener.onLoad(page);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(refreshListViewListener!=null){
            page=page+1;
            refreshListViewListener.onItemClick(parent,view,position,id);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if(refreshListViewListener!=null){
            page=page+1;
            refreshListViewListener.onItemLongClick(parent,view,position,id);
        }
        return true;
    }

    /**
     * 监听
     */
    public static interface RefreshListViewListener{
        //刷新
        public void onRefresh(int page) ;
        //加载
        public void onLoad(int page);
        //点击
        public void onItemClick(AdapterView<?> parent, View view, int position, long id);
        //长按
        public void onItemLongClick(AdapterView<?> parent, View view, int position, long id);
    }

    public void setRefreshListViewListener(RefreshListViewListener refreshListViewListener) {
        this.refreshListViewListener = refreshListViewListener;
    }
}
