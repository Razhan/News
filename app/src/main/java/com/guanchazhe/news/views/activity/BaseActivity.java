package com.guanchazhe.news.views.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ranzh on 1/28/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected SharedPreferences mSharedPreferences;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initToolbar();
    }


    protected abstract void  initUi();
    protected abstract void  initToolbar();
}
