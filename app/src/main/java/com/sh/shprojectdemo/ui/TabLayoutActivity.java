package com.sh.shprojectdemo.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.adapter.FragmentAdapter;
import com.sh.shprojectdemo.ui.fragment.DataBindingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TabLayoutActivity extends AppCompatActivity {

    @InjectView(R.id.tab_layout)
    TabLayout tabLayout;
    @InjectView(R.id.view_pager)
    ViewPager viewPager;
    @InjectView(R.id.tab_layout2)
    TabLayout tabLayout2;
    @InjectView(R.id.view_pager2)
    ViewPager viewPager2;
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
        fragments.add(DataBindingFragment.newInstance(1));
        fragments.add(DataBindingFragment.newInstance(2));
        fragments.add(DataBindingFragment.newInstance(3));
        fragments.add(DataBindingFragment.newInstance(4));
        fragments.add(DataBindingFragment.newInstance(5));
        fragments.add(DataBindingFragment.newInstance(6));
        fragments.add(DataBindingFragment.newInstance(7));
        fragments.add(DataBindingFragment.newInstance(8));
        fragments.add(DataBindingFragment.newInstance(9));
        fragments.add(DataBindingFragment.newInstance(10));
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


        List<Fragment> fragments2 = new ArrayList<>();
         List<String> titles2= new ArrayList<>();

        fragments2.add(DataBindingFragment.newInstance(10));
        fragments2.add(DataBindingFragment.newInstance(20));
        fragments2.add(DataBindingFragment.newInstance(30));
        fragments2.add(DataBindingFragment.newInstance(40));
        fragments2.add(DataBindingFragment.newInstance(50));

        titles2.add("一个");
        titles2.add("二个");
        titles2.add("三个");
        titles2.add("四个");
        titles2.add("五个");


        FragmentAdapter  fragmentAdapter2 = new FragmentAdapter(getSupportFragmentManager(), fragments2, titles2);
        tabLayout2.setTabsFromPagerAdapter(fragmentAdapter2);
        tabLayout2.setupWithViewPager(viewPager2);
        viewPager2.setOffscreenPageLimit(0);
        viewPager2.setAdapter(fragmentAdapter2);
    }
}
