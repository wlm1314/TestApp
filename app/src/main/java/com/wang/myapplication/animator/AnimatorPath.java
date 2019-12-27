package com.wang.myapplication.animator;

import android.animation.ObjectAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AnimatorPath {

    private List<PathPoint> mPathPoint = new ArrayList<>();
    private View view;

    public void moveTo(float x, float y) {
        mPathPoint.add(PathPoint.moveTo(x, y));

    }

    public void cubicTo(float x1, float y1, float x2, float y2, float x, float y) {
        mPathPoint.add(PathPoint.cubicTo(x1, y1, x2, y2, x, y));
    }

    public void lineTo(float x, float y) {
        mPathPoint.add(PathPoint.lineTo(x, y));
    }

    public void startAnimation(View view, int duration) {
        this.view = view;
        ObjectAnimator animator = ObjectAnimator.ofObject(this, "haha", new PathEvalutor(), mPathPoint.toArray());
        animator.setDuration(duration);
        animator.start();
    }

    public void setHaha(PathPoint pathPoint) {
        view.setTranslationX(pathPoint.mX);
        view.setTranslationY(pathPoint.mY);
    }

}
