package com.guanchazhe.news.views.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.guanchazhe.news.R;

/**
 * Created by ranzh on 12/24/2015.
 */
public class RecyclerInsetsDecoration extends RecyclerView.ItemDecoration {

    private int mInsets;

    public RecyclerInsetsDecoration(Context context) {
        mInsets = context.getResources().getDimensionPixelSize(R.dimen.insets);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //We can supply forced insets for each item view here in the Rect
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mInsets, mInsets, mInsets, mInsets);
    }
}
