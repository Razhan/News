package com.guanchazhe.news.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.model.entities.Author;

import java.util.Collection;

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

        Glide.with(holder.getmContext())
                .load(item.getPic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.def_placeholder)
                .into((ImageView) holder.getView(R.id.author_image));

    }


}
