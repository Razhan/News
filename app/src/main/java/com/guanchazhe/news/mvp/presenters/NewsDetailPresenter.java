package com.guanchazhe.news.mvp.presenters;

import android.graphics.Bitmap;

import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.views.NewsDetailView;
import com.guanchazhe.news.mvp.views.Views;

import javax.inject.Inject;

/**
 * Created by ranzh on 12/31/2015.
 */
public class NewsDetailPresenter implements Presenter {


    private NewsDetailView mNewsDetailView;

    @Inject
    public NewsDetailPresenter() {
    }

    @Override
    public void onCreate() {

        mNewsDetailView.setImageSource();
        mNewsDetailView.startWebView();
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop() {}

    @Override
    public void onPause() {}

    @Override
    public void attachView (Views v) {
        mNewsDetailView = (NewsDetailView) v;
    }

    private void resultError(Throwable error) {
        // TODO
    }

    public void resultArrived(News news) {
        mNewsDetailView.setContent(news);
    }

    public void onImageReceived(Bitmap resource) {
        mNewsDetailView.initActivityColors(resource);
    }

}
