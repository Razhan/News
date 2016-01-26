package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.guanchazhe.news.R;
import com.guanchazhe.news.views.Fragment.SettingFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)         Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        initToolbar();
        initFragment();
    }

    private void initUI() {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    private void initToolbar() {
        toolBar.setTitle("设置");
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.setting_framelayout, new SettingFragment())
                .commit();
    }
}