package com.guanchazhe.news.mvp.model.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.mvp.model.entities.UpdateInfo;
import com.guanchazhe.news.mvp.model.repository.restfulAPIs.CheckUpdateFromFIRAPIs;
import com.guanchazhe.news.mvp.model.repository.restfulAPIs.DetailRestfulAPIs;
import com.guanchazhe.news.mvp.model.repository.restfulAPIs.ListRestfulAPIs;
import com.guanchazhe.news.mvp.model.repository.utils.deserializers.ToStringConverterFactory;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.model.repository.utils.deserializers.NewsResultsDeserializer;
import com.guanchazhe.news.mvp.model.repository.utils.interceptors.HttpLoggingInterceptor;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by ranzh on 12/22/2015.
 */
public class RestDataSource implements Repository {

    private int CONNECTION_TIMEOUT = 5 * 1000;
    private final ListRestfulAPIs listRestfulAPIs;
    private final DetailRestfulAPIs detailRestfulAPIs;
    private final CheckUpdateFromFIRAPIs checkUpdateFromFIRAPIs;


    @Inject
    public RestDataSource() {
        OkHttpClient client = new OkHttpClient();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        client.interceptors().add(loggingInterceptor);
        client.setConnectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
        client.setReadTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);

        Gson customGsonInstance = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<News>>() {}.getType(),
                        new NewsResultsDeserializer<News>())
                .create();


        Retrofit listApiAdapter = new Retrofit.Builder()
                .baseUrl(ListRestfulAPIs.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(customGsonInstance))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        Retrofit detailApiAdapter = new Retrofit.Builder()
                .baseUrl(ListRestfulAPIs.END_POINT)
                .addConverterFactory(new ToStringConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        Retrofit checkUpdateAdapter = new Retrofit.Builder()
                .baseUrl(CheckUpdateFromFIRAPIs.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(customGsonInstance))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();


        listRestfulAPIs =  listApiAdapter.create(ListRestfulAPIs.class);
        detailRestfulAPIs = detailApiAdapter.create(DetailRestfulAPIs.class);
        checkUpdateFromFIRAPIs = checkUpdateAdapter.create(CheckUpdateFromFIRAPIs.class);
    }

    @Override
    public Observable<List<News>> getNews(int typeid, int attributeid, String pageindex,int pagesize) {
        return listRestfulAPIs.getNews(typeid, attributeid, pageindex, pagesize)
                .retry(2)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }

    @Override
    public Observable<String> getNewsDetail(String device, int id) {

        return detailRestfulAPIs.getNewsDetail(device, id)
                .retry(2)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }

    @Override
    public Observable<List<News>> getCommentaries(String authorid, String pageindex, int pagesize) {
        return listRestfulAPIs.getCommentaries(authorid, pageindex, pagesize)
                .retry(2)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }

    @Override
    public Observable<List<Author>> getAuthors() {
        return listRestfulAPIs.getAuthors()
                .retry(2)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }

    @Override
    public Observable<UpdateInfo> checkUpdate(String appid, String apptoken) {
        return checkUpdateFromFIRAPIs.checkUpdate(appid, apptoken)
                .retry(2)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }
}
