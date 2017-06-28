package com.jruilibrary.form.utils;

import android.view.View;

import com.jruilibrary.form.layout.FormRowView;
import com.sh.zsh.jr_ui_library.R;

/**
 * Created by zhush on 2017/6/23.
 * Email 405086805@qq.com
 * 表单行属性控件
 */

public class FormRowUtils {
    /**
     * 该行 是否可用
     * @param v
     * @param enablde
     */
    public static    void  enabled(View v,boolean enablde){
        FormRowView formRowView= (FormRowView) v.getTag(R.id.form_row_id);
        formRowView.setEnabled(enablde);
    }

    /**
     * 隐藏显示表单行控件
     * @param v
     * @param vis
     */
    public static  void visibility(View v ,boolean vis){
        FormRowView formRowView= (FormRowView) v.getTag(R.id.form_row_id);
        if(vis){
            formRowView.setVisibility(View.VISIBLE);
        }else {
            formRowView.setVisibility(View.GONE);
        }
    }
}
