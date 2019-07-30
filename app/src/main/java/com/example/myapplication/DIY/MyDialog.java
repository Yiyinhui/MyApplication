package com.example.myapplication.DIY;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.myapplication.R;

public class MyDialog extends Dialog {
    private ImageView mStartImage;
    private AnimationDrawable mAnimationDrawable;

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        //空白处不能取消动画
        setCanceledOnTouchOutside(true);

        //初始化界面控件
        initView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {

        mStartImage = findViewById(R.id.startImage);
        mAnimationDrawable = (AnimationDrawable) getContext().getResources().getDrawable(R.drawable.waiting_animation);
        mStartImage.setImageDrawable(mAnimationDrawable);
        mAnimationDrawable.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
