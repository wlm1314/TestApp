package com.wang.myapplication.explosion;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Partical {

    //位置
    float cx, cy;
    int color;

    public Partical(float cx, float cy, int color) {
        this.cx = cx;
        this.cy = cy;
        this.color = color;
    }

    //计算
    protected abstract void calculate(float factor);

    //绘制
    protected abstract void draw(Canvas canvas, Paint paint);

    protected void advance(Canvas canvas, Paint paint, float factor){
        calculate(factor);
        draw(canvas, paint);
    }
}
