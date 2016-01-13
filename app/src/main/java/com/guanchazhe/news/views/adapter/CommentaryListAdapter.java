package com.guanchazhe.news.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.model.entities.Commentary;

import java.util.Collection;

/**
 * Created by ranzh on 1/8/2016.
 */
public class CommentaryListAdapter extends BaseRecyclerAdapter<Commentary> {

    public CommentaryListAdapter(RecyclerView v, Collection<Commentary> datas, OnItemClickListener listener) {
        super(v, datas, R.layout.item_commentary);
        setOnItemClickListener(listener);
    }

    @Override
    public void convert(RecyclerHolder holder, Commentary item, int position, boolean isScrolling) {

        holder.setText(R.id.newsTitle, item.getTitle());
        holder.setText(R.id.author, item.getAuthor());
        holder.setText(R.id.newsTime, item.getCreationtime());
        holder.setText(R.id.description, item.getSummary());

        if (item.getPic() != null) {
            Glide.with(holder.getmContext())
                    .load(item.getPic())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.error_placeholder)
                    .into((ImageView)holder.getView(R.id.profile_image));

        } else {
            holder.setImageResource(R.id.profile_image, R.drawable.default_avatar);
        }
    }

}