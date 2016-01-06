package com.guanchazhe.news.model.APIs.JsoupAPIs;

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

    public Observable<String> getNewsDetail(String id) {

        String url = generateUrl(id);

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
            URL Url = new URL(url);
            doc = Jsoup.parse(Url, 1000 * 10);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element section = doc.select("article.detail section").first();
        section.select("script").remove();
        section.select("div.m_share").remove();
        String article = section.html();
        article = article
                .replaceAll("src=\"/thumbnail", "src=\"http://m.guancha.cn/thumbnail")
                .replaceAll("href=\"/", "href=\"http://m.guancha.cn/")
                .replaceAll("src=\"/default_pic.png\" url", "width=\\\"100%\\\" src")
                .replaceAll("<p", "<p style=\"line-height:1.5\"");

        return article;
    }

    private String generateUrl(String id) {
        String Url = "http://mobileservice.guancha.cn/Appdetail/get/?devices=android" + "&id=" + id;
        return Url;
    }

}
