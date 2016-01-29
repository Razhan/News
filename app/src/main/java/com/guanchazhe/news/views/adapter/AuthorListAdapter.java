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
import com.guanchazhe.news.mvp.model.entities.Author;
import com.guanchazhe.news.views.utils.ItemAnimators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by ran.zhang on 1/13/16.
 */
public class AuthorListAdapter extends BaseRecyclerAdapter<Author>{

    public AuthorListAdapter(RecyclerView v, Collection<Author> data, boolean header, OnItemClickListener listener) {
        super(v, data, R.layout.item_author, 0, header, null);
        setOnItemClickListener(listener);
    }

    @Override
    public void headerConvert(RecyclerHolder holder, Author item, int position, boolean isScrolling) {
        return;
    }

    @Override
    public void itemConvert(RecyclerHolder holder, Author item, int position, boolean isScrolling) {

        holder.setText(R.id.author_name, item.getName());
        holder.setText(R.id.author_title, item.getTitle());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (prefs.getBoolean("noPicMode", false)) {
            return;
        }

        Glide.with(holder.getmContext())
                .load(item.getPic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.def_placeholder)
                .into((ImageView) holder.getView(R.id.author_image));

    }

    @Override
    protected List<Animator> getAnimators(View view) {
        List<Animator> animators = new ArrayList<>();
        animators.addAll(ItemAnimators.getSlideInRightAnimation(view));

        return animators;
    }


}
