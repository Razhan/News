package com.guanchazhe.news.mvp.presenters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.guanchazhe.news.domain.GetNewsDetailUsecase;
import com.guanchazhe.news.model.entities.NewsItem;
import com.guanchazhe.news.mvp.views.NewsDetailView;
import com.guanchazhe.news.mvp.views.Views;


import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by ranzh on 12/31/2015.
 */
public class NewsDetailPresenter implements Presenter {

    private final Context mActivityContext;
    private NewsDetailView mNewsDetailView;
    private final GetNewsDetailUsecase mNewsDetailUsecase;
    private Subscription mNewsDetailSubscription;
    private String mNewsId;
    private String mNewsTitle;
    private NewsItem mNewsItem;

    @Inject
    public NewsDetailPresenter(GetNewsDetailUsecase newsDetailUsecase, Context activityContext) {
        mNewsDetailUsecase = newsDetailUsecase;
        mActivityContext = activityContext;
    }

    @Override
    public void onCreate() {
        if (mNewsId == null || mNewsTitle == null)
            throw new IllegalStateException("initializePresenter was not well initialised");

        mNewsDetailSubscription = mNewsDetailUsecase.execute(mNewsId)
                .map(this::convertHtmlToObj)
                .subscribe(
                        this::onNewsReceived,
                        this::manageCharacterError
                );
    }

    @Override
    public void onStart() {}

    @Override
    public void onStop(){
        if (!mNewsDetailSubscription.isUnsubscribed()) {
            mNewsDetailSubscription.unsubscribe();
        }

        mNewsDetailView.stopWebView();

    }

    @Override
    public void onPause() {}

    @Override
    public void attachView (Views v) {
        mNewsDetailView = (NewsDetailView) v;
    }

    public void initializePresenter(String Id, String title, NewsItem newsItem) {
        mNewsId = Id;
        mNewsTitle = title;
        mNewsItem = newsItem;
    }

    private NewsItem convertHtmlToObj(String content) {

        mNewsItem.setContent(content);

        return mNewsItem;
    }

    private void manageCharacterError(Throwable error) {
        // TODO
    }

    private void onNewsReceived(NewsItem news) {
        mNewsDetailView.bindNews(news);
        mNewsDetailView.setContent(news);
    }

    public void setCharacterId(String Id) {
        mNewsId = Id;
    }

    public void onImageReceived(Bitmap resource) {
        mNewsDetailView.hideRevealViewByAlpha();
        mNewsDetailView.initActivityColors(resource);
    }

}
