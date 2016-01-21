package com.guanchazhe.news.views.listener;

import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ranzh on 1/15/2016.
 */
public class OnScrollChangeListener implements NestedScrollView.OnScrollChangeListener {

    View mCoverView;
    View mTitle;

    public OnScrollChangeListener(View coverImageView, View title) {
        mCoverView = coverImageView;
        mTitle = title;
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

        if (scrollY > mCoverView.getHeight()) {
            mTitle.setTranslationY(scrollY - mCoverView.getHeight());
        } else {
            mTitle.setTranslationY(0);
        }
    }


}
