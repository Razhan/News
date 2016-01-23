package com.guanchazhe.news.views.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.R;
import com.guanchazhe.news.injector.components.DaggerNewsDetailComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.presenters.NewsDetailPresenter;
import com.guanchazhe.news.mvp.views.NewsDetailView;
import com.guanchazhe.news.utils.Utils;
import com.guanchazhe.news.views.listener.OnScrollChangeListener;
import com.guanchazhe.news.views.utils.GUIUtils;
import com.guanchazhe.news.views.widget.JsOperation;
import com.guanchazhe.news.views.widget.WebClient;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * Created by ranzh on 1/15/2016.
 */
public class NewsDetailActivity extends SwipeBackActivity implements NewsDetailView {

    @Bind(R.id.item_movie_cover)                ImageView itemMovieCoverIV;
    @Bind(R.id.activity_detail_title)           TextView titleTV;
    @Bind(R.id.activity_detail_fab)             FloatingActionButton fabButton;
    @Bind(R.id.news_webview_content)            WebView contentWV;
    @Bind(R.id.activity_detail_scroll)          NestedScrollView ScrollView;
    @Bind(R.id.item_movie_cover_wrapper)        FrameLayout imageWrapperFL;

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

        itemMovieCoverIV.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        itemMovieCoverIV.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        AnimatorListenerAdapter listenerAdapter = new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                animateElementsByScale();
                            }
                        };

                        GUIUtils.showRevealEffect(NewsDetailActivity.this, itemMovieCoverIV,
                                itemMovieCoverIV.getWidth(), 0, listenerAdapter);
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
                .build().inject(this);
    }

    private void initPresenter() {

        mNewsDetailPresenter.attachView(this);
        mNewsDetailPresenter.onCreate();
    }

    private void initScrollView() {
        ScrollView.setOnScrollChangeListener(new OnScrollChangeListener(itemMovieCoverIV, titleTV));
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
                .into(new BitmapImageViewTarget(itemMovieCoverIV) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
//                        itemMovieCoverIV.setImageBitmap(resource);
                        mNewsDetailPresenter.onImageReceived(resource);
                    }
                });
    }

    @Override
    public void initActivityColors(Bitmap sourceBitmap) {
//        Palette.from(sourceBitmap)
//                .generate(palette -> {
//
//                    int darkVibrant = palette.getDarkVibrantColor(mColorPrimaryDark);
//
//                    getWindow().setStatusBarColor(darkVibrant);
//                    imageWrapperFL.setBackgroundColor(darkVibrant);
//                    titleTV.setBackgroundColor(darkVibrant);
//                });
    }

    private void animateElementsByScale() {

        GUIUtils.showViewByScale(fabButton);
        GUIUtils.showViewByScaleY(titleTV, new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                super.onAnimationEnd(animation);
                GUIUtils.showViewByScale(contentWV);
            }
        });
    }

    @Override
    public void setContent(News news) {
        titleTV.setText(mNews.getTitle());
        contentWV.setVisibility(View.VISIBLE);
        Utils.convertActivityToTranslucent(this);
    }

    @Override
    public void startWebView() {
        WebSettings settings = contentWV.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);

        contentWV.addJavascriptInterface(new JsOperation(this, null), "client");

        contentWV.setWebViewClient(new WebClient(this, contentWV,
                        (news) -> mNewsDetailPresenter.resultArrived((News) news))
        );
        contentWV.loadUrl(Constant.NEWSDETAILURLPREFIX + mNews.getId());
    }

    @Override
    public void stopWebView() {
        contentWV.loadUrl("about:blank");
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

}
