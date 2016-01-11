package com.guanchazhe.news.injector.modules;

import com.guanchazhe.news.domain.GetCommentaryListUsecase;
import com.guanchazhe.news.injector.scopes.Activity;
import com.guanchazhe.news.mvp.model.repository.Repository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ranzh on 1/6/2016.
 */
@Module
public class CommentaryListModule {
    private final String mAuthorName;

    public CommentaryListModule(String name) {
        mAuthorName = name;
    }

    @Provides
    @Activity
    GetCommentaryListUsecase provideGetNewsDetailUsecase (Repository repository) {
        return new GetCommentaryListUsecase(mAuthorName, repository);
    }
}