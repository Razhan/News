package com.guanchazhe.news.views.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.guanchazhe.news.views.listener.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by ranzh on 1/8/2016.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerHolder> implements ItemTouchHelperAdapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    protected List<T> mData;
    protected final int mItemLayoutId;
    protected final int mHeaderLayoutId;
    protected final boolean mWithHeader;

    protected boolean isScrolling;
    protected Context mContext;
    private OnItemClickListener listener;
    private final OnStartDragListener mDragStartListener;


    public interface OnItemClickListener {
        void onItemClick(View view, Object data, int position);
    }


    //创建者模式
    public BaseRecyclerAdapter(RecyclerView v, Collection<T> data, int itemLayoutId,
                               int headerLayoutId, boolean withHeader,
                               OnStartDragListener startDragListener) {

            if (data == null) {
                mData = new ArrayList<>();
            } else if (data instanceof List) {
                mData = (List<T>) data;
            } else {
                mData = new ArrayList<>(data);
            }
        mItemLayoutId = itemLayoutId;
        mHeaderLayoutId = headerLayoutId;
        mWithHeader = withHeader;

        mContext = v.getContext();

        mDragStartListener = startDragListener;
    }

    public abstract void headerConvert(RecyclerHolder holder, T item, int position, boolean isScrolling);

    public abstract void itemConvert(RecyclerHolder holder, T item, int position, boolean isScrolling);

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View root;

        if(viewType == TYPE_HEADER) {
            root = inflater.inflate(mHeaderLayoutId, parent, false);
        } else if(viewType == TYPE_ITEM) {
            root = inflater.inflate(mItemLayoutId, parent, false);
        } else {
            throw new RuntimeException("there is no type that matches the type " + viewType);
        }

        return new RecyclerHolder(root, mContext, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {

        if (holder.getHoldType() == TYPE_HEADER) {
            headerConvert(holder, mData.get(position), position, isScrolling);
        } else {
            itemConvert(holder, mData.get(position), position, isScrolling);
        }

        holder.itemView.setOnClickListener(getOnClickListener(position));

        // Start a drag whenever the handle view it touched
        holder.itemView.setOnTouchListener((v, event) -> {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN
                        && mDragStartListener != null) {

                        mDragStartListener.onStartDrag(holder);
                    }
                return false;
            });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    private boolean isPositionHeader(int position) {
        if (mWithHeader && position == 0) {
            return true;
        }
        return false;
    }


    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return v -> {
            if (listener != null && v != null) {
                    listener.onItemClick(v, mData.get(position), position);
                }
        };
    }

    public BaseRecyclerAdapter<T> refresh(Collection<T> datas) {
        if (datas == null) {
            mData = new ArrayList<>();
        } else if (datas instanceof List) {
            mData = (List<T>) datas;
        } else {
            mData = new ArrayList<>(datas);
        }
        return this;
    }

    public void set(@NonNull List<T> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void add(@NonNull List<T> moreNews) {
        if (!moreNews.isEmpty()) {
            int currentSize = mData.size();
            int amountInserted = moreNews.size();

            mData.addAll(moreNews);
            notifyItemRangeInserted(currentSize, amountInserted);
        }
    }

    public void clear() {
        if (!mData.isEmpty()) {
            mData.clear();
            notifyDataSetChanged();
        }
    }
}