package com.guanchazhe.news.model.rest.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by ranzh on 1/5/2016.
 */
public class NewsJsoupApi {

    private NewsJsoupApi() {}

    public static final NewsJsoupApi getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final NewsJsoupApi INSTANCE = new NewsJsoupApi();
    }

    public Observable<String> getNewsDetail(String url) {

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> observer) {
                String newsdetail = jsoupParseFromUrl(url);
                observer.onNext(newsdetail);
            }
        });

        return observable;
    }

    private String jsoupParseFromUrl(String url) {
        Document doc = null;
        try {
            URL Url = new URL("http://mobileservice.guancha.cn/Appdetail/get/?devices=android&id=251031");
            doc = Jsoup.parse(Url, 1000 * 10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element section = doc.select("article.detail section").first();
        section.select("script").remove();
        section.select("div.m_share").remove();
        String article = section.html();
        article = article
                // normalize image url
                .replaceAll("src=\"/thumbnail", "src=\"http://m.guancha.cn/thumbnail")
                // normalize link url
                .replaceAll("href=\"/", "href=\"http://m.guancha.cn/");

        return article;
    }

}
