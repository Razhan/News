package com.guanchazhe.news.mvp.presenters;

import android.graphics.Bitmap;

import com.guanchazhe.news.domain.GetNewsDetailUseCase;
import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.views.NewsDetailView;
import com.guanchazhe.news.mvp.views.Views;


import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by ranzh on 12/31/2015.
 */
public class NewsDetailPresenter implements Presenter {


    private NewsDetailView mNewsDetailView;
    private final GetNewsDetailUseCase mNewsDetailUsecase;

    @Inject
    public NewsDetailPresenter(GetNewsDetailUseCase newsDetailUsecase) {
        mNewsDetailUsecase = newsDetailUsecase;
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
