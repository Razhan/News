package com.guanchazhe.news.views.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.presenters.NewsDetailPresenter;
import com.guanchazhe.news.mvp.views.NewsDetailView;
import com.guanchazhe.news.views.listener.OnScrollChangeListener;
import com.guanchazhe.news.views.utils.GUIUtils;
import com.guanchazhe.news.views.widget.JsOperation;
import com.guanchazhe.news.views.widget.WebClient;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class CommentaryDetailActivity extends SwipeBackActivity implements NewsDetailView {

    @Bind(R.id.commentary_detail_author_image)          CircleImageView authorImageCIV;
    @Bind(R.id.commentary_detail_author_name)           TextView authorNameTV;
    @Bind(R.id.commentary_detail_author_title)          TextView authorTitleTV;
    @Bind(R.id.commentary_detail_title)                 TextView commentaryDetailTitleTV;
    @Bind(R.id.commentary_detail_content)               WebView commentaryDetailContentWV;
    @Bind(R.id.commentary_detail_header)                LinearLayout commentaryDetailHeaderLL;
    @Bind(R.id.commentary_detail_scroll)                NestedScrollView commentaryDetailNS;

    @Inject
    NewsDetailPresenter mCommentaryDetailPresenter;

    private News mNews;
    private Author mAuthor;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        initUI();
        initDependencyInjector();
        initPresenter();
        initScrollView();
    }

    private void initUI() {
        setContentView(R.layout.activity_commentary_detail);
        ButterKnife.bind(this);
    }

    private void init() {
        mNews = (News)getIntent().getSerializableExtra("news");
        mAuthor = new Author();
        mHandler=  new Handler();
    }

    private void initDependencyInjector() {

        NewsApplication avengersApplication = (NewsApplication) getApplication();
        DaggerNewsDetailComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(avengersApplication.getAppComponent())
                .build().inject(this);
    }

    private void initPresenter() {
        mCommentaryDetailPresenter.attachView(this);
        mCommentaryDetailPresenter.onCreate();
    }

    private void initScrollView() {
        commentaryDetailNS.setOnScrollChangeListener(
                new OnScrollChangeListener(commentaryDetailHeaderLL, commentaryDetailTitleTV));
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCommentaryDetailPresenter.onStop();
    }

    @Override
    public void setImageSource() {
        Glide.with(this)
                .load(mNews.getPic())
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(authorImageCIV) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        authorImageCIV.setImageBitmap(resource);
                        mCommentaryDetailPresenter.onImageReceived(resource);
                    }
                });
    }

    @Override
    public void initActivityColors(Bitmap sourceBitmap) {
        animateElementsByScale();
    }

    private void animateElementsByScale() {

        GUIUtils.showViewByScaleY(commentaryDetailTitleTV, new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                super.onAnimationEnd(animation);
                GUIUtils.showViewByScale(commentaryDetailContentWV);
            }
        });
    }

    @Override
    public void setContent(News news) {

        mHandler.post(() -> {
            commentaryDetailTitleTV.setText(news.getTitle());
            authorNameTV.setText(mNews.getAuthor());
            authorTitleTV.setText(news.getAuthortitle());

            commentaryDetailContentWV.setVisibility(View.VISIBLE);
        });

        mAuthor.setTitle(news.getAuthortitle());
        mAuthor.setPic(mNews.getPic());
        mAuthor.setSpelling(news.getAuthor());
        mAuthor.setName(mNews.getAuthor());

    }

    @Override
    public void stopWebView() {
        commentaryDetailContentWV.loadUrl("about:blank");
    }

    public static void start(Context context, News news) {
        Intent intent = new Intent(context, CommentaryDetailActivity.class);
        intent.putExtra("news", news);
        context.startActivity(intent);
    }

    @Override
    public void startWebView() {
        WebSettings settings = commentaryDetailContentWV.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);

        commentaryDetailContentWV.addJavascriptInterface(new JsOperation(this,
                (news) -> mCommentaryDetailPresenter.resultArrived((News) news)), "client");

        commentaryDetailContentWV.setWebViewClient(new WebClient(this, commentaryDetailContentWV, null));
        commentaryDetailContentWV.loadUrl(Constant.NEWSDETAILURLPREFIX + mNews.getId());
    }

    @OnClick(R.id.commentary_detail_header)
    public void OnAuthorClick() {
        CommentaryListActivity.start(this, null, mAuthor);
    }


}
