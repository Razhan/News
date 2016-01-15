package com.guanchazhe.news.mvp.views;

import com.guanchazhe.news.mvp.model.entities.Commentary;

import java.util.List;

/**
 * Created by ranzh on 1/6/2016.
 */
public interface CommentaryListView extends Views{

    void setCommentaryList(List<Commentary> commentaries);

    void addCommentaryList(List<Commentary> moreCommentaries);

    void clearCommentaryList();

    void showNewsListView();

    void showLoadingView();

    void showErrorView();

    boolean isContentDisplayed();

    void showDetailScreen(Commentary commentary);
}
