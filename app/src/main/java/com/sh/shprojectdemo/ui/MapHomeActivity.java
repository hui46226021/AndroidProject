package com.sh.shprojectdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.sh.shprojectdemo.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MapHomeActivity extends AppCompatActivity {


    @InjectView(R.id.map_button)
    Button mapButton;
    @InjectView(R.id.redar_button)
    Button redarButton;
    @InjectView(R.id.nva_button)
    Button nvaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.map_button)
    void mapButton() {
        startActivity(new Intent(this, MapActivity.class));
    }

    @OnClick(R.id.redar_button)
    void redarButton() {
        startActivity(new Intent(this, RadarActivity.class));
    }

    @OnClick(R.id.nva_button)
    void navButton() {
        startActivity(new Intent(this, NavigationActivity.class));
    }
}
