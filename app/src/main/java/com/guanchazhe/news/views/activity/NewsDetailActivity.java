package com.guanchazhe.news.views.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.R;
import com.guanchazhe.news.injector.components.DaggerNewsDetailComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.NewsDetailModule;
import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.presenters.NewsDetailPresenter;
import com.guanchazhe.news.mvp.views.NewsDetailView;
import com.guanchazhe.news.views.listener.OnScrollChangeListener;
import com.guanchazhe.news.views.utils.GUIUtils;
import com.guanchazhe.news.views.widget.JsOperation;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ranzh on 1/15/2016.
 */
public class NewsDetailActivity extends AppCompatActivity implements NewsDetailView {

    @Bind(R.id.item_movie_cover)                ImageView itemMovieCover;
    @Bind(R.id.activity_detail_title)           TextView mTitle;
    @Bind(R.id.activity_detail_fab)             FloatingActionButton mFabButton;
    @Bind(R.id.character_biography)             WebView contentWebView;
    @Bind(R.id.activity_detail_book_info)       CardView activityDetailBookInfo;
    @Bind(R.id.activity_detail_scroll)          NestedScrollView mScrollView;
    @Bind(R.id.item_movie_cover_wrapper)        FrameLayout imageWrapper;

    @BindColor(R.color.colorPrimary)            int mColorPrimary;
    @BindColor(R.color.colorPrimaryDark)        int mColorPrimaryDark;

    @Inject
    NewsDetailPresenter mNewsDetailPresenter;

    private News mNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();
        initDependencyInjector();
        initPresenter();
        initScrollView();
    }

    private void initUI() {
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        itemMovieCover.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        itemMovieCover.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        AnimatorListenerAdapter listenerAdapter = new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                animateElementsByScale();
                            }
                        };

                        GUIUtils.showRevealEffect(NewsDetailActivity.this, itemMovieCover,
                                itemMovieCover.getWidth(), 0, listenerAdapter);
                    }
                }
        );
    }

    private void initDependencyInjector() {
        mNews = (News) getIntent().getSerializableExtra("news");

        NewsApplication avengersApplication = (NewsApplication) getApplication();
        DaggerNewsDetailComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(avengersApplication.getAppComponent())
                .newsDetailModule(new NewsDetailModule(mNews.getId()))
                .build().inject(this);
    }

    private void initPresenter() {

        mNewsDetailPresenter.attachView(this);
        mNewsDetailPresenter.initializePresenter(mNews);
        mNewsDetailPresenter.onCreate();
    }

    private void initScrollView() {
        mScrollView.setOnScrollChangeListener(new OnScrollChangeListener(itemMovieCover, mTitle));
    }

    @Override
    protected void onStop() {
        super.onStop();
        mNewsDetailPresenter.onStop();
    }

    @Override
    public void finish() {
        super.finish();
        stopWebView();
    }

    @Override
    public void setImageSource() {
        Glide.with(this)
                .load(mNews.getPic())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(itemMovieCover) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
//                        itemMovieCover.setImageBitmap(resource);
                        mNewsDetailPresenter.onImageReceived(resource);
                    }
                });
    }

    @Override
    public void initActivityColors(Bitmap sourceBitmap) {
        Palette.from(sourceBitmap)
                .generate(palette -> {

                    int darkVibrant = palette.getDarkVibrantColor(mColorPrimaryDark);

                    getWindow().setStatusBarColor(darkVibrant);
                    imageWrapper.setBackgroundColor(darkVibrant);
                    mTitle.setBackgroundColor(darkVibrant);
                });
    }

    private void animateElementsByScale() {

        GUIUtils.showViewByScale(mFabButton);
        GUIUtils.showViewByScaleY(mTitle, new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                super.onAnimationEnd(animation);
                GUIUtils.showViewByScale(activityDetailBookInfo);
            }
        });
    }

    @Override
    public void bindNews(News news) {
        mNews = news;
    }

    @Override
    public void setContent(News news) {
        mTitle.setText(news.getTitle());

        WebSettings settings = contentWebView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);

        contentWebView.addJavascriptInterface(new JsOperation(this), "client");
        contentWebView.setWebViewClient(new MyWebClient());
        contentWebView.loadUrl("http://mobileservice.guancha.cn/Appdetail/get/?devices=android&id=" + news.getId());
    }

    @Override
    public void stopWebView() {
        contentWebView.loadUrl("about:blank");
    }

    public static void start(Context context, News news) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra("news", news);
        context.startActivity(intent);
    }

    @OnClick(R.id.activity_detail_fab)
    public void onClick() {
        Log.d("activity_detail_fab", "activity_detail_fab");
    }

    private class MyWebClient extends WebViewClient {

        private MyWebClient() {
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            contentWebView.setVisibility(View.VISIBLE);
            contentWebView.loadUrl("javascript:$.startload();");
            contentWebView.loadUrl("javascript:$.doZoom(" + 18 + ");");
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

            Intent intent = new Intent(NewsDetailActivity.this, NewsDetailActivity.class);

            intent.putExtra("news", new News(Integer.parseInt(id)));
            NewsDetailActivity.this.startActivity(intent);

            return true;
        }
    }
}
