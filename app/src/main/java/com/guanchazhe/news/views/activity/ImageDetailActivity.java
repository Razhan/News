package com.guanchazhe.news.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.guanchazhe.news.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageDetailActivity extends BaseActivity {

    private final String EXTRA_URL_NAME = "url";

    @Bind(R.id.toolbar)         Toolbar toolBar;
    @Bind(R.id.image_detail)    ImageView imageDetailIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadImage();
    }

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_image_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolbar() {
        toolBar.setTitle(R.string.image_detail);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void loadImage() {

        String url = getIntent().getStringExtra(EXTRA_URL_NAME);

        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.def_placeholder)
                .into(imageDetailIV);
    }
}
