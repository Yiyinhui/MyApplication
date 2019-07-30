package com.example.myapplication.DIY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;



@SuppressLint("AppCompatCustomView")
public class UnderlineEditText extends TextView {
    private static final String TAG = "UnderlineEditText";
    private Paint mPaint;
    private Paint mPaint2;
    private Paint mPaint3;
    private Paint mPaint4;
    private Rect mRect;
    private float mult = 1.5f;
    private float add = 2.0f;

    public UnderlineEditText(Context context) {
        super(context);
    }

    public UnderlineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getResources().getColor(R.color.gray));
        mPaint.setStrokeWidth(6.5f);
        mPaint.setAntiAlias(true);

//        mPaint2 = new Paint();
//        mPaint2.setStyle(Paint.Style.STROKE);
//        mPaint2.setColor(getResources().getColor(R.color.Black));
//        mPaint2.setTextSize(50);
//        mPaint2.setAntiAlias(true);

        mPaint3 = new Paint();
        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setColor(getResources().getColor(R.color.gray));
        mPaint3.setStrokeWidth(2.5f);
        mPaint3.setAntiAlias(true);
        this.setLineSpacing(add, mult);

        mPaint4 = new Paint();
        mPaint4.setStyle(Paint.Style.STROKE);
        mPaint4.setColor(getResources().getColor(R.color.gray));
        mPaint4.setTextSize(50);
        mPaint4.setAntiAlias(true);
    }

    public UnderlineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(TAG, "func [onDraw]");
        int count = getLineCount();
        for (int i = 0; i < count; i++) {
            getLineBounds(i, mRect);
            int baseline = (i + 1) * getHeight();
            int baselineT = getLayoutParams().height*(i +1);
            int upperline = i * getHeight();
            //下划线
            canvas.drawLine(mRect.left, baseline, mRect.right, baseline, mPaint);
            //分割线
            canvas.drawLine((mRect.left+mRect.right)/2, upperline+20, (mRect.left+mRect.right)/2, baseline-20, mPaint3);


        }
        super.onDraw(canvas);
    }
}

