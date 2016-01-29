package com.guanchazhe.news.views.adapter;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guanchazhe.news.R;
import com.guanchazhe.news.views.listener.RecycleViewAdapterListener;

import java.util.Collection;
import java.util.List;

/**
 * Created by ran.zhang on 1/16/16.
 */
public class ChannelListAdapter extends BaseRecyclerAdapter<String>{

    private RecycleViewAdapterListener mListener;

    public ChannelListAdapter(RecyclerView v, Collection<String> data, boolean header, OnItemClickListener listener) {
        super(v, data, R.layout.item_channel, 0, header, null);
        setOnItemClickListener(listener);
    }

    public void setListener(RecycleViewAdapterListener listener) {
        mListener = listener;
    }

    @Override
    public void headerConvert(RecyclerHolder holder, String item, int position, boolean isScrolling) {
        return;
    }

    @Override
    public void itemConvert(RecyclerHolder holder, String item, int position, boolean isScrolling) {
        holder.setText(R.id.column_name, item);
    }

    @Override
    public void onItemDismiss(int position) {
        mListener.afterItemRemoved(mData.get(position));
        super.onItemDismiss(position);
    }

    @Override
    protected List<Animator> getAnimators(View view) {
        return null;
    }
}