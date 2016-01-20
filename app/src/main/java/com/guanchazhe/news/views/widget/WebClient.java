package com.guanchazhe.news.views.widget;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.views.activity.NewsWebViewActivity;
import com.guanchazhe.news.views.listener.WebViewLoadedListener;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by ranzh on 1/20/2016.
 */
public class WebClient extends WebViewClient {

    private Activity mActivity;
    WebView mWebView;
    WebViewLoadedListener mWebViewLoadedListener;

    public WebClient(Activity activity, WebView webView, WebViewLoadedListener listener) {
        mActivity = activity;
        mWebView = webView;
        mWebViewLoadedListener = listener;
    }

    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);

        mWebView.loadUrl("javascript:showAuthor();");
        mWebView.loadUrl("javascript:$.startload();");
        mWebView.loadUrl("javascript:$.doZoom(" + 16 + ");");

        if (mWebViewLoadedListener != null) {
            mWebViewLoadedListener.afterWebViewLoaded(null);
        }
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (url.indexOf("guancha") == -1) {
            return false;
        }
        String[] urs = url.split("[_]");
        if (urs.length < 2) {
            return false;
        }
        String id = urs[urs.length - 1].replace(".shtml", XmlPullParser.NO_NAMESPACE);

        Intent intent = new Intent(mActivity, NewsWebViewActivity.class);
        intent.putExtra(Constant.NEWSURL, Constant.NEWSDETAILWEBURLPREFIX + id);
        mActivity.startActivity(intent);

        return true;
    }
}