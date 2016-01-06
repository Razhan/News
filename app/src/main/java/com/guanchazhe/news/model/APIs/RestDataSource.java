package com.guanchazhe.news.model.APIs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.guanchazhe.news.model.entities.News;
import com.guanchazhe.news.model.repository.Repository;
import com.guanchazhe.news.model.APIs.JsoupAPIs.NewsJsoupApi;
import com.guanchazhe.news.model.APIs.RestfulAPIs.NewsRestfulApi;
import com.guanchazhe.news.model.APIs.utils.deserializers.NewsResultsDeserializer;
import com.guanchazhe.news.model.APIs.utils.interceptors.HttpLoggingInterceptor;
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

    private final NewsRestfulApi newsRestfulApi;
    private final NewsJsoupApi newsJsoupApi;


    @Inject
    public RestDataSource() {
        OkHttpClient client = new OkHttpClient();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        client.interceptors().add(loggingInterceptor);

        Gson customGsonInstance = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<News>>() {
                }.getType(),
                        new NewsResultsDeserializer<News>())
                .create();


        Retrofit marvelApiAdapter = new Retrofit.Builder()
                .baseUrl(NewsRestfulApi.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(customGsonInstance))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        newsRestfulApi =  marvelApiAdapter.create(NewsRestfulApi.class);
        newsJsoupApi = NewsJsoupApi.getInstance();
    }

    @Override
    public Observable<List<News>> getNews(int typeid, int attributeid, int pageindex,int pagesize) {
        return newsRestfulApi.getNews(typeid, attributeid, pageindex, pagesize)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }

    @Override
    public Observable<String> getNewsDetail(String id) {

        return newsJsoupApi.getNewsDetail(id);
    }

}
