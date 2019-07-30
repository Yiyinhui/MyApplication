package com.example.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.Test_Attribute_Animation.MyView3;
import com.example.myapplication.Test_Attribute_Animation.SqureEvaluator;

public class TestActivity2 extends Activity {
    private LinearLayout mPoint_1;
    private LinearLayout mPoint_2;
    private LinearLayout mPoint_3;
    private LinearLayout mPoint_4;
    MyView3 loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        mPoint_1 = (LinearLayout) findViewById(R.id.ll_point_circle_1);
        mPoint_2 = (LinearLayout) findViewById(R.id.ll_point_circle_2);
        mPoint_3 = (LinearLayout) findViewById(R.id.ll_point_circle_3);
        mPoint_4 = (LinearLayout) findViewById(R.id.ll_point_circle_4);



        Button startAni = (Button) findViewById(R.id.start_ani_2);
        startAni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beginPropertyAni();
            }
        });
    }

    /**
     * 开启动画
     */
    private void beginPropertyAni() {
//        //mPoint_1.setRotationX(50);
//        mPoint_1.setPivotX(mPoint_1.getWidth()/2);
//        mPoint_1.setPivotY(mPoint_1.getHeight()/2);
//        mPoint_1.setRotationX(20);
//        ObjectAnimator animator_1 = ObjectAnimator.ofFloat(
//                mPoint_1,
//                "rotation",
//                0,
//                360);
//        animator_1.setDuration(2000);
//        animator_1.setInterpolator(new AccelerateDecelerateInterpolator());

//        Animation animation= AnimationUtils.loadAnimation(TestActivity2.this,R.anim.rotate_x);
//        mPoint_1.startAnimation(animation);//开始动画
//        animation.setAnimationListener(new Animation.AnimationListener(){
//            @Override
//            public void onAnimationStart(Animation animation) {}
//            @Override
//            public void onAnimationRepeat(Animation animation) {}
//            @Override
//            public void onAnimationEnd(Animation animation) {//动画结束
//                //mPoint_1.setVisibility(View.GONE);
//            }
//        });



        loading = findViewById(R.id.loading1);
        ObjectAnimator anim = ObjectAnimator.ofObject(loading, "mSkew", new SqureEvaluator(),
                0f,0.5f ,0.1f,-1f);

        anim.setDuration(5000);
        anim.start();










        ObjectAnimator animator_2 = ObjectAnimator.ofFloat(
                mPoint_2,
                "rotation",
                0,
                360);
        animator_2.setStartDelay(150);
        animator_2.setDuration(2000 + 150);
        animator_2.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator animator_3 = ObjectAnimator.ofFloat(
                mPoint_3,
                "rotation",
                0,
                360);
        animator_3.setStartDelay(2 * 150);
        animator_3.setDuration(2000 + 2 * 150);
        animator_3.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator animator_4 = ObjectAnimator.ofFloat(
                mPoint_4,
                "rotation",
                0,
                360);
        animator_4.setStartDelay(3 * 150);
        animator_4.setDuration(2000 + 3 * 150);
        animator_4.setInterpolator(new AccelerateDecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator_2).with(animator_3).with(animator_4);
        animatorSet.start();
    }
}
