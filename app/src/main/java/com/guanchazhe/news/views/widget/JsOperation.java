package com.guanchazhe.news.views.widget;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.views.activity.ImageDetailActivity;
import com.guanchazhe.news.views.listener.WebViewLoadedListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

/**
 * Created by ranzh on 1/19/2016.
 */
public class JsOperation {
    private Activity mActivity;

    private News mNews;
    private WebViewLoadedListener mWebViewLoadedListener;


    public JsOperation(Activity activity, WebViewLoadedListener listener) {
        mActivity = activity;
        mWebViewLoadedListener = listener;
    }

    @JavascriptInterface
    public void showAuthor(String value) {
        if (value != null || !value.equals(XmlPullParser.NO_NAMESPACE)) {
            try {
                JSONObject json = new JSONObject(value);

                mNews = new News();

                mNews.setPic(json.getString("AuthorPic"));
                mNews.setAuthor(json.getString("AuthorID"));
                mNews.setAuthortitle(json.getString("AuthorSummary"));
                mNews.setTitle(json.getString("Title"));

                if (mWebViewLoadedListener != null) {
                    mWebViewLoadedListener.afterWebViewLoaded(mNews);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @JavascriptInterface
    public void showImage(String url) {
        if (url != null || !url.equals(XmlPullParser.NO_NAMESPACE)) {
            Intent intent = new Intent(mActivity, ImageDetailActivity.class);
            intent.putExtra("url", url);
            mActivity.startActivity(intent);
        }
    }
}