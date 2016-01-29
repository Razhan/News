package com.guanchazhe.news.views.utils;

import android.animation.Animator;
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

    public static final int DEFAULT_DELAY           = 30;
    public static final int SCALE_DELAY             = 300;
    public static final float SCALE_START_ANCHOR    = 0.3f;

    public static void tintAndSetCompoundDrawable (Context context, @DrawableRes
    int drawableRes, int color, TextView textview) {

        Resources res = context.getResources();
        int padding = (int) res.getDimension(R.dimen.spacing_small);

        Drawable drawable = res.getDrawable(drawableRes, context.getTheme());
        drawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);

        textview.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable,
                null, null, null);

        textview.setCompoundDrawablePadding(padding);
    }

    public static ViewPropertyAnimator showViewByScale (View v) {

        ViewPropertyAnimator propertyAnimator = v.animate()
                .setStartDelay(DEFAULT_DELAY)
                .scaleX(1)
                .scaleY(1)
                .setInterpolator(new LinearInterpolator( ));

        return propertyAnimator;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void showViewByRevealEffect (View hidenView, View centerPointView, int height) {

        int cx = (centerPointView.getLeft() + centerPointView.getRight())   / 2;
        int cy = (centerPointView.getTop()  + centerPointView.getBottom())  / 2;

        Animator anim = ViewAnimationUtils.createCircularReveal(
                hidenView, cx, cy, 0, height);

        hidenView.setVisibility(View.VISIBLE);
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void makeTheStatusbarTranslucent (Activity activity) {

        Window w = activity.getWindow();
        w.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        w.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setTheStatusbarNotTranslucent(Activity activity) {

        WindowManager.LayoutParams attrs = activity.getWindow()
                .getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static int getWindowWidth (Activity activity) {

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size.y;
    }

    public static final ButterKnife.Setter<TextView, Integer> setter = new ButterKnife.Setter<TextView, Integer>() {

        @Override
        public void set(TextView view, Integer value, int index) {

            view.setTextColor(value);
        }
    };

    public static void startScaleAnimationFromPivotY (int pivotX, int pivotY, final View v,
                                                      final Animator.AnimatorListener animatorListener) {

        final AccelerateDecelerateInterpolator interpolator =
                new AccelerateDecelerateInterpolator();

        v.setScaleY(SCALE_START_ANCHOR);
        v.setPivotX(pivotX);
        v.setPivotY(pivotY);

        v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                v.getViewTreeObserver().removeOnPreDrawListener(this);

                ViewPropertyAnimator viewPropertyAnimator = v.animate()
                        .setInterpolator(interpolator)
                        .scaleY(1)
                        .setDuration(SCALE_DELAY);

                if (animatorListener != null)
                    viewPropertyAnimator.setListener(animatorListener);

                viewPropertyAnimator.start();

                return true;
            }
        });
    }


    public static void startScaleAnimationFromPivot (int pivotX, int pivotY, final View v,
                                                     final Animator.AnimatorListener animatorListener) {

        final AccelerateDecelerateInterpolator interpolator =
                new AccelerateDecelerateInterpolator();

        v.setScaleY(SCALE_START_ANCHOR);
        v.setPivotX(pivotX);
        v.setPivotY(pivotY);

        v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                v.getViewTreeObserver().removeOnPreDrawListener(this);

                ViewPropertyAnimator viewPropertyAnimator = v.animate()
                        .setInterpolator(interpolator)
                        .scaleY(1).scaleX(1)
                        .setDuration(SCALE_DELAY);

                if (animatorListener != null)
                    viewPropertyAnimator.setListener(animatorListener);

                viewPropertyAnimator.start();

                return true;
            }
        });
    }

    /**
     * Shows a view by scaling
     *
     * @param v the view to be scaled
     *
     * @return the ViewPropertyAnimation to manage the animation
     */
    public static ViewPropertyAnimator showViewByScaleY (View v, Animator.AnimatorListener animatorListener) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(SCALE_DELAY)
                .scaleY(1);

        propertyAnimator.setListener(animatorListener);

        return propertyAnimator;
    }

    public static ViewPropertyAnimator showViewByScale (View v, Animator.AnimatorListener animatorListener) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(SCALE_DELAY)
                .scaleY(1).scaleX(1);

        propertyAnimator.setListener(animatorListener);

        return propertyAnimator;
    }

    public static ViewPropertyAnimator hideViewByScaleY (View v, Animator.AnimatorListener animatorListener) {

        ViewPropertyAnimator propertyAnimator = v.animate().setStartDelay(SCALE_DELAY)
                .scaleY(0);

        propertyAnimator.setListener(animatorListener);

        return propertyAnimator;
    }

    public static void hideScaleAnimationFromPivot(View v, Animator.AnimatorListener animatorListener) {

        ViewPropertyAnimator viewPropertyAnimator = v.animate()
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .scaleY(SCALE_START_ANCHOR)
                .setDuration(SCALE_DELAY);

        if (animatorListener != null)
            viewPropertyAnimator.setListener(animatorListener);

        viewPropertyAnimator.start();

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
            return;
        }
    }

    public static Transition buildExplodeTransition (Integer... exlcudeIds) {
        Explode explodeTransition = new Explode();
        excludeTransitionIds(explodeTransition, exlcudeIds);
        return  explodeTransition;
    }

    public static Transition buildSlideTransition (int gravity, Integer... excludeIds) {
        Slide explodeTransition = new Slide();
        excludeTransitionIds(explodeTransition, excludeIds);
        return  explodeTransition;
    }

    private static void excludeTransitionIds(Transition transition, Integer[] exlcudeIds) {
        transition.excludeTarget(android.R.id.statusBarBackground, true);
        transition.excludeTarget(android.R.id.navigationBarBackground, true);

        for (Integer exlcudeId : exlcudeIds) {
            transition.excludeTarget(exlcudeId, true);
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
