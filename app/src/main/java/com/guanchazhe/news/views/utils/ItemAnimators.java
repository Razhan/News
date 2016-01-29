package com.guanchazhe.news.views.utils;

import android.animation.ObjectAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ranzh on 1/29/2016.
 */
public class ItemAnimators {

    private ItemAnimators(){}

    public static List<ObjectAnimator> getAlphaInAnimation(View view, float from) {
        List<ObjectAnimator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(view, "alpha", from, 1f));
        return animators;
    }

    public static List<ObjectAnimator> getScaleInAnimation(View view, float from) {
        List<ObjectAnimator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(view, "scaleX", from, 1f));
        animators.add(ObjectAnimator.ofFloat(view, "scaleY", from, 1f));

        return animators;
    }

    public static List<ObjectAnimator> getSlideInBottomAnimation(View view) {
        List<ObjectAnimator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight(), 0));
        return animators;
    }

    public static List<ObjectAnimator> getSlideInLeftAnimation(View view) {
        List<ObjectAnimator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(view, "translationX", -view.getRootView().getWidth(), 0)
        );
        return animators;
    }

    public static List<ObjectAnimator> getSlideInRightAnimation(View view) {
        List<ObjectAnimator> animators = new ArrayList<>();
        animators.add(ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0)
        );
        return animators;
    }
}
