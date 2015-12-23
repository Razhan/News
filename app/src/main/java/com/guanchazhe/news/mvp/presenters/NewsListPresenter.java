package com.guanchazhe.news.mvp.presenters;

import android.view.View;

import com.guanchazhe.news.domain.GetNewsUsecase;
import com.guanchazhe.news.model.entities.NewsItem;
import com.guanchazhe.news.mvp.views.NewsListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by ranzh on 12/23/2015.
 */
public class NewsListPresenter implements Presenter {
    private final GetNewsUsecase mNewsUsecase;
    private boolean mIsTheNewsRequestRunning;
    private Subscription mNewsSubscription;

    private List<NewsItem> mNews;
    private NewsListView mNewsView;

    @Inject
    public NewsListPresenter(GetNewsUsecase newsUsecase) {
        mNewsUsecase = newsUsecase;
        mNews = new ArrayList<NewsItem>();
    }

    @Override
    public void onCreate() {
        askForNews();
    }


    @Override
    public void onStart() {
        // Unused
    }

    @Override
    public void onStop() {
        // Unused
    }

    @Override
    public void onPause() {
        mNewsView.hideLoadingMoreNewsIndicator();
        mNewsSubscription.unsubscribe();
        mIsTheNewsRequestRunning = false;
    }

    @Override
    public void attachView(View v) {
        mNewsView = (NewsListView) v;
    }

    public void onListEndReached() {
        if (!mIsTheNewsRequestRunning) {
            askForMoreNews();
        }
    }

    private void askForNews() {
        mIsTheNewsRequestRunning = true;
        mNewsView.hideErrorView();
        mNewsView.showLoadingView();

        mNewsSubscription = mNewsUsecase.execute()
                .subscribe(
                        characters -> {
                            mNews.addAll(characters);
                            mNewsView.bindNewsList(mNews);
                            mNewsView.showNewsList();
                            mNewsView.hideEmptyIndicator();
                            mIsTheNewsRequestRunning = false;

                },
                        error -> {
                            mIsTheNewsRequestRunning = false;
                            showErrorView(error);
                });

    }

    private void askForMoreNews() {
        mNewsView.showLoadingMoreNewsIndicator();
        mIsTheNewsRequestRunning = true;

        mNewsSubscription = mNewsUsecase.execute()
                .subscribe(
                        newCharacters -> {
                            mNews.addAll(newCharacters);
                            mNewsView.updateNewsList(
                                    GetNewsUsecase.NEWS_LIMIT);

                            mNewsView.hideLoadingIndicator();
                            mIsTheNewsRequestRunning = false;
                        },

                        error -> {
                            mIsTheNewsRequestRunning = false;
                            showGenericError();
                        }
                );
    }



}
