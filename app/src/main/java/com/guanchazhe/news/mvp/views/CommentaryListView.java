package com.guanchazhe.news.mvp.views;

import com.guanchazhe.news.mvp.model.entities.News;

import java.util.List;

/**
 * Created by ranzh on 1/6/2016.
 */
public interface CommentaryListView extends Views{

    void setCommentaryList(List<News> commentaries);

    void addCommentaryList(List<News> moreCommentaries);

    void clearCommentaryList();

    void showNewsListView();

    void showLoadingView();

    void showErrorView();

    boolean isContentDisplayed();

    void showDetailScreen(News commentary);
}
