package com.guanchazhe.news.injector.modules;

import com.guanchazhe.news.domain.GetCommentaryListUseCase;
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
    GetCommentaryListUseCase provideGetNewsDetailUsecase (Repository repository) {
        return new GetCommentaryListUseCase(mAuthorName, repository);
    }
}