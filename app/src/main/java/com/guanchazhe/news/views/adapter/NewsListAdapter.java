package com.guanchazhe.news.views.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.views.utils.ItemAnimators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by ranzh on 1/13/2016.
 */
public class NewsListAdapter extends BaseRecyclerAdapter<News> {

    public NewsListAdapter(RecyclerView v, Collection<News> data, boolean header, OnItemClickListener listener) {
        super(v, data, R.layout.item_news, R.layout.item_news_header, header, null);
        setOnItemClickListener(listener);
    }

    @Override
    public void headerConvert(RecyclerHolder holder, News item, int position, boolean isScrolling) {

        holder.setText(R.id.header_title, item.getTitle());
        holder.getView(R.id.header_title).getBackground().setAlpha(100);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (prefs.getBoolean("noPicMode", false)) {
            return;
        }

        Glide.with(holder.getmContext())
                .load(item.getPic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.def_placeholder)
                .into((ImageView) holder.getView(R.id.header_image));
    }

    @Override
    public void itemConvert(RecyclerHolder holder, News item, int position, boolean isScrolling) {

        holder.setText(R.id.news_title, item.getTitle());
        holder.setText(R.id.news_summary, item.getSummary());

        holder.setVisility(R.id.news_image, View.GONE);
        holder.setVisility(R.id.news_image_author, View.GONE);

        if (containID(clickedItems, item.getId())) {
            holder.setVisility(R.id.news_status_indicator, View.INVISIBLE);
        } else {
            holder.setVisility(R.id.news_status_indicator, View.VISIBLE);
        }

        ImageView placeHold;

        if (item.getPic().contains("ColumnPic") || item.getPic().contains("authors")) {
            holder.setVisility(R.id.news_image_author, View.VISIBLE);
            placeHold = holder.getView(R.id.news_image_author);
        } else {
            holder.setVisility(R.id.news_image, View.VISIBLE);
            placeHold = holder.getView(R.id.news_image);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (prefs.getBoolean("noPicMode", false)) {
            return;
        }

        Glide.with(holder.getmContext())
                .load(item.getPic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.def_placeholder)
                .into(placeHold);
    }

    @Override
    protected List<Animator> getAnimators(View view) {
        List<Animator> animators = new ArrayList<>();
        animators.addAll(ItemAnimators.getScaleInAnimation(view, 0.5f));
        animators.addAll(ItemAnimators.getAlphaInAnimation(view, 0f));

        return animators;
    }

    private boolean containID(List<News> newsList, int id) {
        if (newsList == null || newsList.size() < 0) {
            return false;
        }

        for (News news : newsList) {
            if (news.getId() == id) {
                return true;
            }
        }

        return false;
    }


}
