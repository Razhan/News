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
public class GetNewsListUseCase implements UseCase<List<News>> {

    public final static int NEWS_LIMIT = 20;

    public final int mAttributeId;
    public final int mChannelId;


    private final Repository mRepository;

    @Inject
    public GetNewsListUseCase(Repository repository, int attributrId, int channelId) {
        mRepository = repository;
        mAttributeId = attributrId;
        mChannelId = channelId;
    }

    @Override
//    @RxLogObservable
    public Observable<List<News>> execute(String... parameters) {
        return mRepository.getNews(mChannelId, mAttributeId, parameters[0], NEWS_LIMIT)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
