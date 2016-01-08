package com.guanchazhe.news.domain;

import com.guanchazhe.news.model.entities.Commentary;
import com.guanchazhe.news.model.repository.Repository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ranzh on 1/6/2016.
 */
public class GetCommentaryListUsecase implements Usecase<List<Commentary>> {

    private final Repository mRepository;
    private final String mAuthorName;

    @Inject
    public GetCommentaryListUsecase(String authorname, Repository repository) {
        mRepository = repository;
        mAuthorName = authorname;
    }


//    http://mobileservice.guancha.cn/app/GetAuthorNews/?authorid=FuLangXiSiÂ·FuShan&pageindex=1&pagesize=20
    @Override
    public Observable<List<Commentary>> execute(String... parameters) {
        return mRepository.getCommentaries("Libin", 1, 20)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());    }

}
