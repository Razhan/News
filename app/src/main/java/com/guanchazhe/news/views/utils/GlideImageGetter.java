package com.guanchazhe.news.views.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.guanchazhe.news.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ranzh on 1/6/2016.
 */
public final class GlideImageGetter implements Html.ImageGetter, Drawable.Callback {

    private final Context mContext;

    private final TextView mTextView;

    private final Set<ImageGetterViewTarget> mTargets;

    public GlideImageGetter(TextView textView) {
        this.mContext = textView.getContext();
        this.mTextView = textView;


        clear(); // Cancel all previous request
        mTargets = new HashSet<>();
        mTextView.setTag(R.integer.drawable_callback_tag, this);
    }

    public static GlideImageGetter get(View view) {
        return (GlideImageGetter)view.getTag(R.integer.drawable_callback_tag);
    }

    public void clear() {
        GlideImageGetter prev = get(mTextView);
        if (prev == null) {
            return;
        }

        for (ImageGetterViewTarget target : prev.mTargets) {
            System.out.println("Cleared!");
            Glide.clear(target);
        }
    }
    @Override
    public Drawable getDrawable(String url) {
        final UrlDrawable urlDrawable = new UrlDrawable();

        System.out.println("Downloading from: " + url);
        Glide.with(mContext)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new ImageGetterViewTarget(mTextView, urlDrawable));

        return urlDrawable;
    }

    @Override
    public void invalidateDrawable(Drawable who) {
        mTextView.invalidate();
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {

    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {

    }

    private class ImageGetterViewTarget extends ViewTarget<TextView, GlideDrawable> {

        private final UrlDrawable mDrawable;
        private Request request;

        private ImageGetterViewTarget(TextView view, UrlDrawable drawable) {
            super(view);
            mTargets.add(this); // Add ViewTarget into Set
            this.mDrawable = drawable;
        }

        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            // Resize images - Scale image proportionally to fit TextView width
            float width;
            float height;
            if (resource.getIntrinsicWidth() >= getView().getWidth()) {
                float downScale = (float) resource.getIntrinsicWidth() / getView().getWidth();
                width = (float) resource.getIntrinsicWidth() / (float) downScale;
                height = (float) resource.getIntrinsicHeight() / (float) downScale;
            } else {
                float multiplier = (float) getView().getWidth() / resource.getIntrinsicWidth();
                width = (float) resource.getIntrinsicWidth() * (float) multiplier;
                height = (float) resource.getIntrinsicHeight() * (float) multiplier;
            }
            Rect rect = new Rect(0, 0, Math.round(width), Math.round(height));

            resource.setBounds(rect);

            mDrawable.setBounds(rect);
            mDrawable.setDrawable(resource);

            if (resource.isAnimated()) {
                // set callback to drawable in order to
                // signal its container to be redrawn
                // to show the animated GIF

                mDrawable.setCallback(get(getView()));
                resource.setLoopCount(GlideDrawable.LOOP_FOREVER);
                resource.start();
            }

            getView().setText(getView().getText());
            getView().invalidate();
        }

        @Override
        public Request getRequest() {
            return request;
        }

        @Override
        public void setRequest(Request request) {
            this.request = request;
        }
    }
}
