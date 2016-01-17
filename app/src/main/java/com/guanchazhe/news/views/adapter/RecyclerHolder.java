package com.guanchazhe.news.views.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ranzh on 1/7/2016.
 */
public class RecyclerHolder extends RecyclerView.ViewHolder implements
        ItemTouchHelperViewHolder {
    private final SparseArray<View> mViews;
    private Context mContext;
    private final int mHoldType;

    public RecyclerHolder(View itemView, Context context, int type) {
        super(itemView);
        mContext = context;
        mHoldType = type;
        this.mViews = new SparseArray<>(8);
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.RED);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(Color.TRANSPARENT);
    }

    public SparseArray<View> getAllView() {
        return mViews;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public RecyclerHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public RecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    public RecyclerHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    public void setVisility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
    }

    public Context getmContext() {
        return mContext;
    }

    public int getHoldType() {
        return mHoldType;
    }
}
