package com.sh.shprojectdemo.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jereibaselibrary.tools.JRBarUtils;
import com.jrfunclibrary.base.activity.BaseActivity;
import com.jruilibrary.utils.JRActivityUtils;
import com.sh.shprojectdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MaterialDesignActivity extends BaseActivity {

    @InjectView(R.id.main_view)
    FrameLayout mainView;
    @InjectView(R.id.activity_material_design)
    RelativeLayout activityMaterialDesign;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        JRBarUtils.setTransparentStatusBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
        ButterKnife.inject(this);

        JRActivityUtils.getInstance().show(this, activityMaterialDesign, mainView);
        initToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JRActivityUtils.removeUtils();
    }

    @Override
    public void onBackPressed() {
        JRActivityUtils.getInstance().closeViewAnim(this, mainView);

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }
}
