package com.guanchazhe.news.mvp.views;

import android.graphics.Bitmap;

import com.guanchazhe.news.mvp.model.entities.News;

/**
 * Created by ranzh on 1/15/2016.
 */
public interface NewsDetailNewView extends Views {

    void bindNews(News news);

    void initActivityColors(Bitmap resource);

    void setContent(News news);

    void stopWebView();

    void setImageSource();

}
