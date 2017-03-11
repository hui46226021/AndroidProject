package com.jruilibrary.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.sh.zsh.jr_ui_library.R;

/**
 * Created by zhush on 2017/2/3.
 * E-mail 405086805@qq.com
 * PS
 */
public class DownProgressDialog {
    private static Dialog downloadDialog;
    /* 进度条与通知ui刷新的handler和msg常量 */
    private static ProgressBar mProgress;
    public static void show(final Context mContext,String title){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress, null);
        mProgress = (ProgressBar)v.findViewById(R.id.progress);

        builder.setView(v);
        builder.setNegativeButton("后台下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        downloadDialog = builder.create();
        downloadDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        downloadDialog.show();

    }

    public static void setProgress(int progress){
        mProgress.setProgress(progress);
    }

    public static void dismiss(){
        downloadDialog.dismiss();
    }
}
