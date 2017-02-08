package com.sh.shprojectdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jrfunclibrary.base.activity.BaseActivity;
import com.jruilibarary.widget.layerListView.LayerListView;
import com.jruilibarary.widget.layerListView.TreeListViewAdapter;
import com.jruilibarary.widget.layerListView.model.Node;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.model.FileBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LayerListViewActivity extends BaseActivity {

    @InjectView(R.id.layerListView)
    LayerListView layerListView;
    private List<FileBean> mDatas = new ArrayList<FileBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layer_list_view);
        ButterKnife.inject(this);

        initDatas();
        layerListView = (LayerListView) findViewById(R.id.layerListView);
        layerListView.setmDatas(mDatas);

        layerListView.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener()
        {
            @Override
            public void onClick(Node node, int position)
            {
                if (node.isLeaf())
                {
                    Toast.makeText(getApplicationContext(), node.getName(),
                            Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void initDatas()
    {

        mDatas.add(new FileBean(2, 1, "游戏"));
        mDatas.add(new FileBean(3, 1, "文档"));
        mDatas.add(new FileBean(4, 1, "程序"));
        mDatas.add(new FileBean(5, 2, "war3"));
        mDatas.add(new FileBean(6, 2, "刀塔传奇"));

        mDatas.add(new FileBean(7, 4, "面向对象"));
        mDatas.add(new FileBean(8, 4, "非面向对象"));

        mDatas.add(new FileBean(9, 7, "C++"));
        mDatas.add(new FileBean(10, 7, "JAVA"));
        mDatas.add(new FileBean(11, 7, "Javascript"));
        mDatas.add(new FileBean(12, 8, "C"));
        mDatas.add(new FileBean(15, 5, "war31"));
        mDatas.add(new FileBean(16, 5, "war32"));
        mDatas.add(new FileBean(17, 15, "war33"));
        mDatas.add(new FileBean(18, 15, "war35"));
        mDatas.add(new FileBean(19, 17, "war37"));
        mDatas.add(new FileBean(20, 17, "war38"));

    }
}
