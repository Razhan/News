package com.guanchazhe.news.domain;

import com.guanchazhe.news.mvp.model.entities.VersionInfo;
import com.guanchazhe.news.mvp.model.repository.Repository;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ran.zhang on 1/30/16.
 */
public class GetVersionInfoUseCase implements UseCase<VersionInfo> {
    private final Repository mRepository;

    @Inject
    public GetVersionInfoUseCase(Repository repository) {
        mRepository = repository;
    }

    @Override
    public Observable<VersionInfo> execute(String... parameters) {
        return mRepository.getVersionInfo(parameters[0], parameters[1])
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}