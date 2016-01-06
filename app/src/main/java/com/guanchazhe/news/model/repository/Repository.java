package com.guanchazhe.news.model.repository;

import com.guanchazhe.news.model.entities.NewsItem;

import java.util.List;

import rx.Observable;

/**
 * Created by ranzh on 12/22/2015.
 */
public interface Repository {
    Observable<List<NewsItem>> getNews(int typeid, int attributeid, int pageindex, int pagesize);

    Observable<String> getNewsDetail(String id);
}
