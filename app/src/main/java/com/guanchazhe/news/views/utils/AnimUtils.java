package com.guanchazhe.news.views.utils;

import android.animation.Animator;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by ranzh on 1/4/2016.
 */
public class AnimUtils {

    public static final int REVEAL_DURATION = 5000;

    public static void showRevealEffect(final View v, int centerX, int centerY,
                                        @Nullable Animator.AnimatorListener lis) {

        v.setVisibility(View.VISIBLE);

        int finalRadius = Math.max(v.getWidth(), v.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(
                v, centerX, centerY, 0, finalRadius);

        anim.setDuration(REVEAL_DURATION);

        if (lis != null)
            anim.addListener(lis);

        anim.start();
    }
}
