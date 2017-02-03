package com.sh.shprojectdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jrfunclibrary.base.activity.BaseActivity;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.common.cache.TemporaryCache;
import com.sh.shprojectdemo.model.User;
import com.sh.shprojectdemo.ui.fragment.HomeFragment;
import com.sh.shprojectdemo.ui.fragment.IMFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{


    @InjectView(R.id.id_fragment_title)
    FrameLayout idFragmentTitle;
    @InjectView(R.id.homepage)
    RadioButton homepage;
    @InjectView(R.id.classification)
    RadioButton classification;
    @InjectView(R.id.radio_group)
    RadioGroup radioGroup;
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

    void initView(){
        radioGroup.setOnCheckedChangeListener(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.id_fragment_title, HomeFragment.newInstance()).commit();
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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        if (checkedId == R.id.homepage) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.id_fragment_title, HomeFragment.newInstance()).commit();

        }
        if (checkedId == R.id.classification) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.id_fragment_title,new  IMFragment()).commit();

        }
    }
}
