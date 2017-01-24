package com.sh.shprojectdemo.ui;

import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jrfunclibrary.base.activity.BaseActivity;
import com.jruilibarary.widget.CircleImageView;
import com.jruilibarary.widget.TemplateTitleBar;
import com.sh.shprojectdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends BaseActivity {

    @InjectView(R.id.template)
    TemplateTitleBar template;
    @InjectView(R.id.image)
    CircleImageView image;
    @InjectView(R.id.account)
    EditText account;
    @InjectView(R.id.pwd)
    EditText pwd;
    @InjectView(R.id.activity_login)
    LinearLayout activityLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        initView();
    }

    void initView(){
        template.setMoreTextContextAction("注册", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this,"注册页面",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
