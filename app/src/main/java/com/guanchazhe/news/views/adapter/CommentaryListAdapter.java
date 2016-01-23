package com.guanchazhe.news.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.model.entities.News;

import java.util.Collection;

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

        Glide.with(holder.getmContext())
                .load(item.getPic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.def_avatar)
                .into((ImageView)holder.getView(R.id.commemtary_author_image));
    }

}