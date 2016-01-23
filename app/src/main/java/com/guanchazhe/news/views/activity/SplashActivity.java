package com.guanchazhe.news.views.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.guanchazhe.news.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.splash_image)    ImageView splashImageIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        splashImageIV.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        splashImageIV.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        startImageAnim(toMainActivity());
                    }
                }
        );
    }

    private void startImageAnim(AnimatorListenerAdapter listenerAdapter) {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(SplashActivity.this, R.animator.splash_animation);
        set.setInterpolator(new LinearInterpolator());
        set.addListener(listenerAdapter);
        set.setTarget(splashImageIV);
        set.start();
    }

    private AnimatorListenerAdapter toMainActivity() {

        AnimatorListenerAdapter listenerAdapter = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        };
        return listenerAdapter;
    }


}
