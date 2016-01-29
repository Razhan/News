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
 * Created by ran.zhang on 1/16/16.
 */
public class CommentaryListAdapter extends BaseRecyclerAdapter<News> {

    public CommentaryListAdapter(RecyclerView v, Collection<News> data, boolean header, OnItemClickListener listener) {
        super(v, data, R.layout.item_commentary, 0, header, null);
        setOnItemClickListener(listener);
    }

    @Override
    public void headerConvert(RecyclerHolder holder, News item, int position, boolean isScrolling) {
        return;
    }

    @Override
    public void itemConvert(RecyclerHolder holder, News item, int position, boolean isScrolling) {

        holder.setText(R.id.commentary_title, item.getTitle());
        holder.setText(R.id.commentary_summary, item.getSummary());
        holder.setText(R.id.commentary_author, item.getAuthor());
        holder.setText(R.id.commentary_createtime, item.getCreationtime());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (prefs.getBoolean("noPicMode", false)) {
            return;
        }

        Glide.with(holder.getmContext())
                .load(item.getPic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.def_avatar)
                .into((ImageView)holder.getView(R.id.commemtary_author_image));
    }

    @Override
    protected List<Animator> getAnimators(View view) {
        List<Animator> animators = new ArrayList<>();
        animators.addAll(ItemAnimators.getScaleInAnimation(view, 0.5f));
        animators.addAll(ItemAnimators.getAlphaInAnimation(view, 0f));

        return animators;
    }

}