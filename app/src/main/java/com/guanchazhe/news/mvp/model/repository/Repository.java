package com.guanchazhe.news.mvp.model.repository;

import com.guanchazhe.news.mvp.model.entities.Commentary;
import com.guanchazhe.news.mvp.model.entities.News;

import java.util.List;

import rx.Observable;

/**
 * Created by ranzh on 12/22/2015.
 */
public interface Repository {
    Observable<List<News>> getNews(int typeid, int attributeid, int pageindex, int pagesize);

    Observable<String> getNewsDetail(String device, String id);

    Observable<List<Commentary>> getCommentaries(String authorid, int pageindex, int pagesize);

//    Observable<String> getCommentaryDetail(String id);
}
