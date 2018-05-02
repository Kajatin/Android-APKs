package com.example.rkajatin.animationtest;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ExampleFragment extends Fragment {

    private Button bt;
    private TextView tv1;
    private TextView tv2;
    private View bg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bt = (Button) getView().findViewById(R.id.button);
        tv1 = (TextView) getView().findViewById(R.id.textView2);
        tv2 = (TextView) getView().findViewById(R.id.textView3);
        bg = (View) view.findViewById(R.id.view2);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final ValueAnimator anim = ValueAnimator.ofInt(bg.getMeasuredHeight(),bg.getMeasuredHeight()+250);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int val = (Integer) animation.getAnimatedValue();
                        ViewGroup.LayoutParams lp = bg.getLayoutParams();
                        lp.height = val;
                        bg.setLayoutParams(lp);
                    }
                });

                anim.setDuration(380);
                anim.start();
                */

                //bg.animate().scaleYBy(1.5f).setDuration(380).setInterpolator(new AccelerateDecelerateInterpolator());


                startActivity(new Intent(v.getContext(), DetailView.class));



                tv1.setVisibility(View.VISIBLE);
                tv2.setVisibility(View.VISIBLE);
                tv1.setText("Hello there");
                tv2.setText("Hello there");

                ValueAnimator b = ObjectAnimator.ofFloat(bg, "scaleY", 3f);
                b.setDuration(380);
                ValueAnimator v1 = ObjectAnimator.ofFloat(tv1,"translationX",35f);
                v1.setDuration(200);
                ValueAnimator v2 = ObjectAnimator.ofFloat(tv1, "alpha", 0f, 1f);
                v2.setDuration(200);
                ValueAnimator vv1 = ObjectAnimator.ofFloat(tv2,"translationX",45f);
                vv1.setDuration(280);
                ValueAnimator vv2 = ObjectAnimator.ofFloat(tv2, "alpha", 0f, 1f);
                vv2.setDuration(280);

                AnimatorSet set = new AnimatorSet();
                set.play(b).before(v1);
                set.play(v1).with(v2).with(vv1).with(vv2);
                set.start();
            }
        });
    }
}
