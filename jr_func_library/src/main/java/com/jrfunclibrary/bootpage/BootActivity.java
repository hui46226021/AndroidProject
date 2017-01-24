package com.jrfunclibrary.bootpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.jereibaselibrary.tools.JRDensityUtil;
import com.jrfunclibrary.base.activity.BaseActivity;
import com.sh.zsh.jrfunclibrary.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BootActivity extends BaseActivity {
    public static String IDS_KEY  = "ids_key";
    public static String IDS_ACTIVITY_KEY  ="ids_activity_key";
    public HashMap<String,Object> aCache = new HashMap<>();
    // 到达最后一张
    private static final int TO_THE_END = 0;
    // 离开最后一张
    private static final int LEAVE_FROM_END = 1;
    private LinearLayout dotContain; // 存储点的容器
    private int offset;              // 位移量
    private int curPos = 0;          // 记录当前的位置
    private ViewPager mViewPage;
    private List<View> dots = new ArrayList<View>();
    private List<View> guides = new ArrayList<View>();
    private  GuidePagerAdapter adapter;
    private View curDot;
    private Button startBtn;

    int[] ids=null;
    Class activityClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
        ids = getIntent().getIntArrayExtra(IDS_KEY);
        activityClass= (Class) getIntent().getSerializableExtra(IDS_ACTIVITY_KEY);
        initView();
    }

    private void initView() {
        mViewPage = (ViewPager) findViewById(R.id.viewPage);
        dotContain = (LinearLayout) findViewById(R.id.dotLayout);
        curDot = findViewById(R.id.cur_dot);
        startBtn= (Button) findViewById(R.id.startBtn);
        ImageView imageView = null;
        View dot = null;
        for (int i = 0; i < ids.length; i++) {
            imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(ids[i]);
            imageView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            guides.add(imageView);

            dot=new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    JRDensityUtil.dip2px(this, 10), JRDensityUtil.dip2px(this, 10),1.0f);
            params.setMargins(0, 0,JRDensityUtil.dip2px(this, 5), 0);
            dot.setLayoutParams(params);
            dot.setBackgroundResource(R.drawable.dot_normal);
            dotContain.addView(dot);
            dots.add(dot);
        }

        // 当curDot的所在的树形层次将要被绘出时此方法被调用
        curDot.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        // 获取ImageView的宽度也就是点图片的宽度
                        offset = curDot.getWidth()+JRDensityUtil.dip2px(BootActivity.this, 5);
                        return true;
                    }
                });
        adapter = new GuidePagerAdapter();
        mViewPage.setAdapter(adapter);
        //切换动画
        mViewPage.setPageTransformer(true, new TabletTransformer());
        mViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int pos = position % ids.length;

                moveCursorTo(pos);

                if (pos == ids.length - 1) {// 到最后一张了
                    handler.sendEmptyMessageDelayed(TO_THE_END, 250);

                } else if (curPos == ids.length - 1) {
                    handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);
                }
                curPos = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aCache.put("guide",1+"");
                finish();
                startActivity(new Intent(BootActivity.this, activityClass));
            }
        });

    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TO_THE_END)
                startBtn.setVisibility(View.VISIBLE);
            else if (msg.what == LEAVE_FROM_END)
                startBtn.setVisibility(View.GONE);
        }
    };


    /**
     * 移动指针到相邻的位置 动画
     *
     * @param position 指针的索引值
     */
    private void moveCursorTo(int position) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation tAnim =
                new TranslateAnimation(offset * curPos, offset * position, 0, 0);
        animationSet.addAnimation(tAnim);
        animationSet.setDuration(300);
        animationSet.setFillAfter(true);
        curDot.startAnimation(animationSet);
    }

    class GuidePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return guides.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(guides.get(arg1 % guides.size()));
        }

        //初始化arg1位置的界面
        @Override
        public Object instantiateItem(View arg0, int arg1) {

            ((ViewPager) arg0).addView(guides.get(arg1), 0);

            return guides.get(arg1);
        }
    }
}
