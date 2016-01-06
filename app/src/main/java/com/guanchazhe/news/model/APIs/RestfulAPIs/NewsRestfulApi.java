package com.guanchazhe.news.model.APIs.RestfulAPIs;

import com.guanchazhe.news.model.entities.News;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by ranzh on 12/22/2015.
 */
public interface NewsRestfulApi {
    String END_POINT = "http://mobileservice.guancha.cn/";

    @GET("/app/GetNewsList/")
    Observable<List<News>> getNews(
            @Query("typeid") int typeid,
            @Query("attributeid") int attributeid,
            @Query("pageindex") int pageindex,
            @Query("pagesize") int pagesize
    );

//    http://mobileservice.guancha.cn/app/GetAuthorNews/?authorid=FuLangXiSiÂ·FuShan&pageindex=1&pagesize=20
    @GET("/app/GetAuthorNews/")
    Observable<List<News>> getCommentaries(
            @Query("authorid") String authorid,
            @Query("pageindex") int pageindex,
            @Query("pagesize") int pagesize
    );

}

