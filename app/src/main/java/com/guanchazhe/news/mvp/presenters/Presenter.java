package com.guanchazhe.news.mvp.presenters;

import android.view.View;

import com.guanchazhe.news.mvp.views.Views;

/**
 * Created by ranzh on 12/23/2015.
 */
public interface Presenter {
    void onStart();

    void onStop();

    void onPause();

    void attachView (Views v);

    void onCreate();
}