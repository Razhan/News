package com.guanchazhe.news.mvp.views;

import android.graphics.Bitmap;

import com.guanchazhe.news.model.entities.NewsItem;

/**
 * Created by ranzh on 1/4/2016.
 */
public interface NewsDetailView extends Views {

    void hideRevealViewByAlpha();

    void showError(String s);

    void bindNews(NewsItem news);

    void initActivityColors(Bitmap resource);

    void setContent(NewsItem news);

    void stopWebView();

}
