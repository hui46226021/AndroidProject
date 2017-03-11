package com.jruilibrary.widget.layerListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jruilibrary.widget.NoScrollGridView;
import com.jruilibrary.widget.layerListView.model.Node;
import com.sh.zsh.jr_ui_library.R;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhush on 2017/2/8.
 * E-mail 405086805@qq.com
 * PS 层级展示listView
 */
public class LayerListView <T>  extends LinearLayout{
    private Context mContext;
    public LayerListView(Context context) {
        super(context);
    }

    private NoScrollGridView gridView;
    private ListView mTree;
    private TreeListViewAdapter mAdapter;
    private TreeListViewAdapter.OnTreeNodeClickListener  onTreeNodeClickListener;
    private List<Node> gridlist = new ArrayList<>();
    private LayerGridViewAdapter layerGridViewAdapter;
    public LayerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext =context;
        LayoutInflater.from(context).inflate(R.layout.widget_layerlist, this);
        mTree = (ListView) findViewById(R.id.id_tree);
        gridView = (NoScrollGridView) findViewById(R.id.grid_view);
        gridlist.add(0,new Node(999999,999999,"根目录"));
        layerGridViewAdapter = new LayerGridViewAdapter(context,gridlist);
        gridView.setAdapter(layerGridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==gridlist.size()-1){
                    return;
                }
                Node node = gridlist.get(i);
                if(node.getId()==999999){//回到根目录
                    mAdapter.closeList();
                    mAdapter.notifyDataSetChanged();
                    gridlist.clear();
                    layerGridViewAdapter.notifyDataSetChanged();
                }else {
                    mAdapter.setSelected(node);
                    removeGridList(i);
                    layerGridViewAdapter.notifyDataSetChanged();
                }


            }
        });

    }

    /**
     * 递归删除 gridList后面的数据
     * @param i
     */
    public void removeGridList(int i){
        if(gridlist.size()>i+1){
            gridlist.remove(i+1);
            removeGridList(i);
        }

    }

    /**
     * 设置集合列表
     * @param mDatas
     */
    public void setmDatas(List<T> mDatas) {

        try
        {
            mAdapter = new MyTreeAdapter<T>(mTree, mContext, mDatas, 10);
            mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    if(onTreeNodeClickListener!=null){
                        onTreeNodeClickListener.onClick(node,position);
                    }

                    gridlist.clear();
                    gridAddNode(node);
                    layerGridViewAdapter.notifyDataSetChanged();
                }
            });

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        mTree.setAdapter(mAdapter);
        mAdapter.closeList();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 递归添加父节点
     * @param node
     */
    public void gridAddNode(Node node){
        if(node!=null){
            gridlist.add(0,node);
            gridAddNode(node.getParent());
        }

    }

    /**
     * 设置点击事件
     * @param onTreeNodeClickListener
     */
    public void setOnTreeNodeClickListener(TreeListViewAdapter.OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }
}
