package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ranzh on 1/28/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initToolbar();
    }

    protected abstract void initUI();
    protected abstract void  initToolbar();
}
