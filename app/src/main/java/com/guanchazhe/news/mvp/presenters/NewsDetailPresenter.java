package com.guanchazhe.news.mvp.presenters;

import android.graphics.Bitmap;

import com.guanchazhe.news.domain.GetNewsDetailUseCase;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.views.NewsDetailView;
import com.guanchazhe.news.mvp.views.Views;


import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by ranzh on 12/31/2015.
 */
public class NewsDetailPresenter implements Presenter {

    private final String DEVICE = "android";

    private NewsDetailView mNewsDetailView;
    private final GetNewsDetailUseCase mNewsDetailUsecase;
    private Subscription mNewsDetailSubscription;
    private News mNews;

    @Inject
    public NewsDetailPresenter(GetNewsDetailUseCase newsDetailUsecase) {
        mNewsDetailUsecase = newsDetailUsecase;
    }

    @Override
    public void onCreate() {
        if (mNews == null)
            throw new IllegalStateException("initializePresenter was not well initialised");

        mNewsDetailView.setImageSource();

        mNewsDetailSubscription = mNewsDetailUsecase.execute(DEVICE)
                .map(str -> addToObject(str))
                .subscribe(
                        news -> resultArrived(news),
                        error -> resultError(error)
                );
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop(){
        if (!mNewsDetailSubscription.isUnsubscribed()) {
            mNewsDetailSubscription.unsubscribe();
        }
    }

    @Override
    public void onPause() {}

    @Override
    public void attachView (Views v) {
        mNewsDetailView = (NewsDetailView) v;
    }

    public void initializePresenter(News news) {
        mNews = news;
    }

    private News addToObject(String content) {

        mNews.setContent(content);

        return mNews;
    }

    private void resultError(Throwable error) {
        // TODO
    }

    private void resultArrived(News news) {
        mNewsDetailView.bindNews(news);
        mNewsDetailView.setContent(news);
    }

    public void onImageReceived(Bitmap resource) {
        mNewsDetailView.initActivityColors(resource);
    }

}
