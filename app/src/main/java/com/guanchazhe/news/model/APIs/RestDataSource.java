package com.guanchazhe.news.model.APIs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.guanchazhe.news.model.APIs.RestfulAPIs.DetailRestfulAPIs;
import com.guanchazhe.news.model.APIs.utils.deserializers.ToStringConverterFactory;
import com.guanchazhe.news.model.entities.Commentary;
import com.guanchazhe.news.model.entities.News;
import com.guanchazhe.news.model.repository.Repository;
import com.guanchazhe.news.model.APIs.RestfulAPIs.ListRestfulAPIs;
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

    private final ListRestfulAPIs listRestfulAPIs;
    private final DetailRestfulAPIs detailRestfulAPIs;

//    private final NewsJsoupApi newsJsoupApi;


    @Inject
    public RestDataSource() {
        OkHttpClient client = new OkHttpClient();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        client.interceptors().add(loggingInterceptor);

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

        listRestfulAPIs =  listApiAdapter.create(ListRestfulAPIs.class);
        detailRestfulAPIs = detailApiAdapter.create(DetailRestfulAPIs.class);
    }

    @Override
    public Observable<List<News>> getNews(int typeid, int attributeid, int pageindex,int pagesize) {
        return listRestfulAPIs.getNews(typeid, attributeid, pageindex, pagesize)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }

    @Override
    public Observable<String> getNewsDetail(String device, String id) {

        return detailRestfulAPIs.getNewsDetail(device, id)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }

    @Override
    public Observable<List<Commentary>> getCommentaries(String authorid, int pageindex, int pagesize) {
        return listRestfulAPIs.getCommentaries(authorid, pageindex, pagesize)
                .onErrorResumeNext(throwable -> {
                    return Observable.error(throwable);
                });
    }
}
