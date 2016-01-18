package com.guanchazhe.news.domain;

import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.mvp.model.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ranzh on 1/14/2016.
 */
public class GetAuthorListUseCase implements UseCase<List<Author>> {

    private final Repository mRepository;

    @Inject
    public GetAuthorListUseCase(Repository repository) {
        mRepository = repository;
    }

    @Override
    public Observable<List<Author>> execute(String... parameters) {
        return mRepository.getAuthors()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
