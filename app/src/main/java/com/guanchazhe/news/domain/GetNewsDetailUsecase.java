package com.guanchazhe.news.domain;

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
    public Observable<String> execute(String... parameters) {
        return mRepository.getNewsDetail(parameters[0], parameters[1])
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
