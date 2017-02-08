package com.sh.shprojectdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jrfunclibrary.base.activity.BaseActivity;
import com.jruilibarary.widget.TabRadioView;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.common.cache.TemporaryCache;
import com.sh.shprojectdemo.model.User;
import com.sh.shprojectdemo.ui.fragment.HomeFragment;
import com.sh.shprojectdemo.ui.fragment.IMFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity {


    @InjectView(R.id.id_fragment_title)
    FrameLayout idFragmentTitle;

    @InjectView(R.id.tabRadioView)
    TabRadioView tabRadioView;
    @InjectView(R.id.navigation_view)
    NavigationView navigationView;
    private int LOGIN_REQUESTCODE = 10001;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        user = TemporaryCache.getUserSession();
        if (user == null) {
            startActivityForResult(new Intent(this, LoginActivity.class), LOGIN_REQUESTCODE);
            return;
        }
        initView();
    }
    void initView() {
        initBottomNavig();
        initRightNavig();
    }

    /**
     * 初始化底部导航
     */
    void initBottomNavig(){
        String[] title = new String[]{"首页", "IM", "IM2", "我的"};
        int[] images = new int[]{R.drawable.bg_tab_selector_home1, R.drawable.bg_tab_selector_home2, R.drawable.bg_tab_selector_home2, R.drawable.bg_tab_selector_home2};
        tabRadioView.setTabTexts(title, R.drawable.bg_radiobutton_selector_home_page, images);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(IMFragment.newInstance(1));
        fragments.add(IMFragment.newInstance(1));
        fragments.add(IMFragment.newInstance(1));
        tabRadioView.setFragmentList(fragments, R.id.id_fragment_title);
        tabRadioView.setCount(new int[]{0, 19, 0, 2});
    }

    /**
     * 初始化右侧导航菜单
     */
    void initRightNavig(){
        //让 navigationView 菜单的图标 以 本来的图片颜色显示
        navigationView.setItemIconTintList(null);
        //navigationView 菜单的点击时间
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.one:
                        showMessage("点击了第一个");
                        break;
                    case R.id.two:
                        showMessage("点击了第二个");
                        break;
                    case R.id.three:
                        showMessage("点击了第三个");
                        break;
                    case R.id.four:
                        showMessage("点击了第四个");
                        break;
                }

                return false;
            }
        });
        //获取 头部视图
        View headerView = navigationView.getHeaderView(0);
        ImageView imageView= (ImageView) headerView.findViewById(R.id.image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("点击了猫头");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                user = (User) data.getSerializableExtra(LoginActivity.LOGIN_USER);
                initView();
            } else {
                finish();
            }
        }
    }

}
