package com.jruilibarary.widget.cycleview.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sh.zsh.jr_ui_library.R;


/**
 * Created by wpy on 15/9/2.
 */
public class ViewPagerItemFragment extends Fragment {

    private static final String KEY_IMAGES = "images";
    private static final String KEY_SCALETYPE = "scaleType";
    private scaleType scaleType;
    private Images mImages;
    private CycleView.OnItemClickListener listener;

    public enum scaleType {
        centerCrop, fitXY
    }

    public static ViewPagerItemFragment instantiateWithArgs(final Context context, final Images images,CycleView.OnItemClickListener listener,ViewPagerItemFragment.scaleType scaleType) {
        final ViewPagerItemFragment fragment = (ViewPagerItemFragment) instantiate(context, ViewPagerItemFragment.class.getName());
        fragment.setListener(listener);
        final Bundle args = new Bundle();
        args.putSerializable(KEY_SCALETYPE,scaleType);
        args.putSerializable(KEY_IMAGES, images);
        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(CycleView.OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initArguments();
    }

    private void initArguments() {
        final Bundle arguments = getArguments();
        if (arguments != null) {
            mImages = (Images) arguments.getSerializable(KEY_IMAGES);
            scaleType = (scaleType)arguments.getSerializable(KEY_SCALETYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.viewpager_item_fragment, container, false);
        initViews(view);
        return view;
    }

    private void initViews(final View view) {
        final ImageView backgroundImage = (ImageView) view.findViewById(R.id.item_fragment_image);
        switch (scaleType){
            case fitXY:
                backgroundImage.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case centerCrop:
                backgroundImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
        }

        int type = MatchType.getType(mImages.getImg());
        switch (type){
            case MatchType.TYPE_SRC:
                backgroundImage.setImageResource(Integer.parseInt(mImages.getImg()));
                break;
            case MatchType.TYPE_WEB:
            case MatchType.TYPE_FILE:
                listener.onLoadImage(backgroundImage, mImages.getImg());
                break;
        }
        backgroundImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onItemClick(mImages.getId());
                }
            }
        });
    }
}
