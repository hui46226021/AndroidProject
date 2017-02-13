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

import com.jereibaselibrary.cache.AppFileCache;
import com.jereibaselibrary.image.JRSetImage;
import com.jrfunclibrary.activity.ImageViewPageActivity;
import com.jrfunclibrary.base.fragment.LazyFragment;
import com.jruilibarary.widget.RefreshLayout;
import com.jruilibarary.widget.TemplateTitleBar;
import com.jruilibarary.widget.cycleview.widget.CycleView;
import com.jruilibarary.widget.spinner.SpinnerDialog;
import com.jruilibarary.widget.spinner.SpinnerModel;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.presenter.HomePresenter;
import com.sh.shprojectdemo.ui.AudioRecordActivity;
import com.sh.shprojectdemo.ui.LayerListViewActivity;
import com.sh.shprojectdemo.ui.LetterListViewActivity;
import com.sh.shprojectdemo.ui.SettingActivity;
import com.sh.shprojectdemo.ui.ShVideoActivity;
import com.sh.shprojectdemo.ui.TabLayout2Activity;
import com.sh.shprojectdemo.ui.TabLayoutActivity;
import com.sh.shprojectdemo.ui.UserListActivity;
//import com.sh.shprojectdemo.ui.ShVideoActivity;
import com.sh.shprojectdemo.ui.VideoRecordDemoActivity;
import com.sh.shprojectdemo.view.HomeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class HomeFragment extends LazyFragment implements HomeView , SpinnerDialog.SelectedCall {


    @InjectView(R.id.template)
    TemplateTitleBar template;
    @InjectView(R.id.cycleView)
    CycleView cycleView;
    @InjectView(R.id.sideslipListView)
    Button sideslipListView;

    HomePresenter homePresenter;
    @InjectView(R.id.refreshlayout)
    RefreshLayout refreshlayout;

    SpinnerDialog spinnerDialog;//列表点击操作 弹框
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
        template.setMoreTextContextAction("设置", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
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


        List<SpinnerModel> list = new ArrayList<>();
        list.add(new SpinnerModel("第一个菜单",111));
        list.add(new SpinnerModel("第二个菜单",222));
        list.add(new SpinnerModel("第三个菜单",333));
        spinnerDialog = new SpinnerDialog(this);
        spinnerDialog.createLoadingDialog(getActivity(), list);
    }

    @OnClick({R.id.sideslipListView,R.id.tabLayout,R.id.tabLayout2,R.id.spinnerDialog,R.id.image_look,
            R.id.video_record,R.id.video_paly,R.id.layerListView,R.id.drop_test,R.id.audio_record})
    void pageOnClick(View v) {
        switch (v.getId()){
            case R.id.sideslipListView:
                startActivity(new Intent(getActivity(), UserListActivity.class));
                break;
            case R.id.tabLayout:
                startActivity(new Intent(getActivity(), TabLayoutActivity.class));
                break;
            case R.id.tabLayout2:
                startActivity(new Intent(getActivity(), TabLayout2Activity.class));
                break;
            case R.id.spinnerDialog:
                spinnerDialog.show();
                break;
            case R.id.image_look:
                Intent intent = new Intent(getActivity(), ImageViewPageActivity.class);
                String[] urls = new String[]{"http://imgsrc.baidu.com/forum/w%3D580/sign=4c51a1afa5efce1bea2bc8c29f50f3e8/4353b319ebc4b7459f17554fcdfc1e178b8215ea.jpg",
                        "http://tc.sinaimg.cn/maxwidth.2048/tc.service.weibo.com/p/img4_cache_netease_com/2479a6e4a2845db0727f44808138f946.jpg",
                        "http://www.12fly.com.my/images/lifestyle/EventsC/2015/angry-cat/02.jpg"
                };

                //通过url 展示图片
                intent.putExtra(ImageViewPageActivity.IMAGE_LIST,urls);//图片集合
                intent.putExtra(ImageViewPageActivity.IMAGE_INDEX,1);//默认显示 第几张图片
                //通过 bitmap展示图片
//                ImageViewPageActivity.bitmap = JRBitmapUtils.id2Bitmap(getResources(),R.drawable.guide01);
                startActivity(intent);
                break;
            case R.id.audio_record:
                startActivity( new Intent(getActivity(), AudioRecordActivity.class));
                break;
            case R.id.video_record:
                startActivity( new Intent(getActivity(), VideoRecordDemoActivity.class));
                break;
            case R.id.video_paly:
                startActivity( new Intent(getActivity(), ShVideoActivity.class));
                break;
            case R.id.layerListView:
                startActivity( new Intent(getActivity(), LayerListViewActivity.class));
                break;
            case R.id.drop_test:
                homePresenter.dropTest();
                break;
        }

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

    @Override
    public void selectedCall(SpinnerModel spinnerModel) {
        showMessage("点击了"+spinnerModel.getKey()+" 菜单ID"+spinnerModel.getValue());
    }


}
