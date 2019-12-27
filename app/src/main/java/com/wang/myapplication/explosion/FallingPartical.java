package com.wang.myapplication.explosion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class FallingPartical extends Partical {
    float radius = FallingParticalFactory.PART_WH;
    float alpha = 1.0f;
    Rect mBound;

    public FallingPartical(float cx, float cy, int color, Rect bound) {
        super(cx, cy, color);
        mBound = bound;
    }


    @Override
    protected void calculate(float factor) {
        cx = cx + factor * ExplosionUtils.RANDOM.nextInt(mBound.width()) * (ExplosionUtils.RANDOM.nextFloat() - 0.5f);
        cy = cy + factor * ExplosionUtils.RANDOM.nextInt(mBound.height() / 2);
        radius = radius - factor * ExplosionUtils.RANDOM.nextInt(2);
        alpha = (1f - factor) * (1 + ExplosionUtils.RANDOM.nextFloat());
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha));
        canvas.drawCircle(cx, cy, radius / 2, paint);
    }
}
