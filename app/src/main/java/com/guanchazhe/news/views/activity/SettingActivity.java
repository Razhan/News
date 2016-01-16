package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.guanchazhe.news.R;
import com.guanchazhe.news.views.Fragment.SettingFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)                     Toolbar mToolBar;
    @Bind(R.id.setting_framelayout)         FrameLayout settingFramelayout;

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
        mToolBar.setTitle("设置");

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolBar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.setting_framelayout, new SettingFragment())
                .commit();
    }
}

//SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//String strUserName = SP.getString("username", "NA");
//boolean bAppUpdates = SP.getBoolean("applicationUpdates",false);
//String downloadType = SP.getString("downloadType","1");