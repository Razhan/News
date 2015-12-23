package com.guanchazhe.news.model.rest;

import com.guanchazhe.news.model.entities.NewsItem;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by ranzh on 12/22/2015.
 */
public interface NewsApi {
    String END_POINT = "http://mobileservice.guancha.cn/";

    // http://mobileservice.guancha.cn/app/GetNewsList/?typeid=49646&attributeid=1&pageindex=1&pagesize=40
    @GET("/app/GetNewsList/")
    Observable<List<NewsItem>> getArticles(
            @Query("typeid") int typeid,
            @Query("attributeid") int attributeid,
            @Query("pageindex") int pageindex,
            @Query("pagesize") int pagesize
    );
}

