package com.example.rkajatin.animationtest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v = (View) findViewById(R.id.view);

        final GestureDetector.OnGestureListener l = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Toast.makeText(MainActivity.this, "T", Toast.LENGTH_LONG).show();
                FlingAnimation fling = new FlingAnimation(v, DynamicAnimation.TRANSLATION_Y);
                fling.setStartVelocity(velocityY).setFriction(0.8f).start();
                return true;
            }
        };

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l.onFling(null,null,100f,300f);
            }
        });

        ValueAnimator va = ValueAnimator.ofFloat(20f, 80f);
        va.setDuration(1000);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                v.setTranslationX(val);
            }
        });

        ValueAnimator va2 = ObjectAnimator.ofFloat(v, "translationY", 250f);
        va2.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.play(va).before(va2);
        set.start();

        v.animate().rotation(50f).setDuration(1500).setStartDelay(2500);


        ViewGroup vg = (ViewGroup) findViewById(R.id.constr);
        vg.animate().translationX(200f).setDuration(500).start();
    }
}
