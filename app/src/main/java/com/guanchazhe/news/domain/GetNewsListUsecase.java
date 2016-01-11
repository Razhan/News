package com.guanchazhe.news.domain;

import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.model.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ranzh on 12/22/2015.
 */
public class GetNewsListUsecase implements Usecase<List<News>> {

    public final static int NEWS_LIMIT = 20;
    private final Repository mRepository;
    private int currentOffset;

    @Inject
    public GetNewsListUsecase(Repository repository) {
        mRepository = repository;
    }

    @Override
//    @RxLogObservable
    public Observable<List<News>> execute(String... parameters) {
        return mRepository.getNews(49646, 1, 1, 20)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
