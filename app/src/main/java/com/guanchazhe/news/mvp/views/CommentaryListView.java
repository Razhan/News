package com.guanchazhe.news.mvp.views;

import com.guanchazhe.news.model.entities.Commentary;

import java.util.List;

/**
 * Created by ranzh on 1/6/2016.
 */
public interface CommentaryListView extends Views{
    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showItems(List<Commentary> items);
}
