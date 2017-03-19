package com.jrfunclibrary.base.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;


import com.jrfunclibrary.base.view.FormSubmitView;
import com.sh.zsh.code.check.FormCheckInterface;
import com.sh.zsh.code.form.FormInit;
import com.sh.zsh.code.form.FormUtls;


/**
 * Created by zhush on 2016/10/8.
 * E-mail 405086805@qq.com
 */
public class BaseFormActivity extends BaseActivity implements FormSubmitView, FormCheckInterface {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FormInit.deleteInjection(this);
    }

    /**
     * 初始化表单注入
     */
    protected void initFormInjection() {
        FormInit.injection(this);
    }

    /**
     * 表单转换成对象
     */
    protected Object formToObjectAndCheck(Class classz) {
        return FormUtls.formToObjectAndCheck(this, classz);
    }

    /**
     * 对象转表单
     */
    protected void objectToForm(Object object) {
        FormUtls.objectToForm(this, object);
    }

    @Override
    public boolean formCheck(View v) {
        return true;
    }

    /**
     * 通用回调 ，如果不满足需求 在子类里修改即可
     * @param v
     * @param message
     */
    @Override
    public void formCheckParamCall(View v, String message) {
        showMessage(message);
    }
    /**
     * 通用回调 ，如果不满足需求 在子类里修改即可
     * @param v
     * @param message
     */


    /**
     * 通用回调 ，如果不满足需求 在子类里修改即可
     */
    @Override
    public void submitSuccess() {
        closeProgress();
        showAlertDialog("提示", "提交成功", "确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, null, null);
    }
    /**
     * 通用回调 ，如果不满足需求 在子类里修改即可
     */
    @Override
    public void submitfailed(String message) {
        if(TextUtils.isEmpty(message)){
            message = "提交失败";
        }
        closeProgress();
        showAlertDialog("提示", message, "取消", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }, null, null);
    }

    @Override
    public void showProgress(String message) {
        showProgressDialog(message,false);

    }




}
