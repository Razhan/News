package com.guanchazhe.news.domain;

import com.guanchazhe.news.mvp.model.repository.Repository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ranzh on 12/29/2015.
 */
public class GetNewsDetailUseCase implements UseCase<String> {

    private final Repository mRepository;
    private int mNewsId;


    @Inject
    public GetNewsDetailUseCase(int Id, Repository repository) {
        mNewsId = Id;
        mRepository = repository;
    }

    @Override
//    @RxLogObservable
    public Observable<String> execute(String... parameters) {
        return mRepository.getNewsDetail(parameters[0], mNewsId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
