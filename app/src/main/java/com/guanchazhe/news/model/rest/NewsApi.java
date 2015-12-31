package com.guanchazhe.news.model.rest;

import com.fernandocejas.frodo.annotation.RxLogObservable;
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

    @GET("/app/GetNewsList/")
    Observable<List<NewsItem>> getNews(
            @Query("typeid") int typeid,
            @Query("attributeid") int attributeid,
            @Query("pageindex") int pageindex,
            @Query("pagesize") int pagesize
    );

    @GET("/Appdetail/get/")
    Observable<String> getNewsDetail(
            @Query("devices") String devices,
            @Query("id") int id
    );
}

