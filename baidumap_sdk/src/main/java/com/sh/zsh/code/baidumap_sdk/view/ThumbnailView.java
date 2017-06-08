package com.sh.zsh.code.baidumap_sdk.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sh.zsh.code.baidumap_sdk.R;

/**
 * Created by zhush on 2017/2/23.
 * E-mail 405086805@qq.com
 * PS  标注图片控件
 *
 * setImageRes 设置图片
 * setImageRes 设置图片
 */
public class ThumbnailView extends FrameLayout{

    ImageView tmageView;

    public ThumbnailView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.thumbanati_view, this);
        tmageView = (ImageView) findViewById(R.id.image);
    }

    public ThumbnailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.thumbanati_view, this);
        tmageView = (ImageView) findViewById(R.id.image);
    }

    /**
     * 设置图片
     * @param res
     */
    public void setImageRes(int res){
        tmageView.setImageResource(res);
    }
    public void setImageBitmap(Bitmap bitmap){
        tmageView.setImageBitmap(bitmap);
    }

    /**
     * 获取imageView
     * @return
     */
    public ImageView getTmageView() {
        return tmageView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//        setMeasuredDimension(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }




}
