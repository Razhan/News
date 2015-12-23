package com.guanchazhe.news.model.rest;

import com.guanchazhe.news.model.entities.NewsItem;
import com.guanchazhe.news.model.repository.Repository;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import javax.inject.Inject;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by ranzh on 12/22/2015.
 */
public class RestDataSource implements Repository {

    private final NewsApi newsApi;

    @Inject
    public RestDataSource() {
        OkHttpClient client = new OkHttpClient();

        Retrofit marvelApiAdapter = new Retrofit.Builder()
                .baseUrl(NewsApi.END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        newsApi =  marvelApiAdapter.create(NewsApi.class);
    }

    @Override
    public Observable<List<NewsItem>> getNews(int typeid, int attributeid, int pageindex,int pagesize) {
        return newsApi.getArticles(typeid, attributeid, pageindex, pagesize)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }

}
