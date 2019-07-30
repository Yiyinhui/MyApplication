package com.example.myapplication.DIY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class PromptEditText extends EditText {


    public PromptEditText(Context context) {
        super(context);
    }

    public PromptEditText(Context context, AttributeSet attrs) {

        super(context, attrs);
    }

    public PromptEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setColor(Color.BLACK);
        //绘制提示文字  运行时可看到在该控件的左侧有灰色的提示性文字，
        canvas.drawText("请输入提示文本：", 2, getHeight() / 2 +10, paint);
        super.onDraw(canvas);
    }

}

