package com.guanchazhe.news.views.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.guanchazhe.news.NewsApplication;
import com.guanchazhe.news.R;
import com.guanchazhe.news.injector.components.DaggerCommentaryListComponent;
import com.guanchazhe.news.injector.modules.ActivityModule;
import com.guanchazhe.news.injector.modules.CommentaryListModule;
import com.guanchazhe.news.mvp.Constant;
import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.mvp.presenters.CommentaryListPresenter;
import com.guanchazhe.news.mvp.views.CommentaryListView;
import com.guanchazhe.news.views.adapter.CommentaryListAdapter;
import com.guanchazhe.news.views.listener.EndlessScrollListener;
import com.guanchazhe.news.views.widget.BetterViewAnimator;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentaryListActivity extends BaseActivity implements CommentaryListView {

    @Bind(R.id.rv_list_content_listview)            RecyclerView contentRV;
    @Bind(R.id.commentary_list_collapsing)          CollapsingToolbarLayout CollapsingToolbarLayout;
    @Bind(R.id.commentary_list_toolbar)             Toolbar toolBar;
    @Bind(R.id.bv_list_content_viewanimator)        BetterViewAnimator viewAnimator;
    @Bind(R.id.news_image)                          CircleImageView characterImageCIV;
    @Bind(R.id.author_title)                        TextView authorTitleTV;
    @Bind(R.id.author_name)                         TextView authorNameTV;

    @Inject
    CommentaryListPresenter mCommentaryListPresenter;

    private CommentaryListAdapter mCommentaryListAdapter;
    Author author;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDependencyInjector();
        initPresenter();
        initRecyclerView();
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_commentary_list);
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolbar() {
        CollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedExpanded);

        toolBar.setTitle(R.string.author_column);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());

        CollapsingToolbarLayout.setContentScrimResource(R.color.colorPrimary);

        author = (Author) getIntent().getSerializableExtra(Constant.AUTHORNAME);
        authorNameTV.setText(author.getName());
        authorTitleTV.setText(author.getTitle());

        Glide.with(this)
                .load(author.getPic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.def_placeholder)
                .into(characterImageCIV);
    }

    private void initDependencyInjector() {

        DaggerCommentaryListComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(((NewsApplication) getApplication()).getAppComponent())
                .commentaryListModule(new CommentaryListModule(author.getSpelling()))
                .build().inject(this);
    }

    private void initPresenter() {
        mCommentaryListPresenter.attachView(this);
        mCommentaryListPresenter.onCreate();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contentRV.setLayoutManager(linearLayoutManager);
        contentRV.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mCommentaryListPresenter.onListEndReached(currentPage);
            }
        });

        mCommentaryListAdapter = new CommentaryListAdapter(contentRV, null, false,
                (view, data, position) -> mCommentaryListPresenter.onElementClick((News) data));

        contentRV.setAdapter(mCommentaryListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mCommentaryListPresenter.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCommentaryListPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCommentaryListPresenter.onStop();
    }

    @Override
    public void setCommentaryList(List<News> commentaries) {
        mCommentaryListAdapter.set(commentaries);
    }

    @Override
    public void addCommentaryList(List<News> moreCommentaries) {
        mCommentaryListAdapter.add(moreCommentaries);
    }

    @Override
    public void clearCommentaryList() {
        mCommentaryListAdapter.clear();
    }

    @Override
    public void showNewsListView() {
        viewAnimator.setDisplayedChildId(R.id.rv_list_content_listview);
    }

    @Override
    public void showLoadingView() {
        viewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_LOADING);
    }

    @Override
    public void showErrorView() {
        viewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_ERROR);
    }

    @Override
    public boolean isContentDisplayed() {
        return viewAnimator.getDisplayedChildId() == R.id.rv_list_content_listview;
    }

    @Override
    public void showDetailScreen(News commentary) {
        CommentaryDetailActivity.start(this, commentary);
    }

    public static void start(Activity activity, View view, Author anthor) {
        Intent intent = new Intent(activity, CommentaryListActivity.class);

        Bundle mBundle = new Bundle();
        mBundle.putSerializable(Constant.AUTHORNAME, anthor);
        intent.putExtras(mBundle);

        if (view != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(activity, view, "image_shared");
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }
}
