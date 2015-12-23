package com.guanchazhe.news.mvp.presenters;

import android.view.View;

/**
 * Created by ranzh on 12/23/2015.
 */
public interface Presenter {
    void onStart();

    void onStop();

    void onPause();

    void attachView (View v);

    void onCreate();
}