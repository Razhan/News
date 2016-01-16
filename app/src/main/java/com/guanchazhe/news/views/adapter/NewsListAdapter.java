package com.guanchazhe.news.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.model.entities.News;

import java.util.Collection;

/**
 * Created by ranzh on 1/13/2016.
 */
public class NewsListAdapter extends BaseRecyclerAdapter<News> {

    public NewsListAdapter(RecyclerView v, Collection<News> data, boolean header, OnItemClickListener listener) {
        super(v, data, R.layout.item_news, R.layout.item_news_header, header);
        setOnItemClickListener(listener);
    }

    @Override
    public void headerConvert(RecyclerHolder holder, News item, int position, boolean isScrolling) {

        holder.setText(R.id.header_title, item.getTitle());
        holder.getView(R.id.header_title).getBackground().setAlpha(100);
        Glide.with(holder.getmContext())
                .load(item.getPic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.default_avatar)
                .into((ImageView)holder.getView(R.id.header_image));
    }

    @Override
    public void itemConvert(RecyclerHolder holder, News item, int position, boolean isScrolling) {

        holder.setText(R.id.news_title, item.getTitle());
        holder.setText(R.id.news_summary, item.getSummary());

        Glide.with(holder.getmContext())
                .load(item.getPic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.default_avatar)
                .into((ImageView) holder.getView(R.id.news_image));

    }

}
