package com.guanchazhe.news.views.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.guanchazhe.news.mvp.model.entities.Commentary;
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

public class CommentaryListActivity extends AppCompatActivity implements CommentaryListView {

    private final static String EXTRA_AUTHOR_NAME = "author_name";

    @Bind(R.id.commentary_list_recycler)        RecyclerView mCollectionRecycler;
    @Bind(R.id.commentary_list_collapsing)      CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.commentary_list_toolbar)         Toolbar mToolBar;
    @Bind(R.id.commentary_list_animator)        BetterViewAnimator mViewAnimator;
    @Bind(R.id.news_image)                 CircleImageView characterImage;
    @Bind(R.id.author_title)                    TextView authorTitle;
    @Bind(R.id.author_name)                     TextView authorName;

    @Inject
    CommentaryListPresenter mCommentaryListPresenter;



    private CommentaryListAdapter mCommentaryListAdapter;
    Author author;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
        initToolbar();
        initDependencyInjector();
        initPresenter();
        initRecyclerView();
    }

    private void initUi() {
        setContentView(R.layout.activity_commentary_list);
        ButterKnife.bind(this);
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

    private void initToolbar() {
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.Text_CollapsedExpanded);

        mToolBar.setTitle("专栏");
        mToolBar.setNavigationOnClickListener(v -> onBackPressed());

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mCollapsingToolbarLayout.setContentScrimResource(R.color.colorPrimary);

        author = (Author) getIntent().getSerializableExtra(EXTRA_AUTHOR_NAME);
        authorName.setText(author.getName());
        authorTitle.setText(author.getTitle());

        Glide.with(this)
                .load(author.getPic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.error_placeholder)
                .into(characterImage);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mCollectionRecycler.setLayoutManager(linearLayoutManager);
        mCollectionRecycler.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                mCommentaryListPresenter.onListEndReached(currentPage);
            }
        });

        mCommentaryListAdapter = new CommentaryListAdapter(mCollectionRecycler, null,
                (view, data, position) -> mCommentaryListPresenter.onElementClick((Commentary) data));

        mCollectionRecycler.setAdapter(mCommentaryListAdapter);
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
    public void setCommentaryList(List<Commentary> commentaries) {
        mCommentaryListAdapter.set(commentaries);
    }

    @Override
    public void addCommentaryList(List<Commentary> moreCommentaries) {
        mCommentaryListAdapter.add(moreCommentaries);
    }

    @Override
    public void clearCommentaryList() {
        mCommentaryListAdapter.clear();
    }

    @Override
    public void showNewsListView() {
        mViewAnimator.setDisplayedChildId(R.id.commentary_list_recycler);
    }

    @Override
    public void showLoadingView() {
        mViewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_LOADING);
    }

    @Override
    public void showErrorView() {
        mViewAnimator.setDisplayedChildId(Constant.ANIMATOR_VIEW_ERROR);
    }

    @Override
    public boolean isContentDisplayed() {
        return mViewAnimator.getDisplayedChildId() == R.id.commentary_list_recycler;
    }

    @Override
    public void showDetailScreen(Commentary commentary) {
        Log.d("showDetailScreen", "showDetailScreen");
    }

    public static void start(Activity activity, View view, Author anthor) {
        Intent intent = new Intent(activity, CommentaryListActivity.class);

        Bundle mBundle = new Bundle();
        mBundle.putSerializable(EXTRA_AUTHOR_NAME, anthor);
        intent.putExtras(mBundle);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, view, "image_shared");
        activity.startActivity(intent, options.toBundle());
    }
}
