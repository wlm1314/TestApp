package com.wang.myapplication.animator;

import android.animation.TypeEvaluator;

/**
 * 估值器
 */
public class PathEvalutor implements TypeEvaluator<PathPoint> {
    @Override
    public PathPoint evaluate(float fraction, PathPoint startValue, PathPoint endValue) {
        float x = 0, y = 0;
        switch (endValue.mOperation) {
            case PathPoint.MOVE:
                x = endValue.mX;
                y = endValue.mY;
                break;
            case PathPoint.LINE:
                x = startValue.mX + fraction * (endValue.mX - startValue.mX);
                y = startValue.mY + fraction * (endValue.mY - startValue.mY);
                break;
            case PathPoint.CUBIC:
                float oneMinusT = 1 - fraction;
                x = oneMinusT * oneMinusT * oneMinusT * startValue.mX +
                        3 * oneMinusT * oneMinusT * fraction * endValue.mC0X +
                        3 * oneMinusT * fraction * fraction * endValue.mC1X +
                        fraction * fraction * fraction * endValue.mX;
                y = oneMinusT * oneMinusT * oneMinusT * startValue.mY +
                        3 * oneMinusT * oneMinusT * fraction * endValue.mC0Y +
                        3 * oneMinusT * fraction * fraction * endValue.mC1Y +
                        fraction * fraction * fraction * endValue.mY;
                break;
        }
        return PathPoint.moveTo(x, y);
    }
}
