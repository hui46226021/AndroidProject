package com.jrfunclibrary.base.fragment;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.jrfunclibrary.base.activity.BaseActivity;
import com.jrfunclibrary.base.view.BaseView;
import com.jruilibarary.widget.IOSAlertDialog;
import com.jruilibarary.widget.MyProgressDialog;
import com.sh.zsh.jrfunclibrary.R;


/**
 * Created by Administrator on 2016/9/14.
 */
public class BaseFragment extends Fragment implements BaseView {
    /**
     * 显示进度对话框
     */
    public void showProgressDialog(String message) {
        MyProgressDialog.show(getActivity(), message,true);
    }

    /**
     * 关闭进度对话框
     */
    public void closeProgressDialog() {
        MyProgressDialog.dismiss();
    }

    /**
     * 自定义对话框
     *
     * @param tital
     * @param message
     * @param button1
     * @param listener
     * @param button2
     * @param listener2
     */
    public void showAlertDialog(String tital, String message, String button1, View.OnClickListener listener, String button2, View.OnClickListener listener2) {

        new IOSAlertDialog(getActivity()).builder().setTitle(tital)
                .setMsg(message)
                .setPositiveButton(button1, listener).setNegativeButton(button2, listener2).show();
    }

    /**
     * 弹出吐丝
     *
     * @param message
     */
    public void setToastMessage(String message, int i) {
        Toast toast = Toast.makeText(getActivity(), message, i);
//        toast.setGravity(Gravity.CENTER,30,30);
        toast.show();
    }


    @Override
    public void startActivity(Intent intent) {
        ((BaseActivity)getActivity()).startActivity(intent);
    }

    /**
     * 帶动画 切换
     *
     * @param intent
     * @param nimation
     */
    public void startActivity(Intent intent, BaseActivity.Animation nimation) {
        ((BaseActivity)getActivity()).startActivity(intent,nimation);


    }


    public void finish() {
        ((BaseActivity)getActivity()).finish();

    }

    @Override
    public void showProgress(String message) {
        showProgressDialog(message);
    }

    @Override
    public void closeProgress() {
        closeProgressDialog();
    }

    @Override
    public void showMessage(String message) {
        closeProgressDialog();
        setToastMessage(message,Toast.LENGTH_LONG);
    }

    @Override
    public void noNetwork() {

    }
    @Override
    public void offLine() {

    }
}
