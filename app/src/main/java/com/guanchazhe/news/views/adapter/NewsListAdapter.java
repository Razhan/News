package com.guanchazhe.news.views.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.model.entities.News;
import com.guanchazhe.news.utils.Utils;
import com.guanchazhe.news.views.widget.RecyclerClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * Created by ranzh on 12/24/2015.
 */
public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {
    private final String NOT_AVAILABLE_URL = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg";
    private final RecyclerClickListener mRecyclerListener;
    private final List<News> mNews;

    private Context mContext;

    public NewsListAdapter(List<News> news, Context context, RecyclerClickListener recyclerClickListener) {
        mNews = news;
        mContext = context;
        mRecyclerListener = recyclerClickListener;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(
                R.layout.item_news, parent, false);

        return new NewsViewHolder(rootView, mRecyclerListener);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bindNews(mNews.get(position));
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.newsTitle)               TextView avengerTitleTextView;
        @Bind(R.id.news_image)              ImageView avengerThumbImageView;
        @Bind(R.id.description)             TextView avengerPlaceholderTitleTextView;
        @BindColor(R.color.colorPrimary)    int mColorPrimary;

        public NewsViewHolder(View itemView, final RecyclerClickListener recyclerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        public void bindNews(News news) {
            avengerTitleTextView.setText(news.getTitle());
            avengerTitleTextView.setTransitionName(Utils.getListTransitionName(getAdapterPosition()));
            avengerPlaceholderTitleTextView.setText(news.getSummary());

            if (news.getPic().equals(NOT_AVAILABLE_URL)) {
                ColorDrawable colorDrawable = new ColorDrawable(mColorPrimary);
                avengerThumbImageView.setDrawingCacheEnabled(true);
                avengerThumbImageView.setImageDrawable(colorDrawable);

            } else {
                Glide.with(mContext)
                        .load(news.getPic())
                        .crossFade()
                        .into(avengerThumbImageView);
            }
        }

        private void bindListener(View itemView, final RecyclerClickListener recyclerClickListener) {
            itemView.setOnClickListener(v ->
                    recyclerClickListener.onElementClick(getAdapterPosition(), avengerTitleTextView, avengerThumbImageView));
        }
    }
}
