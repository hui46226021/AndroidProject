package com.jruilibrary.widget.cycleview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.jruilibrary.widget.cycleview.Indicator.PagerIndicator;
import com.sh.zsh.jr_ui_library.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/31.
 */
public class CycleView extends LinearLayout {

    public CycleView(Context context) {
        super(context);
        initBase(context, null);
    }

    public CycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBase(context, attrs);
    }

    public CycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBase(context, attrs);
    }

    private void initBase(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_cycleview, this);

        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CycleView, 0, 0);

            selectedColor = attributes.getColor(R.styleable.CycleView_myselected_color, Color.rgb(255, 255, 255));
            unSelectedColor = attributes.getColor(R.styleable.CycleView_myunselected_color, Color.argb(33, 255, 255, 255));

            selectedSrc = attributes.getResourceId(R.styleable.CycleView_myselected_drawable, 0);
            unSelectedSrc = attributes.getResourceId(R.styleable.CycleView_myunselected_drawable, 0);

            int shape = attributes.getInt(R.styleable.CycleView_myshape, PagerIndicator.Shape.Oval.ordinal());
            for (PagerIndicator.Shape s : PagerIndicator.Shape.values()) {
                if (s.ordinal() == shape) {
                    this.shape = s;
                    break;
                }
            }

            int scaleType = attributes.getInt(R.styleable.CycleView_scaleType, ViewPagerItemFragment.scaleType.centerCrop.ordinal());
            for (ViewPagerItemFragment.scaleType s : ViewPagerItemFragment.scaleType.values()) {
                if (s.ordinal() == scaleType) {
                    this.scaleType = s;
                    break;
                }
            }
        }
    }

    private int selectedColor;
    private int unSelectedColor;
    private int selectedSrc;
    private int unSelectedSrc;
    private PagerIndicator.Shape shape;
    private ViewPagerItemFragment.scaleType scaleType;

    public void setItems(String[] imgs, FragmentManager fragmentManager, OnItemClickListener listener) {
        ArrayList<Images> images = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            images.add(new Images(i, imgs[i]));
        }
        setItems(images, fragmentManager, listener);
    }

    public void setItems(int[] srcs, FragmentManager fragmentManager, OnItemClickListener listener) {
        ArrayList<Images> images = new ArrayList<>();
        for (int i = 0; i < srcs.length; i++) {
            images.add(new Images(i, srcs[i] + ""));
        }
        setItems(images, fragmentManager, listener);
    }

    private void setItems(List<Images> imgs, FragmentManager fragmentManager, OnItemClickListener listener) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerIndicator pagerIndicator = (PagerIndicator) findViewById(R.id.pagerIndicator);

        CycleViewStatePagerAdapter cycleViewStatePagerAdapter = new CycleViewStatePagerAdapter(this.getContext(), fragmentManager, imgs, listener, scaleType);
        viewPager.setAdapter(cycleViewStatePagerAdapter);

        pagerIndicator.setDefaultIndicatorColor(selectedColor, unSelectedColor);
        pagerIndicator.setIndicatorStyleResource(selectedSrc, unSelectedSrc);

        pagerIndicator.setDefaultIndicatorShape(shape);
        pagerIndicator.setViewPager(viewPager);
        pagerIndicator.redraw();
        pagerIndicator.setAutoScroll();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onLoadImage(ImageView imageView, String url);
    }
}
