package com.example.myapplication.Test_Attribute_Animation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.myapplication.R;


public class MyView3 extends View {
    private Bitmap mBitmap;
    private Paint mPaint;
    private Matrix mMatrix;
    private Float mSkew;

    public MyView3(Context context, AttributeSet attrs) {

        super(context, attrs);
        mSkew = 0f;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.loading_animation_1);
        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#FF4FFF"));
        //矩阵
        mMatrix = new Matrix();
        //mMatrix.setTranslate(200f,200f);
        //mMatrix.setSkew(0.5f,0.5f);
    }

    public Float getmSkew(){
        return mSkew;
    }

    public void setMSkew(Float skew) {
        this.mSkew = skew;
        // 将画笔的颜色设置成方法参数传入的颜色
        invalidate();
        // 调用了invalidate()方法,即画笔颜色每次改变都会刷新视图，然后调用onDraw()方法重新绘制圆
        // 而因为每次调用onDraw()方法时画笔的颜色都会改变,所以圆的颜色也会改变
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMatrix.setSkew(0, mSkew);
        Paint p = new   Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.parseColor("#FFFFFF"));

        //canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mBitmap,mMatrix,null);
        
    }

}
