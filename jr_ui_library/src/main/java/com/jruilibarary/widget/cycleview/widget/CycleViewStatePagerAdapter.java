package com.jruilibarary.widget.cycleview.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import com.jruilibarary.widget.cycleview.Adapter.BaseCycleFragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by wpy on 15/9/2.
 */
public class CycleViewStatePagerAdapter<T> extends BaseCycleFragmentStatePagerAdapter<Images> {

    private final Context mContext;
    private CycleView.OnItemClickListener listener;
    private ViewPagerItemFragment.scaleType scaleType;

//    public CycleViewStatePagerAdapter(Context context, FragmentManager fm, CycleView.OnItemClickListener listener) {
//        super(fm);
//        mContext = context;
//        this.listener = listener;
//    }

    public CycleViewStatePagerAdapter(final Context context, FragmentManager fragmentManager, List<Images> images, CycleView.OnItemClickListener listener, ViewPagerItemFragment.scaleType scaleType) {
        super(fragmentManager, images);
        this.mContext = context;
        this.listener = listener;
        this.scaleType = scaleType;
    }

    @Override
    protected Fragment getItemFragment(Images images, int position) {
        return ViewPagerItemFragment.instantiateWithArgs(mContext, images, listener,scaleType);
    }
}
