package com.guanchazhe.news.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.guanchazhe.news.R;

import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class AboutActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    protected void initUI() {
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    public static void start(Activity activity) {
        Intent i = new Intent(activity, AboutActivity.class);
        activity.startActivity(i);
    }

}
