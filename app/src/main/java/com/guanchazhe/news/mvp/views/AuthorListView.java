package com.guanchazhe.news.mvp.views;

import android.view.View;

import com.guanchazhe.news.mvp.model.entities.Author;

import java.util.List;

/**
 * Created by ranzh on 1/14/2016.
 */
public interface AuthorListView extends Views {

    void setAuthorList(List<Author> authors);

    void showAuthorListView();

    void showLoadingView();

    void showErrorView();

    void showDetailScreen(View view,Author author);

}
