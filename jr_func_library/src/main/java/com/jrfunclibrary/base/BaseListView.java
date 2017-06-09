package com.jrfunclibrary.base;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;


import com.jereibaselibrary.netowrk.HttpAsynTask;
import com.jereibaselibrary.netowrk.HttpUtils;
import com.jereibaselibrary.netowrk.listen.HandleResponse;
import com.jereibaselibrary.netowrk.listen.RequestCall;
import com.jereibaselibrary.tools.JRDataResult;
import com.jruilibrary.widget.RefreshListView;
import com.jruilibrary.widget.UISearchView;
import com.sh.zsh.jrfunclibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by zhush on 17-6-9.
 * Email:405086805@qq.com
 * Ps:  继承次类的自定义控件
 *      重写getView 和 responseHanle 方法 可实现 列表页
 *      查询数据 已经封装在里面
 * 公有函数
 * setOnItemClickListener  设置点击事件
 * setOnItemLongClickListener 设计长按点击事件
 * getItem  获取单行实体
 * putParam 设置参数
 * setRefreshing 设置刷新
 * setUrl  设置查询路径
 */

public abstract class BaseListView<T>extends FrameLayout implements RefreshListView.RefreshListViewListener{


    public Context context;//上下文
    public  String url;//获取数据的url
    public UISearchView uiSearchView;//搜索控件
    public HashMap<String,Object> paramMap =new HashMap<>();//参数集合
    public List<T> dataList = new ArrayList<T>();//数据集合
    public RefreshListView listView;//下拉刷新上拉加载组件

    /**
     * 适配器
     */
    BaseAdapter baseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(0);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return BaseListView.this.getView(i,view,viewGroup);
        }

    };

    /**
     * 子类调用的构造函数
     * @param context
     * @param attrs
     */
    public BaseListView( @NonNull Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);
        this.context = context;
        initView();
    }

    /**
     * 初始化控件
     */
   private void initView(){
        LayoutInflater.from(context).inflate(R.layout.comm_list_view, this);
        uiSearchView = (UISearchView) findViewById(R.id.search);
        listView = (RefreshListView) findViewById(R.id.listview);
        listView.setAdapter(baseAdapter);
        listView.setRefreshListViewListener(this);
    }





    /**
     * 长按事件  可在子类中重写 或者在外部设置
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 刷新数据
     * @param isRefresh  是否是刷新
     * @param page 页数
     */
    @Override
    public void onRefresh(final boolean isRefresh, int page) {
        HttpAsynTask httpAsynTask = new HttpAsynTask(url);
        httpAsynTask.putParam("page", page);
        httpAsynTask.putParam("keyWord", uiSearchView.getContent());
        httpAsynTask.putParam("rows", 20);
        httpAsynTask.putParam("order", "desc");
        //循环 外部设置的参数
        Set<String> keys = paramMap.keySet();
        for(String key:keys){
            httpAsynTask.putParam(key, paramMap.get(key));
        }

        httpAsynTask.setHttpRequestCall(new RequestCall<List<T>>() {
            @Override
            public void success(List<T> dataResult) {
                if(isRefresh){
                    dataList.clear();
                }
                dataList.addAll(dataResult);
                listView.setRefreshing(false);
            }

            @Override
            public void failed(String message, int errorCode) {
                listView.setRefreshing(false);
            }
        });
        httpAsynTask.setHandleResponse(new HandleResponse() {
            @Override
            public JRDataResult putResponse(JRDataResult dataControlResult, HttpUtils client) {
                return responseHanle(dataControlResult,client);
            }
        });
        httpAsynTask.execute();
    }

    /**
     * BaseAdapter 的 getView 方法
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    protected abstract View getView(int i, View view, ViewGroup viewGroup);

    /**
     * 处理 网络获取到的数据
     * @param dataControlResult
     * @param client
     * @return
     */
    protected abstract JRDataResult responseHanle(JRDataResult dataControlResult, HttpUtils client);

    /**
     * 设置点击事件
     * @param listener
     */
   public void setOnItemClickListener(@Nullable AdapterView.OnItemClickListener listener){
       listView.getListView().setOnItemClickListener(listener);
   }

    /**
     * 设置长按你事件
     * @param listener
     */

    public void setOnItemLongClickListener(@Nullable AdapterView.OnItemLongClickListener listener){
        listView.getListView().setOnItemLongClickListener(listener);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 获取列表单个对象
     * @param i
     * @return
     */
   public T getItem(int i){
        return dataList.get(i);
    }

    /**
     * 设置其他参数
     */
    public void putParam(String key,Object object){
        paramMap.put(key,object);
    }

    /**
     * 设置url
     */
    public void setUrl(String url){
        this.url =url;
    }

    /**
     * 刷新数据
     * @param refre
     */
    public void   setRefreshing(boolean refre){
        listView.setRefreshing(refre);
    }
}
