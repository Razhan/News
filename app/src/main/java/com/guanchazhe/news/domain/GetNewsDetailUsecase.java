package com.guanchazhe.news.domain;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.guanchazhe.news.model.entities.NewsItem;
import com.guanchazhe.news.model.repository.Repository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ranzh on 12/29/2015.
 */
public class GetNewsDetailUsecase implements Usecase<String> {

    private final Repository mRepository;
    private String mNewsId;


    @Inject
    public GetNewsDetailUsecase(String Id, Repository repository) {
        mNewsId = Id;
        mRepository = repository;
    }

    @Override
//    @RxLogObservable
    public Observable<String> execute() {
        return mRepository.getNewsDetail("android", "250912")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
