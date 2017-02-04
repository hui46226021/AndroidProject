package com.sh.shprojectdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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


        String[] title = new String[]{"首页","IM","IM2","我的"};
        int[] images = new int[]{R.drawable.bg_tab_selector_home1,R.drawable.bg_tab_selector_home2,R.drawable.bg_tab_selector_home2,R.drawable.bg_tab_selector_home2};
        tabRadioView.setTabTexts(title,R.drawable.bg_radiobutton_selector_home_page,images);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance());
        fragments.add(IMFragment.newInstance());
        fragments.add(IMFragment.newInstance());
        fragments.add(IMFragment.newInstance());
        tabRadioView.setFragmentList(fragments,R.id.id_fragment_title);
        tabRadioView.setCount(new int[]{0,19,0,2});
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
