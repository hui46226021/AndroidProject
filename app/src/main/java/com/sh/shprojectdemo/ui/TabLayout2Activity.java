package com.sh.shprojectdemo.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.ui.fragment.HomeFragment;
import com.sh.shprojectdemo.ui.fragment.IMFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TabLayout2Activity extends AppCompatActivity {

    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout2);
        ButterKnife.inject(this);
        initView();
    }
    TabLayout.Tab tab1;
    TabLayout.Tab tab2;
    void initView(){
        tab1 = tabLayout.newTab().setText("Tab 1");
        tab2 = tabLayout.newTab().setText("Tab 2");
        tabLayout.addTab(tab1);
        tabLayout.addTab(tab2);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                if(tab==tab1){
                   getSupportFragmentManager().beginTransaction()
                            .replace(R.id.id_fragment_title, IMFragment.newInstance(1)).commit();
                }
                if(tab==tab2){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.id_fragment_title, IMFragment.newInstance(2)).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中tab的逻辑
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中tab的逻辑
            }
        });
    }
}
