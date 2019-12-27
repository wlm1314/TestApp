package com.wang.uiapp.menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MenuBgView extends View {

    private Paint paint;
    private Path path;

    public MenuBgView(Context context) {
        this(context, null, 0);
    }

    public MenuBgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(path, paint);
    }

    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setTouchY(MotionEvent ev, float percent) {
        path.reset();
        float width = getWidth() * percent;
        float height = getHeight();

        float beginX = 0;
        float beginY = -height / 8;

        float endX = 0;
        float endY = height + height / 8;

        float offsetX = width * 3 / 2;
        float offsetY = ev.getY();

        path.moveTo(beginX, beginY);
        path.quadTo(offsetX, offsetY, endX, endY);
        path.close();

        invalidate();

    }
}
