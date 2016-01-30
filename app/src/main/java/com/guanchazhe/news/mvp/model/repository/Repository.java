package com.guanchazhe.news.mvp.model.repository;

import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.model.entities.UpdateInfo;

import java.util.List;

import rx.Observable;

/**
 * Created by ranzh on 12/22/2015.
 */
public interface Repository {
    Observable<List<News>> getNews(int typeid, int attributeid, String pageindex, int pagesize);

    Observable<String> getNewsDetail(String device, int id);

    Observable<List<News>> getCommentaries(String authorid, String pageindex, int pagesize);

    Observable<List<Author>> getAuthors();

    Observable<UpdateInfo> checkUpdate(String appid, String apptoken);

}
