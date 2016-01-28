package com.guanchazhe.news.mvp.views;

import android.app.ActivityOptions;
import android.view.View;

import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.News;

import java.util.List;

/**
 * Created by ranzh on 12/23/2015.
 */
public interface NewsListView extends Views {
    void setNewsList(List<News> news);

    void addNewsList(List<News> moreNews);

    boolean isContentDisplayed();

    void clearNewsList();

    void showNewsListView();

    void showLoadingView();

    void showErrorView();

    void showRefreshIndicator();

    void hideRefreshIndicator();

    void showDetailScreen(News news);

    void hideStatusIndicator(View view);

    Constant.NewsType getNewsType();
}
