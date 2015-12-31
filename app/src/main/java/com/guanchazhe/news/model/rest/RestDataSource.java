package com.guanchazhe.news.model.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.guanchazhe.news.model.entities.NewsItem;
import com.guanchazhe.news.model.repository.Repository;
import com.guanchazhe.news.model.rest.utils.deserializers.NewsResultsDeserializer;
import com.guanchazhe.news.model.rest.utils.interceptors.HttpLoggingInterceptor;
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

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.interceptors().add(loggingInterceptor);

        Gson customGsonInstance = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<NewsItem>>() {}.getType(),
                        new NewsResultsDeserializer<NewsItem>())
                .create();

        Retrofit marvelApiAdapter = new Retrofit.Builder()
                .baseUrl(NewsApi.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(customGsonInstance))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        newsApi =  marvelApiAdapter.create(NewsApi.class);
    }

    @Override
    public Observable<List<NewsItem>> getNews(int typeid, int attributeid, int pageindex,int pagesize) {
        return newsApi.getNews(typeid, attributeid, pageindex, pagesize)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }

    @Override
    public Observable<String> getNewsDetail(String device, int id) {
        return newsApi.getNewsDetail(device, id)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }
}
