package com.sh.shprojectdemo.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.adapter.FragmentAdapter;
import com.sh.shprojectdemo.ui.fragment.IMFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TabLayoutActivity extends AppCompatActivity {

    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    List<Fragment> fragments = new ArrayList<>();
    private List<String> titles= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.inject(this);
        initView();
    }
    void initView(){
        fragments.add(IMFragment.newInstance(1));
        fragments.add(IMFragment.newInstance(2));
        fragments.add(IMFragment.newInstance(3));
        fragments.add(IMFragment.newInstance(4));
        fragments.add(IMFragment.newInstance(5));
        fragments.add(IMFragment.newInstance(6));
        fragments.add(IMFragment.newInstance(7));
        fragments.add(IMFragment.newInstance(8));
        fragments.add(IMFragment.newInstance(9));
        fragments.add(IMFragment.newInstance(10));
        titles.add("第一个");
        titles.add("第二个");
        titles.add("第三个");
        titles.add("第四个");
        titles.add("第五个");
        titles.add("第六个");
        titles.add("第七个");
        titles.add("第八个");
        titles.add("第九个");
        titles.add("第十个");

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(fragmentAdapter);
    }
}
