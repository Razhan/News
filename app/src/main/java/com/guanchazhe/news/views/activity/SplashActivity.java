package com.guanchazhe.news.views.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.guanchazhe.news.R;
import com.guanchazhe.news.views.utils.GUIUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.splash_image)    ImageView splashImage;
    @Bind(R.id.splash_text)     TextView splashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        splashImage.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        splashImage.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        AnimatorListenerAdapter listenerAdapter = new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                                ValueAnimator inAnim = ObjectAnimator.ofFloat(splashText, "alpha", 0f, 1f);
                                inAnim.setDuration(500).setInterpolator(new LinearInterpolator());
                                inAnim.start();
                            }
                        };

                        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(SplashActivity.this, R.animator.splash_animation);
                        set.setInterpolator(new LinearInterpolator());
                        set.addListener(listenerAdapter);
                        set.setTarget(splashImage);
                        set.start();
                    }
                }
        );


    }

}
