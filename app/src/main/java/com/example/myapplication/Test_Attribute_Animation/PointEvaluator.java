package com.example.myapplication.Test_Attribute_Animation;

import android.animation.TypeEvaluator;

public class PointEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        // 将动画初始值startValue 和 动画结束值endValue 强制类型转换成Point对象
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;

        // 根据fraction来计算当前动画的x和y的值
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());

        // 将计算后的坐标封装到一个新的Point对象中并返回
        Point point = new Point(x, y);
        return point;
    }
}
