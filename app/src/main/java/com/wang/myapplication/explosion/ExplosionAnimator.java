package com.wang.myapplication.explosion;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class ExplosionAnimator extends ValueAnimator {

    public static int default_duration = 5000;
    //粒子工厂
    private ParticalFactory particalFactory;
    //所有粒子
    private Partical[] particals;
    //动画场地
    private View container;
    //画笔
    private Paint paint;

    public ExplosionAnimator(View container, ParticalFactory particalFactory, Bitmap bitmap, Rect bound) {
        this.particalFactory = particalFactory;
        this.particals = particalFactory.generateParticales(bitmap, bound);
        this.container = container;
        this.paint = new Paint();
        setFloatValues(0, 1);
        setDuration(default_duration);
    }

    //draw
    public void draw(Canvas canvas){
        if(!isStarted()){
            return;
        }
        //开始所有的动画
        for (Partical partical : particals) {
            partical.advance(canvas, paint, (Float) getAnimatedValue());
        }
        container.invalidate();
    }

    @Override
    public void start() {
        super.start();
        container.invalidate();
    }
}
