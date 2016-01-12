package com.guanchazhe.news.mvp.views;

import android.app.ActivityOptions;

import com.guanchazhe.news.mvp.model.entities.News;

import java.util.List;

/**
 * Created by ranzh on 12/23/2015.
 */
public interface NewsListView extends Views {
    void bindNewsList(List<News> news);

    void showNewsList();

    void showLoadingView();

    void showErrorView();

    void updateNewsList(int charactersLimit);

    void showRefreshIndicator();

    void hideRefreshIndicator();

    void showDetailScreen(News news);
}
