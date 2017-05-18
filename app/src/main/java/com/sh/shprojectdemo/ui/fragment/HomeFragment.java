package com.sh.shprojectdemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.jereibaselibrary.image.JRBitmapUtils;
import com.jereibaselibrary.image.JRSetImage;
import com.jrfunclibrary.activity.ImageViewPageActivity;
import com.jrfunclibrary.base.activity.BaseActivity;
import com.jrfunclibrary.base.fragment.LazyFragment;
import com.jruilibrary.utils.JRActivityUtils;
import com.jruilibrary.widget.RefreshLayout;
import com.jruilibrary.widget.TemplateTitleBar;
import com.jruilibrary.widget.cycleview.widget.CycleView;
import com.jruilibrary.widget.spinner.SpinnerDialog;
import com.jruilibrary.widget.spinner.SpinnerModel;
import com.sh.shprojectdemo.R;
import com.sh.shprojectdemo.adapter.SystemGridViewAdapter;
import com.sh.shprojectdemo.presenter.HomePresenter;
import com.sh.shprojectdemo.ui.AudioRecordActivity;
import com.sh.shprojectdemo.ui.LayerListViewActivity;
import com.sh.shprojectdemo.ui.LetterListViewActivity;
import com.sh.shprojectdemo.ui.MapHomeActivity;
import com.sh.shprojectdemo.ui.MaterialDesignActivity;
import com.sh.shprojectdemo.ui.SettingActivity;

import com.sh.shprojectdemo.ui.ShVideoActivity;
import com.sh.shprojectdemo.ui.TabLayout2Activity;
import com.sh.shprojectdemo.ui.TabLayoutActivity;
import com.sh.shprojectdemo.ui.UserListActivity;

import com.sh.shprojectdemo.ui.VideoRecordActivity;
import com.sh.shprojectdemo.view.HomeView;
import com.sh.zsh.code.umeng_sdk.ShareActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

//import com.sh.shprojectdemo.ui.ShVideoActivity;


public class HomeFragment extends LazyFragment implements HomeView, SpinnerDialog.SelectedCall {


    @InjectView(R.id.template)
    TemplateTitleBar template;
    @InjectView(R.id.cycleView)
    CycleView cycleView;
    @InjectView(R.id.cycleView2)
    CycleView cycleView2;

    HomePresenter homePresenter;
    @InjectView(R.id.refreshlayout)
    RefreshLayout refreshlayout;

    SpinnerDialog spinnerDialog;//列表点击操作 弹框
    @InjectView(R.id.gridview_home)
    GridView gridviewHome;
    private int DIQV_REQUEST = 1001;
    static HomeFragment homeFragment;
    View rootView;
    public static HomeFragment newInstance(View rootView) {
        HomeFragment fragment = new HomeFragment();
        fragment.setRootView(rootView);
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
        initGridView();
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
                        getActivity().runOnUiThread(new Runnable() {
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
        list.add(new SpinnerModel("第一个菜单", 111));
        list.add(new SpinnerModel("第二个菜单", 222));
        list.add(new SpinnerModel("第三个菜单", 333));
        spinnerDialog = new SpinnerDialog(this);
        spinnerDialog.createLoadingDialog(getActivity(), list);
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
        cycleView.setItems(imgs, getChildFragmentManager(), new CycleView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("liao", position + "");
            }

            @Override
            public void onLoadImage(ImageView imageView, String url) {
                JRSetImage.setNetWorkImage(getActivity(), url, imageView,R.drawable.nopicture);
            }
        });

        String[] urls2 = new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492511323&di=e62bedb329f60b0af1f0ab7081a1ab7c&imgtype=jpg&er=1&src=http%3A%2F%2Fimg03.tooopen.com%2Fimages%2F20131102%2Fsy_45237377439.jpg"
                ,"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1924463045,1738546004&fm=21&gp=0.jpg"  };

        cycleView2.setItems(urls2, getChildFragmentManager(), new CycleView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("liao", position + "");
            }

