package com.sh.shprojectdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.jereibaselibrary.db.litepal.crud.DataSupport;
import com.jruilibarary.widget.SideslipListView;
import com.xinlan.dragindicator.DragIndicatorView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ListViewActivity extends AppCompatActivity {

    @InjectView(R.id.listview)
    SideslipListView listview;
    TestAdapter testAdapter;
    List<News> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        ButterKnife.inject(this);

        list = DataSupport.findAll(News.class);
        testAdapter = new TestAdapter(list,this);
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
    }
}
