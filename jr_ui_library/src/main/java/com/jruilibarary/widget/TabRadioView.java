package com.jruilibarary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.sh.zsh.jr_ui_library.R;
import com.xinlan.dragindicator.DragIndicatorView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhush on 2017/2/4.
 * E-mail zhush@jerei.com
 * PS  底部单选导航
 */
public class TabRadioView extends FrameLayout implements RadioGroup.OnCheckedChangeListener{

    RadioGroup radioGroup;
    RadioButton r1;
    RadioButton r2;
    RadioButton r3;
    RadioButton r4;
    RadioButton r5;

    DragIndicatorView drag1;
    DragIndicatorView drag2;
    DragIndicatorView drag3;
    DragIndicatorView drag4;
    DragIndicatorView drag5;
    FrameLayout dragIndicator1;
    FrameLayout dragIndicator2;
    FrameLayout dragIndicator3;
    FrameLayout dragIndicator4;
    FrameLayout dragIndicator5;

    List<RadioButton> radioButtonList = new ArrayList<>();
    List<FrameLayout> dragIndicators = new ArrayList<>();
    List<DragIndicatorView> drags = new ArrayList<>();

    TabSelectedListener tabSelectedListener;//选中监听

    Context mContext;
    public TabRadioView(Context context) {
        super(context);
        mContext = context;
    }

    public TabRadioView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.widget_tab_radio, this);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        r1 = (RadioButton) findViewById(R.id.r1);
        r2 = (RadioButton) findViewById(R.id.r2);
        r3 = (RadioButton) findViewById(R.id.r3);
        r4 = (RadioButton) findViewById(R.id.r4);
        r5 = (RadioButton) findViewById(R.id.r5);
        drag1= (DragIndicatorView) findViewById(R.id.count1);
        drag2= (DragIndicatorView) findViewById(R.id.count2);
        drag3= (DragIndicatorView) findViewById(R.id.count3);
        drag4= (DragIndicatorView) findViewById(R.id.count4);
        drag5= (DragIndicatorView) findViewById(R.id.count5);
        dragIndicator1= (FrameLayout) findViewById(R.id.drag_layout1);
        dragIndicator2= (FrameLayout) findViewById(R.id.drag_layout2);
        dragIndicator3= (FrameLayout) findViewById(R.id.drag_layout3);
        dragIndicator4= (FrameLayout) findViewById(R.id.drag_layout4);
        dragIndicator5= (FrameLayout) findViewById(R.id.drag_layout5);

        radioButtonList.add(r1);
        radioButtonList.add(r2);
        radioButtonList.add(r3);
        radioButtonList.add(r4);
        radioButtonList.add(r5);

        dragIndicators.add(dragIndicator1);
        dragIndicators.add(dragIndicator2);
        dragIndicators.add(dragIndicator3);
        dragIndicators.add(dragIndicator4);
        dragIndicators.add(dragIndicator5);

        drags.add(drag1);
        drags.add(drag2);
        drags.add(drag3);
        drags.add(drag4);
        drags.add(drag5);


        radioGroup.setOnCheckedChangeListener(this);
    }

    /**
     * 设置红点显示的数量
     * @param counts
     */
    public void setCount(int[] counts){
        for(int i=0;i<counts.length;i++){
            dragIndicators.get(i).setVisibility(VISIBLE);
            if(counts[i]==0){
                drags.get(i).setVisibility(INVISIBLE);
            }else {
                drags.get(i).setVisibility(VISIBLE);
                if(counts[i]>9){
                    drags.get(i).setText("9+");
                }else {
                    drags.get(i).setText(counts[i]+"");
                }
            }
        }
    }
    /**
     * 设置 文字 图片颜色
     * @param tabText  文本
     * @param textColour  文字颜色  一般是个选择器
     * @param images   文字上方的图片
     */

    String[] mTabText;
    public void setTabTexts(String[] tabText,int textColour,int[] images){
        if(tabText.length>5){
            throw new RuntimeException("最多设置5个");
        }
        mTabText = tabText;
            for(int i= 0;i<tabText.length;i++){
                RadioButton radioButton  = radioButtonList.get(i);
                radioButton.setText(tabText[i]);
                ColorStateList color = getResources().getColorStateList(textColour);
                radioButton.setTextColor(color);

                Drawable drawable = getResources().getDrawable(images[i]);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
                radioButton.setCompoundDrawables(null, drawable, null, null);//画在上边
                radioButton.setVisibility(VISIBLE);
            }
    }

    /**
     * 设置按钮选中
     * @param i
     */
    public void setChecked(int i){
        switch (i){
            case 0:
                r1.setChecked(true);
                break;
            case 1:
                r2.setChecked(true);
                break;
            case 2:
                r3.setChecked(true);
                break;
            case 3:
                r4.setChecked(true);
                break;
            case 4:
                r5.setChecked(true);
                break;

        }
    }
    List<Fragment> fragments;
    int fragmentsLayout;

    /**
     * 设置 关联的Fragment
     * @param fragments Fragment 集合
     * @param fragmentsLayout  Fragment 需要替换的布局
     */
    public void setFragmentList(List<Fragment> fragments,int fragmentsLayout){
        this.fragments = fragments;
        this.fragmentsLayout = fragmentsLayout;

        addFragment(0);
    }

    /**
     * radioGroup 选中事件
     * @param radioGroup
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

            if (checkedId == R.id.r1) {
                addFragment(0);
                onSelect(0);

            }
            if (checkedId == R.id.r2) {
                addFragment(1);
                onSelect(1);
            }
            if (checkedId == R.id.r3) {
                addFragment(2);
                onSelect(2);
            }
            if (checkedId == R.id.r4) {
                addFragment(3);
                onSelect(3);
            }
            if (checkedId == R.id.r5) {
                addFragment(4);
                onSelect(4);
            }
        }

    private void onSelect(int i){
        if(tabSelectedListener!=null){
            tabSelectedListener.tabSelect(i);
        }
    }
    /**
     * 添加一个fragment
     * @param count
     */
    private void addFragment(int count){

        if(fragments!=null){
            FragmentActivity fragmentActivity;
            try {
                fragmentActivity =(FragmentActivity)mContext;
            }catch (Exception e){
                throw  new RuntimeException("该Activity 必须是 FragmentActivity 的子类");
            }
            fragmentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(fragmentsLayout, fragments.get(count)).commit();
        }
    }

    public void setTabSelectedListener(TabSelectedListener tabSelectedListener) {
        this.tabSelectedListener = tabSelectedListener;
    }

    public static interface TabSelectedListener{
        public void tabSelect(int i);
    }
}
