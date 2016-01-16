package com.guanchazhe.news.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.guanchazhe.news.R;
import com.guanchazhe.news.mvp.model.entities.Author;

import java.util.Collection;

/**
 * Created by ran.zhang on 1/16/16.
 */
public class ColumnListAdapter extends BaseRecyclerAdapter<String>{

    public ColumnListAdapter(RecyclerView v, Collection<String> data, boolean header, OnItemClickListener listener) {
        super(v, data, R.layout.item_column, 0, header);
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