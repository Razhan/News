package com.guanchazhe.news.mvp.views;

import android.app.ActivityOptions;

import com.guanchazhe.news.model.entities.NewsItem;

import java.util.List;

/**
 * Created by ranzh on 12/23/2015.
 */
public interface NewsListView extends Views {
    void bindNewsList(List<NewsItem> news);

    void showNewsList();

    void hideNewsList();

    void showLoadingMoreNewsIndicator();

    void hideLoadingMoreNewsIndicator();

    void hideLoadingIndicator ();

    void showLoadingView();

    void hideLoadingView();

    void showLightError();

    void hideErrorView();

    void showEmptyIndicator();

    void hideEmptyIndicator();

    void updateNewsList(int charactersLimit);

    ActivityOptions getActivityOptions(int position, android.view.View clickedView);

    void showConnectionErrorMessage();

    void showServerErrorMessage();

    void showUnknownErrorMessage();

    void showDetailScreen(NewsItem newsItem);
}
