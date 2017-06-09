package com.jruilibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.sh.zsh.jr_ui_library.R;

import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by zhush on 17-6-9 下午2:59
 * Email:405086805@qq.com
 * Ps:
 * 公有函数
 * setRefreshing   设置刷新动画 同时出发 onRefresh（会延时500毫秒）
 * setColorSchemeColors  设置刷新 圈的颜色
 * setAdapter
 * setRefreshListViewListener  设置监听  并同时 刷新数据
 */
public class RefreshListView extends FrameLayout implements RefreshLayout.OnLoadListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    public RefreshListView(Context context) {
        super(context);
    }

    RefreshListViewListener refreshListViewListener;
    /**
     * 下拉组件
     */
    RefreshLayout refreshLayout;
    TextView nodataTextView;
    ListView listView;
    public RefreshListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_refresh_listview, this);
        refreshLayout = (RefreshLayout) findViewById(R.id.refesh_layout);
        listView = (ListView) findViewById(R.id.refreshLayout_listview);
        nodataTextView = (TextView)findViewById(R.id.no_data);
        initVidget();
    }

   private void initVidget(){
        refreshLayout.setOnLoadListener(this);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        refreshLayout.setColorSchemeColors(Color.rgb(229,1,18));
    }
    private RefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public ListView getListView() {
        return listView;
    }

    /**
     * 设置刷新动画 同时出发 onRefresh（会延时500毫秒）
     * @param bo
     */
    public void setRefreshing(final boolean bo){

        new Timer().schedule(new TimerTask() {//延时250毫秒刷新
            @Override
            public void run() {
                ((Activity) getContext()). runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(bo);
                        if(bo){
                            onRefresh(1);
                        }else {
                            notifyDataSetChanged();
                        }

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
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), colorsRes));
    }

    public void setAdapter(BaseAdapter adapter){
        listView.setAdapter(adapter);
    }

    //刷新
    @Override
    public void onRefresh(int page) {
        if(refreshListViewListener!=null){
            refreshListViewListener.onRefresh(true,page);
        }
    }
    //加载
    @Override
    public void onLoad(int page) {
        if(refreshListViewListener!=null){

            refreshListViewListener.onRefresh(false,page);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(refreshListViewListener!=null){
            refreshListViewListener.onItemClick(parent,view,position,id);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if(refreshListViewListener!=null){
            refreshListViewListener.onItemLongClick(parent,view,position,id);
        }
        return true;
    }

    /**
     * 监听
     */
    public static interface RefreshListViewListener{
        //刷新
        public void onRefresh(boolean isRefresh,int page) ;
        //点击
        public void onItemClick(AdapterView<?> parent, View view, int position, long id);
        //长按
        public void onItemLongClick(AdapterView<?> parent, View view, int position, long id);
    }

    /**
     * 设置监听  并同时 刷新数据
     * @param refreshListViewListener
     */
    public void setRefreshListViewListener(RefreshListViewListener refreshListViewListener) {
        this.refreshListViewListener = refreshListViewListener;
        setRefreshing(true);
    }

    /**
     * 刷新数据
     */
    private void notifyDataSetChanged(){

        try {
            ((BaseAdapter)(listView.getAdapter())).notifyDataSetChanged();
            if(listView.getAdapter().getCount()==0){
                nodataTextView.setVisibility(VISIBLE);
            }else {
                nodataTextView.setVisibility(GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
