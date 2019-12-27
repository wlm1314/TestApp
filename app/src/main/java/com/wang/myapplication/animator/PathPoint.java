package com.wang.myapplication.animator;

public class PathPoint {
    static final int MOVE = 0;
    static final int LINE = 1;
    static final int CUBIC = 2;

    int mOperation;
    float mX, mY;
    float mC0X, mC0Y, mC1X, mC1Y;

    private PathPoint(int move, float x, float y) {
        mOperation = move;
        mX = x;
        mY = y;
    }

    private PathPoint(int move, float x1, float y1, float x2, float y2, float x, float y) {
        mOperation = move;
        mX = x;
        mY = y;
        mC0X = x1;
        mC0Y = y1;
        mC1X = x2;
        mC1Y = y2;
    }

    public static PathPoint moveTo(float x, float y){
        return new PathPoint(MOVE, x, y);
    }

    public static PathPoint lineTo(float x, float y){
        return new PathPoint(LINE, x, y);
    }

    public static PathPoint cubicTo(float x1, float y1, float x2, float y2, float x, float y){
        return new PathPoint(CUBIC, x1, y1, x2, y2, x, y);
    }
}
