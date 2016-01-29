package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.views.widget.JsOperation;
import com.guanchazhe.news.views.widget.WebClient;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsWebViewActivity extends BaseActivity {

    @Bind(R.id.toolbar)                         Toolbar toolBar;
    @Bind(R.id.news_webview_content)            WebView newsContentWV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWebView();
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_news_webview);
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolbar() {
        toolBar.setTitle(R.string.old_news);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initWebView() {

        String url = getIntent().getStringExtra(Constant.NEWSURL);

        WebSettings settings = newsContentWV.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);

        newsContentWV.addJavascriptInterface(new JsOperation(this, null), "client");
        newsContentWV.setWebViewClient(new WebClient(this, newsContentWV, null));

        newsContentWV.loadUrl(url);
    }

}
