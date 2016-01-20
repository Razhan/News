package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.views.widget.JsOperation;
import com.guanchazhe.news.views.widget.WebClient;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsWebViewActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)                         Toolbar toolbar;
    @Bind(R.id.news_webview_content)            WebView newsContentWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        initToolbar();
        initWebView();
    }

    private void initUI() {
        setContentView(R.layout.activity_news_webview);
        ButterKnife.bind(this);
    }

    private void initToolbar() {
        toolbar.setTitle("旧闻");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initWebView() {

        String url = getIntent().getStringExtra(Constant.NEWSURL);

        WebSettings settings = newsContentWebview.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);

        newsContentWebview.addJavascriptInterface(new JsOperation(this, null), "client");
        newsContentWebview.setWebViewClient(new WebClient(this, newsContentWebview, null));

        newsContentWebview.loadUrl(url);
    }

}
