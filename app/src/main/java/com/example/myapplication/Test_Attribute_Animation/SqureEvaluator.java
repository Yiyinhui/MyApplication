package com.example.myapplication.Test_Attribute_Animation;

import android.animation.TypeEvaluator;
import android.graphics.Bitmap;
import android.graphics.Matrix;

public class SqureEvaluator implements TypeEvaluator {
    private String mCurrentSkew;

    private Bitmap mbitmap;

    @Override
    public Object evaluate(float fraction, Object start, Object end) {

        Float s = (Float) start;
        Float e = (Float) end;

        return s+fraction*(e-s);
    }
}
