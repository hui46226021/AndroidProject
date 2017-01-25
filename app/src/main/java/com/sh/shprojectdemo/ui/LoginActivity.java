package com.sh.shprojectdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jereibaselibrary.image.JRSetImage;
import com.jrfunclibrary.base.activity.BaseActivity;
import com.jruilibarary.widget.CircleImageView;
import com.jruilibarary.widget.TemplateTitleBar;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.model.User;
import com.sh.shprojectdemo.presenter.LoginPresenter;
import com.sh.shprojectdemo.view.LoginView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

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
    public static String LOGIN_USER = "login_user";
    LoginPresenter loginPresenter;
    @InjectView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        loginPresenter = new LoginPresenter(this);
        initView();
    }

    void initView() {
        template.setMoreTextContextAction("注册", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "嗯，这时候应该弹出注册页面", Toast.LENGTH_SHORT).show();
            }
        });
        String url = "http://ww2.sinaimg.cn/bmiddle/43a39d58gw1ebqjvjr5onj20ea0e1ach";
        JRSetImage.setNetWorkImage(this, url, image);
    }

    @OnClick(R.id.button)
    void pageOnClick(){
        loginPresenter.login(account.getText()+"",pwd.getText()+"");
    }
    @Override
    public void loginSuccess(User user) {

        Intent intent = getIntent().putExtra(LOGIN_USER, user);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void loginfail(String message) {
        showMessage(message);
    }
}
