package com.guanchazhe.news.views.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Display;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.guanchazhe.news.R;

import butterknife.ButterKnife;

/**
 * Created by ranzh on 1/15/2016.
 */
public class GUIUtils {

    public static final int DEFAULT_DELAY   = 30;
    public static final int SCALE_DELAY     = 300;

    public static ViewPropertyAnimator showViewByScale (View v) {

        ViewPropertyAnimator propertyAnimator = v.animate()
                .setStartDelay(DEFAULT_DELAY)
                .scaleX(1)
                .scaleY(1)
                .setInterpolator(new LinearInterpolator( ));

        return propertyAnimator;
    }

    public static ViewPropertyAnimator showViewByScaleY (View v, Animator.AnimatorListener animatorListener) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(SCALE_DELAY)
                .scaleY(1);

        propertyAnimator.setListener(animatorListener);

        return propertyAnimator;
    }

    public static void showRevealEffect(Context context, final View v, int centerX, int centerY,
                                        @Nullable Animator.AnimatorListener lis) {

        v.setVisibility(View.VISIBLE);

        int finalRadius = Math.max(v.getWidth(), v.getHeight());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Animator anim = ViewAnimationUtils.createCircularReveal(
                    v, centerX, centerY, 0, finalRadius);

            anim.setDuration(context.getResources().getInteger(R.integer.duration_medium));
            anim.setInterpolator(new LinearInterpolator());

            if (lis != null)
                anim.addListener(lis);

            anim.start();
        } else {

            ObjectAnimator scaleX = ObjectAnimator.ofFloat(v, "scaleX", 0.2F, 1F);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(v, "scaleY", 0.2F, 1F);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(v, "alpha", 0F, 1F);

            AnimatorSet set = new AnimatorSet();
            set.playTogether(scaleX, scaleY, alpha);
            set.setDuration(context.getResources().getInteger(R.integer.duration_medium));
            set.setInterpolator(new LinearInterpolator());

            if (lis != null)
                set.addListener(lis);

            set.start();
        }
    }

    public static void clear(View v) {
        v.setAlpha(1);
        v.setScaleY(1);
        v.setScaleX(1);
        v.setTranslationY(0);
        v.setTranslationX(0);
        v.setRotation(0);
        v.setRotationY(0);
        v.setRotationX(0);
        v.setPivotY(v.getMeasuredHeight() / 2);
        v.setPivotX(v.getMeasuredWidth() / 2);
        v.animate().setInterpolator(null);
    }
}
