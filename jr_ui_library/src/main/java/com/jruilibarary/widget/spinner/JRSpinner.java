package com.jruilibarary.widget.spinner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * 自定义 底部弹出菜单
 * Created by zhush on 2016/9/29.
 * E-mail zhush@jerei.com
 */
public class JRSpinner extends TextView implements SpinnerDialog.SelectedCall {

    private Object value;
    private SpinnerModel spinnerModel;

    List<SpinnerModel> list;//一级列表

    SelectedListener selectedListener;

    public void setSelectedListener(SelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }

    public JRSpinner(Context context) {
        super(context);
    }

    public JRSpinner(final Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                SpinnerDialog spinnerDialog = new SpinnerDialog(JRSpinner.this);
                spinnerDialog.createLoadingDialog(context, list).show();
            }
        });

        setTextColor(Color.rgb(64,130,184));
    }

    public JRSpinner(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    public void selectedCall(SpinnerModel spinnerModel) {
        this.spinnerModel = spinnerModel;
        setText(spinnerModel.getKey());
        if(selectedListener!=null){
            selectedListener.customSpinnerSelected(this,spinnerModel.getKey(), spinnerModel.getValue());
        }
    }

    public Object getValue() {
        return spinnerModel.getValue();
    }
    public String getKey() {
        return spinnerModel.getKey();
    }
    public void setlevel1List(List<SpinnerModel> list) {
        this.list = list;
        setSelectedIndexe(0);

    }
    public void setSelected(int i) {
        for (SpinnerModel spinnerModel : list) {
            if ((int) spinnerModel.getValue() == i) {
                this.spinnerModel = spinnerModel;
                setText(spinnerModel.getKey());
                if(selectedListener!=null){
                    selectedListener.customSpinnerSelected(this,spinnerModel.getKey(), spinnerModel.getValue());
                }
            }
        }
    }

    public void setSelectedIndexe(int i) {
        this.spinnerModel = list.get(i);
        setText(spinnerModel.getKey());
        if(selectedListener!=null){
            selectedListener.customSpinnerSelected(this,spinnerModel.getKey(),spinnerModel.getValue());
        }
    }

    /**
     * 选中监听
     */
    public static interface SelectedListener{
        public void customSpinnerSelected(View view, String key, Object value);
    }
}
