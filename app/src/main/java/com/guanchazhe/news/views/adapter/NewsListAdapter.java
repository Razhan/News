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

    public NewsListAdapter(RecyclerView v, Collection<News> datas, OnItemClickListener listener) {
        super(v, datas, R.layout.item_news);
        setOnItemClickListener(listener);
    }

    @Override
    public void convert(RecyclerHolder holder, News item, int position, boolean isScrolling) {

        holder.setText(R.id.newsTitle, item.getTitle());
        holder.setText(R.id.description, item.getSummary());

        if (item.getPic() != null) {
            Glide.with(holder.getmContext())
                    .load(item.getPic())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.error_placeholder)
                    .into((ImageView)holder.getView(R.id.news_image));

        } else {
            holder.getView(R.id.profile_image).setVisibility(View.GONE);
        }
    }


}
