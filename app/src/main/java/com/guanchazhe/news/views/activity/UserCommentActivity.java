package com.guanchazhe.news.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.News;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserCommentActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)                 Toolbar toolBar;
    @Bind(R.id.user_comment_content)    WebView userCommentContentWV;
    @Bind(R.id.user_comment_wrapper)    CardView userCommentWrapperCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        initToolbar();
        initWebView();
    }

    private void initUI() {
        setContentView(R.layout.activity_user_comment);
        ButterKnife.bind(this);
    }

    private void initToolbar() {
        toolBar.setTitle("网友评论");

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initWebView() {


        WebSettings settings = userCommentContentWV.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);

        userCommentContentWV.setWebViewClient(new MyWebClient());

        userCommentContentWV.loadUrl(Constant.USERCOMMENT_URL);
    }

    public static void start(Activity activity, News news) {
        Intent intent = new Intent(activity, UserCommentActivity.class);
        intent.putExtra("key", String.valueOf(news.getId()));
        intent.putExtra("title", "");

        activity.startActivity(intent);
    }

    private class MyWebClient extends WebViewClient {
        private MyWebClient() {}

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            Intent intent =getIntent();
            String key = intent.getStringExtra("key");
            String title = intent.getStringExtra("title");

            userCommentContentWV.loadUrl("javascript:loadData(\"" + key + "\",\"" + title + "\");");
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

}
