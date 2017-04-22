package com.seven.circularprogressbar;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;

import com.seven.circularprogressbar.view.CircularProgressBar;

public class MainActivity extends AppCompatActivity {
    private boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CircularProgressBar prg = (CircularProgressBar)findViewById(R.id.prg);

        prg.setProgressBackgroundColor(Color.GRAY);
        prg.setProgressColor(Color.RED);

        final ObjectAnimator animator = ObjectAnimator.ofFloat(prg,"progress",0.0f,1.0f);
        animator.setDuration(2000);
        animator.setRepeatCount(-1);
        animator.setInterpolator(new LinearInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (flag) {
                    prg.setProgressColor(Color.GRAY);
                    prg.setProgressBackgroundColor(Color.RED);
                } else {
                    prg.setProgressColor(Color.RED);
                    prg.setProgressBackgroundColor(Color.GRAY);
                }
                flag = !flag;
            }
        });
        animator.start();
    }
}
