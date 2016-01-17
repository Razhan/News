package com.guanchazhe.news.views.adapter;

import android.support.v7.widget.RecyclerView;

import com.guanchazhe.news.R;

import java.util.Collection;

/**
 * Created by ran.zhang on 1/16/16.
 */
public class ChannelListAdapter extends BaseRecyclerAdapter<String>{

    public ChannelListAdapter(RecyclerView v, Collection<String> data, boolean header, OnItemClickListener listener) {
        super(v, data, R.layout.item_channel, 0, header, null);
        setOnItemClickListener(listener);
    }

    @Override
    public void headerConvert(RecyclerHolder holder, String item, int position, boolean isScrolling) {
        return;
    }

    @Override
    public void itemConvert(RecyclerHolder holder, String item, int position, boolean isScrolling) {

        holder.setText(R.id.column_name, item);
    }


}