            @Override
            public void onLoadImage(ImageView imageView, String url) {
                JRSetImage.setNetWorkImage(getActivity(), url, imageView,R.drawable.nopicture);
            }
        });
    }

    @Override
    public void selectedCall(SpinnerModel spinnerModel) {
        showMessage("点击了" + spinnerModel.getKey() + " 菜单ID" + spinnerModel.getValue());
    }


    void initGridView() {
        List<String> menuModels = new ArrayList<>();
        menuModels.add("SideslipListView");
        menuModels.add("TabLayout");
        menuModels.add("TabLayout2");
        menuModels.add("SpinnerDialog");
        menuModels.add("图片查看器");
        menuModels.add("音频录制播放");
        menuModels.add("视频录制压缩");
        menuModels.add("视频播放");
        menuModels.add("layerListView");
        menuModels.add("测试掉线");
        menuModels.add("第三方分享");
        menuModels.add("M D风格控件");
        menuModels.add("Baidu 地图");
        SystemGridViewAdapter systemGridViewAdapter = new SystemGridViewAdapter(getActivity(), menuModels);
        gridviewHome.setAdapter(systemGridViewAdapter);
        gridviewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        startActivity(new Intent(getActivity(), UserListActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getActivity(), TabLayoutActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), TabLayout2Activity.class));
                        break;
                    case 3:
                        spinnerDialog.show();
                        break;
                    case 4:
                        Intent intent = new Intent(getActivity(), ImageViewPageActivity.class);
                        String[] urls = new String[]{"http://www.mobileui.cn/blog/uploads/2015/02/2202032219.jpeg"
                                ,"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487578011676&di=1b49981c1b51d954d2a2f67e2b3e504f&imgtype=0&src=http%3A%2F%2Fattachments.gfan.com%2Fforum%2Fattachments2%2F201407%2F31%2F000513mtxxldodtxtlbjoh.png",
                                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487577385127&di=454683fabd40813585d640971cb8b261&imgtype=0&src=http%3A%2F%2Fp1.image.hiapk.com%2Fuploads%2Fallimg%2F141124%2F7730-141124100211.jpg",
                                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1488172303&di=8057d775e88fc75e2830d9433bd3ceca&imgtype=jpg&er=1&src=http%3A%2F%2Fi1.download.fd.pchome.net%2Ft_960x600%2Fg1%2FM00%2F0C%2F0F%2FoYYBAFRh0Z-IZt-rAAFTtOctLdEAACEuABjxbsAAVPM869.png"
                        };

                        //通过url 展示图片
                        intent.putExtra(ImageViewPageActivity.IMAGE_LIST, urls);//图片集合
                        intent.putExtra(ImageViewPageActivity.IMAGE_INDEX, 1);//默认显示 第几张图片
                        //通过 bitmap展示图片
//                ImageViewPageActivity.bitmap = JRBitmapUtils.id2Bitmap(getResources(),R.drawable.guide01);
                        startActivity(intent);
                        break;
                    case 5:
                        startActivity(new Intent(getActivity(), AudioRecordActivity.class));
                        break;
                    case 6:
                        startActivity(new Intent(getActivity(), VideoRecordActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getActivity(), ShVideoActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(getActivity(), LayerListViewActivity.class));
                        break;
                    case 9:
                        homePresenter.dropTest();
                        break;
                    case 10:
                        /**
                         * 使用该种模糊效果 请确定 在app的build.gradle 的 defaultConfig 下设置了    renderscriptTargetApi 18    renderscriptSupportModeEnabled true
                         */
                        ShareActivity.share(rootView,getActivity(),null,"测试分享","https://www.baidu.co,","测试分享");
                        break;
                    case 11:

                        JRActivityUtils.getInstance().activityAnim(getActivity(), JRBitmapUtils.myShot(getActivity(),true), BaseActivity.touchX,BaseActivity.touchY,new Intent(new Intent(getActivity(), MaterialDesignActivity.class)));
                        break;
                    case 12:

                        startActivity(new Intent(getActivity(), MapHomeActivity.class));
                        break;

                }
            }
        });
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }


}
