package com.jruilibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sh.zsh.jr_ui_library.R;


/**
 * 标题控件
 */
public class TemplateTitleBar extends RelativeLayout {

    private String titleText;
    private boolean canBack;
    private String backText;

    private int textColors;



    public TemplateTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_title, this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TemplateTitle, 0, 0);
        try {
            titleText = ta.getString(R.styleable.TemplateTitle_titleText);
            canBack = ta.getBoolean(R.styleable.TemplateTitle_canBack, false);
            backText = ta.getString(R.styleable.TemplateTitle_backText);

            textColors=ta.getResourceId(R.styleable.TemplateTitle_textColors, 0);
            setUpView();
        } finally {
            ta.recycle();
        }
    }

    private void setUpView(){
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(titleText);
        LinearLayout backBtn = (LinearLayout) findViewById(R.id.title_back);
        backBtn.setVisibility(canBack ? VISIBLE : INVISIBLE);
        TextView  tvBack = (TextView) findViewById(R.id.txt_back);
        if (canBack){

            tvBack.setText(backText);
            backBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) getContext()).finish();
                }
            });
        }


        if(textColors!=0){
            tvTitle.setTextColor(ContextCompat.getColor(getContext(),textColors));

            tvBack.setTextColor(ContextCompat.getColor(getContext(),textColors));
            TextView  tvMore2 = (TextView) findViewById(R.id.txt_more2);
            tvMore2.setTextColor(ContextCompat.getColor(getContext(),textColors));
        }
    }


    /**
     * 标题控件
     *
     * @param titleText 设置标题文案
     */
    public void setTitleText(String titleText){
        this.titleText = titleText;
        TextView tvTitle = (TextView) findViewById(R.id.title);
        tvTitle.setText(titleText);
    }






    /**
     * 左侧图标 按钮
     *
     * @param img
     */
    public void setLifeImg(int img,OnClickListener listener){

        ImageView moreImgView = (ImageView) findViewById(R.id.img_life);
        moreImgView.setImageDrawable(getContext().getResources().getDrawable(img));
        if(listener!=null){
            moreImgView.setOnClickListener(listener);
        }

    }







    /**
     * 设置更多按钮事件
     *
     * @param listener 事件监听
     */
    public void setMoreImgAction(int img,OnClickListener listener){
        ImageView moreImgView = (ImageView) findViewById(R.id.img_more2);
        moreImgView.setVisibility(VISIBLE);
        moreImgView.setImageDrawable(getContext().getResources().getDrawable(img));
        if(listener!=null){
            moreImgView.setOnClickListener(listener);
        }
    }





    /**
     * 设置更多文字内容
     * @param text 更多文本
     */
    public void setMoreTextContextAction(String text,OnClickListener listener){
        TextView  tvMore2 = (TextView) findViewById(R.id.txt_more2);
        tvMore2 .setText(text+"  ");

        if(listener!=null){
            tvMore2.setOnClickListener(listener);
        }
    }


    /**
     * 设置返回文字内容
     * @param text 更多文本
     */
    public void setTxtBackContextAction(String text,OnClickListener listener){
        TextView  txtBack = (TextView) findViewById(R.id.txt_back);
        txtBack .setText(text+"  ");
        if(listener!=null){
            txtBack.setOnClickListener(listener);
        }
        txtBack.setVisibility(VISIBLE);
        LinearLayout backBtn = (LinearLayout) findViewById(R.id.title_back);
        backBtn.setVisibility(VISIBLE);
        findViewById(R.id.img_back).setVisibility(GONE);

    }


    /**
     * 设置返回按钮事件
     *
     * @param listener 事件监听
     */
    public void setBackListener(OnClickListener listener){
        if (canBack){
            LinearLayout backBtn = (LinearLayout) findViewById(R.id.title_back);
            backBtn.setOnClickListener(listener);
        }
    }






}
