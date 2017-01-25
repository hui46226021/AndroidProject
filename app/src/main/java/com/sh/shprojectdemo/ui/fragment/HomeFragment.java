package com.sh.shprojectdemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jereibaselibrary.image.JRSetImage;
import com.jrfunclibrary.base.fragment.LazyFragment;
import com.jruilibarary.widget.RefreshLayout;
import com.jruilibarary.widget.TemplateTitleBar;
import com.jruilibarary.widget.cycleview.widget.CycleView;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.presenter.HomePresenter;
import com.sh.shprojectdemo.ui.LetterListViewActivity;
import com.sh.shprojectdemo.ui.UserListActivity;
import com.sh.shprojectdemo.view.HomeView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class HomeFragment extends LazyFragment implements HomeView {


    @InjectView(R.id.template)
    TemplateTitleBar template;
    @InjectView(R.id.cycleView)
    CycleView cycleView;
    @InjectView(R.id.sideslipListView)
    Button sideslipListView;

    HomePresenter homePresenter;
    @InjectView(R.id.refreshlayout)
    RefreshLayout refreshlayout;
    private int DIQV_REQUEST = 1001;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();


        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, view);
        homePresenter = new HomePresenter(this);
        homePresenter.initViewData();
        initView();
        return view;
    }


    void initView() {
        template.setMoreTextContextAction("表单", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        template.setTxtBackContextAction("地区", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), LetterListViewActivity.class), DIQV_REQUEST);
            }
        });

        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Timer().schedule(new TimerTask() {//延时250毫秒刷新
                    @Override
                    public void run() {
                       getActivity(). runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshlayout.setRefreshing(false);

                            }
                        });
                    }
                }, 1500);
            }
        });

        refreshlayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorAccent));
    }

    @OnClick({R.id.sideslipListView})
    void pageOnClick(View v) {
        startActivity(new Intent(getActivity(), UserListActivity.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DIQV_REQUEST && resultCode == getActivity().RESULT_OK) {
            template.setTxtBackContextAction(data.getStringExtra("city"), null);
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void getImages(String[] imgs) {
        cycleView.setItems(imgs, getActivity().getSupportFragmentManager(), new CycleView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("liao", position + "");
            }

            @Override
            public void onLoadImage(ImageView imageView, String url) {
                JRSetImage.setNetWorkImage(getActivity(), url, imageView);
            }
        });
    }

}
