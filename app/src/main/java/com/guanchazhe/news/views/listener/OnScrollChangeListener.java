package com.guanchazhe.news.views.listener;

import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ranzh on 1/15/2016.
 */
public class OnScrollChangeListener implements NestedScrollView.OnScrollChangeListener {

    ImageView mCoverImageView;
    TextView mTitle;

    public OnScrollChangeListener(ImageView coverImageView, TextView title) {
        mCoverImageView = coverImageView;
        mTitle = title;
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

        if (scrollY > mCoverImageView.getHeight()) {
            mTitle.setTranslationY(scrollY - mCoverImageView.getHeight());
        } else {
            mTitle.setTranslationY(0);
        }
    }
}
