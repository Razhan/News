package com.guanchazhe.news.views.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.views.utils.AnimUtils;
import com.guanchazhe.news.views.utils.TransitionUtils;
import com.guanchazhe.news.databinding.ActivityNewsDetailBinding;
import com.guanchazhe.news.injector.components .DaggerNewsDetailComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.NewsDetailModule;
import com.guanchazhe.news.mvp.presenters.NewsDetailPresenter;
import com.guanchazhe.news.mvp.views.NewsDetailView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailView {

    private static final String EXTRA_NEWS_TITLE    = "news.title";
    public static final String EXTRA_NEWS_ID       = "news.id";

    @BindInt(R.integer.duration_medium)                 int mAnimMediumDuration;
    @BindInt(R.integer.duration_huge)                   int mAnimHugeDuration;
    @BindColor(R.color.colorPrimaryDark)                int mColorPrimaryDark;

    @Bind(R.id.character_biography)                     WebView content;

    @Inject NewsDetailPresenter mNewsDetailPresenter;
    private ActivityNewsDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeBinding();
        initButterknife();
        initUI();
        initializeDependencyInjector();
        initializePresenter();
        initToolbar();
        initTransitions();
    }

    private void initUI() {
        WebSettings settings = content.getSettings();
        settings.setTextZoom(110);
    }

    private void initializeBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);
    }

    private void initButterknife() {
        ButterKnife.bind(this);
    }

    private void initializeDependencyInjector() {
        NewsApplication avengersApplication = (NewsApplication) getApplication();

        String NewsId = getIntent().getStringExtra(EXTRA_NEWS_ID);

        DaggerNewsDetailComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(avengersApplication.getAppComponent())
                .newsDetailModule(new NewsDetailModule(NewsId))
                .build().inject(this);
    }

    private void initializePresenter() {

        News news = (News)getIntent().getSerializableExtra("news");

        String NewsId = news.getId();
        String NewsTitle = news.getTitle();

        mNewsDetailPresenter.attachView(this);
        mNewsDetailPresenter.setCharacterId(NewsId);
        mNewsDetailPresenter.initializePresenter(NewsId, NewsTitle, news);
        mNewsDetailPresenter.onCreate();
        mBinding.setPresenter(mNewsDetailPresenter);
    }

    private void initToolbar() {
        mBinding.characterCollapsing.setExpandedTitleTextAppearance(
                R.style.Text_CollapsedExpanded);

        mBinding.characterToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initTransitions() {

        Transition enterTransition = TransitionUtils.buildSlideTransition(Gravity.BOTTOM);
        enterTransition.setDuration(mAnimMediumDuration);

        getWindow().setEnterTransition(enterTransition);
        getWindow().setReturnTransition(TransitionUtils.buildSlideTransition(Gravity.BOTTOM));

        View collapsingToolbar = mBinding.characterCollapsing;
        View imageReveal = mBinding.characterImageReveal;
        collapsingToolbar.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        collapsingToolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        int width = imageReveal.getWidth();
                        int height = imageReveal.getHeight();

                        AnimUtils.showRevealEffect(imageReveal, width / 2, height / 2, null);
                    }
                });
    }

    public void initActivityColors(Bitmap sourceBitmap) {
        Palette.from(sourceBitmap)
                .generate(palette -> {
                    View imageCover = mBinding.characterImageReveal;
                    int darkVibrant = palette.getDarkVibrantColor(mColorPrimaryDark);

                    mBinding.setSwatch(palette.getDarkVibrantSwatch());

                    ValueAnimator colorAnimation = ValueAnimator.ofArgb(mColorPrimaryDark, darkVibrant);
                    colorAnimation.setDuration(mAnimHugeDuration);
                    colorAnimation.addUpdateListener(animator -> {
                        imageCover.setBackgroundColor((Integer) animator.getAnimatedValue());
                    });

                    colorAnimation.start();
                    getWindow().setStatusBarColor(darkVibrant);
                });
    }

    @Override
    public void hideRevealViewByAlpha() {
        mBinding.characterImageReveal
                .animate()
                .alpha(0f)
                .setDuration(mAnimHugeDuration)
                .start();
    }

    @Override
    public void showError(String errorMessage) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_error))
                .setPositiveButton(getString(R.string.action_accept), (dialog, which) -> finish())
                .setMessage(errorMessage)
                .setCancelable(false).show();
    }

    @Override
    public void bindNews(News news) {
        mBinding.setNews(news);
    }

    @Override
    public void setContent(News news) {
        content.loadDataWithBaseURL(null, news.getContent(), "text/html","UTF-8", null);
        // contentTextView.setText(Html.fromHtml(news.getContent(), new GlideImageGetter(contentTextView), null));
    }

    @Override
    public void stopWebView() {
        content.loadUrl("about:blank");
    }

    @BindingAdapter({"source", "presenter"})
    public static void setImageSource(ImageView v, String url, NewsDetailPresenter detailPresenter) {
        Glide.with(v.getContext())
            .load(url)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(new BitmapImageViewTarget(v) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    super.onResourceReady(resource, glideAnimation);
                    v.setImageBitmap(resource);
                    detailPresenter.onImageReceived(resource);
                }
            });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mNewsDetailPresenter.onStop();
    }

    public static void start(Context context, News news) {
        Intent characterDetailIntent = new Intent(context, NewsDetailActivity.class);
        characterDetailIntent.putExtra("news", news);
        context.startActivity(characterDetailIntent);
    }

}
