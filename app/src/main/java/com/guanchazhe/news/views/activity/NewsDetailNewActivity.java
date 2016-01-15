package com.guanchazhe.news.views.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.presenters.NewsDetailPresenter;
import com.guanchazhe.news.mvp.views.NewsDetailNewView;
import com.guanchazhe.news.views.listener.OnScrollChangeListener;
import com.guanchazhe.news.views.utils.GUIUtils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ranzh on 1/15/2016.
 */
public class NewsDetailNewActivity extends AppCompatActivity implements NewsDetailNewView {

    public static final String EXTRA_NEWS_ID = "newsId";

    @Bind(R.id.item_movie_cover)                ImageView itemMovieCover;
    @Bind(R.id.activity_detail_title)           TextView mTitle;
    @Bind(R.id.activity_detail_fab)             ImageButton mFabButton;
    @Bind(R.id.character_biography)             WebView contentWebView;
    @Bind(R.id.activity_detail_book_info)       LinearLayout activityDetailBookInfo;
    @Bind(R.id.item_movie_cover_wrapper)        FrameLayout imageWrapper;
    @Bind(R.id.activity_detail_scroll)          NestedScrollView mScrollView;

    @BindColor(R.color.colorPrimaryDark)        int mColorPrimaryDark;
    @BindInt(R.integer.duration_medium)         int mAnimMediumDuration;

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
        setContentView(R.layout.activity_news_detail_new);
        ButterKnife.bind(this);
    }

    private void initDependencyInjector() {
        mNews = (News)getIntent().getSerializableExtra("news");

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
    public void setImageSource() {
        Glide.with(itemMovieCover.getContext())
                .load(mNews.getPic())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(itemMovieCover) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        itemMovieCover.setImageBitmap(resource);
                        mNewsDetailPresenter.onImageReceived(resource);
                    }
                });
    }

    @Override
    public void initActivityColors(Bitmap sourceBitmap) {
        Palette.from(sourceBitmap)
                .generate(palette -> {

                    int darkVibrant = palette.getDarkVibrantColor(mColorPrimaryDark);

                    ValueAnimator colorAnimation = ValueAnimator.ofArgb(mColorPrimaryDark, darkVibrant);
                    colorAnimation.setDuration(mAnimMediumDuration);
                    colorAnimation.addUpdateListener(animator ->
                                    imageWrapper.setBackgroundColor((Integer) animator.getAnimatedValue())
                    );

                    colorAnimation.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            animateElementsByScale();
                        }
                    });

                    colorAnimation.start();
                    getWindow().setStatusBarColor(darkVibrant);
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
        contentWebView.setVisibility(View.VISIBLE);
        contentWebView.loadDataWithBaseURL(null, news.getContent(), "text/html", "UTF-8", null);
    }


    @Override
    public void stopWebView() {
        contentWebView.loadUrl("about:blank");
    }

    public static void start(Context context, News news) {
        Intent characterDetailIntent = new Intent(context, NewsDetailNewActivity.class);
        characterDetailIntent.putExtra("news", news);
        context.startActivity(characterDetailIntent);
    }

    @OnClick(R.id.activity_detail_fab)
    public void onClick() {
        Log.d("activity_detail_fab", "activity_detail_fab");
    }

}
