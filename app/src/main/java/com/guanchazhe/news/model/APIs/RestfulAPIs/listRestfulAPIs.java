package com.guanchazhe.news.model.APIs.RestfulAPIs;

import com.guanchazhe.news.model.entities.Commentary;
import com.guanchazhe.news.model.entities.News;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by ranzh on 12/22/2015.
 */
public interface ListRestfulAPIs {
    String END_POINT = "http://mobileservice.guancha.cn/";

    @GET("/app/GetNewsList/")
    Observable<List<News>> getNews(
            @Query("typeid") int typeid,
            @Query("attributeid") int attributeid,
            @Query("pageindex") int pageindex,
            @Query("pagesize") int pagesize
    );

    @GET("/app/GetAuthorNews/")
    Observable<List<Commentary>> getCommentaries(
            @Query("authorid") String authorid,
            @Query("pageindex") int pageindex,
            @Query("pagesize") int pagesize
    );

}

