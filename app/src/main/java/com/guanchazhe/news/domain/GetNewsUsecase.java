package com.guanchazhe.news.domain;

import com.guanchazhe.news.model.entities.NewsItem;
import com.guanchazhe.news.model.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ranzh on 12/22/2015.
 */
public class GetNewsUsecase implements Usecase<List<NewsItem>> {

    public final static int NEWS_LIMIT = 20;
    private final Repository mRepository;
    private int currentOffset;

    @Inject
    public GetNewsUsecase(Repository repository) {
        mRepository = repository;
    }

    @Override
    public Observable<List<NewsItem>> execute() {
        return mRepository.getNews(49646, 1, 1, 20)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
