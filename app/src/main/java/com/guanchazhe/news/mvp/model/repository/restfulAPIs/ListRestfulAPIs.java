package com.guanchazhe.news.mvp.model.repository.restfulAPIs;

import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.mvp.model.entities.Commentary;
import com.guanchazhe.news.mvp.model.entities.News;

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
            @Query("attributeid") String attributeid,
            @Query("pageindex") String pageindex,
            @Query("pagesize") int pagesize
    );

    @GET("/app/GetAuthorNews/")
    Observable<List<Commentary>> getCommentaries(
            @Query("authorid") String authorid,
            @Query("pageindex") String pageindex,
            @Query("pagesize") int pagesize
    );

    @GET("/app/GetAuthorList/")
    Observable<List<Author>> getAuthors();

}

