package com.jruilibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jruilibrary.form.layout.model.ViewData;
import com.jruilibrary.form.layout.view.FormSpinner;
import com.sh.zsh.jr_ui_library.R;


import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/28.
 */

public class FormSpinnerView extends TextView{
    OptionsPickerView pvOptions;//选项选择器
    Object pvOptionsSelectValue;//选中的value
    Object pvOptionsSelectValueObject;//选中的value对象
    String mesage = "不满足筛选条件";

    String titel;
    ArrayList<ViewData> options1Items ;
    private String commCode;
    private FormSpinner.SelectedListener selectedListener; //选中监听
    public FormSpinnerView(Context context) {
        super(context);
        pvOptions = new OptionsPickerView(context);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pvOptions.show();
            }
        });
    }
    int selectIndex;
    public FormSpinnerView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = getContext().obtainStyledAttributes(attrs,  R.styleable.less_from_view, 0, 0);
        selectIndex = ta.getInt( R.styleable.less_from_view_less_form_spi_sel,999);
        pvOptions = new OptionsPickerView(context);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(options1Items!=null&&options1Items.size()>0){
                    pvOptions.show();
                }else{
                    Toast.makeText(context,mesage,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void setpvOptionsList(ArrayList<ViewData> options,String titel){

        this.titel =titel;
        setpvOptionsList(options);
    }
    /**
     * 设置选择器list
     */
    public void setpvOptionsList(ArrayList<ViewData> options){
        this.options1Items =options;

        //三级联动效果
        pvOptions.setPicker(options1Items);
        //设置选择的三级单位
//        pwOptions.setLabels("省", "市", "区");
        pvOptions.setTitle(titel);
        pvOptions.setCyclic(false, true, true);
        //设置默认选中的三级项目
        //监听确定选择按钮
//        pvOptions.setSelectOptions(1, 1, 1);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText();
                FormSpinnerView.super.setText(tx);
                pvOptionsSelectValue = options1Items.get(options1).getValue();
                pvOptionsSelectValueObject = options1Items.get(options1).getValueObject();
                if(selectedListener!=null){
                    selectedListener.pvOptions(tx,pvOptionsSelectValue,pvOptionsSelectValueObject);
                }


            }
        });

        if(selectIndex>0&&selectIndex<options.size()){
            //默认选中第一个
            pvOptions.setSelecedItem(selectIndex);
        }


    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }

    /**
     * 选中监听
     */
    public static interface SelectedListener{
        public void pvOptions(String key, Object value, Object pvOptionsSelectValueObject);
    }

    public void setSelectedListener(FormSpinner.SelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }

    /**
     * 设置选中
     * @param indext
     */
    public void setSelect(int indext){
        pvOptions.setSelecedItem(indext);
    }

    public String getSelectKey(){
        return getText()+"";
    }
    public Object getSelectValue(){
        if(pvOptionsSelectValue==null){
            return 0;
        }
        return pvOptionsSelectValue;
    }
    public Object getSelectValueObject(){
        return pvOptionsSelectValueObject;
    }

    public String getCommCode() {
        return commCode;
    }

    public void setCommCode(String commCode) {
        this.commCode = commCode;
    }
}
