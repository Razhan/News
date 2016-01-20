package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.model.entities.Author;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDetailActivity extends AppCompatActivity {

    private final String EXTRA_URL_NAME = "url";

    @Bind(R.id.toolbar)         Toolbar toolbar;
    @Bind(R.id.image_detail)    ImageView imageDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        initToolbar();

        loadImage();
    }

    private void initUI() {
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
    }

    private void initToolbar() {
        toolbar.setTitle("图片");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void loadImage() {

        String url = getIntent().getStringExtra(EXTRA_URL_NAME);

        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.error_placeholder)
                .into(imageDetail);
    }
}
