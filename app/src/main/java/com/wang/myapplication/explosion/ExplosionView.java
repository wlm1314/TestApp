package com.wang.myapplication.explosion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ExplosionView extends View {
    private ArrayList<ExplosionAnimator> explosionAnimators;
    //效果 工厂
    private ParticalFactory particalFactory;
    private OnClickListener onClickListener;

    public ExplosionView(Context context, ParticalFactory particalFactory) {
        super(context);
        explosionAnimators = new ArrayList<>();
        this.particalFactory = particalFactory;
        attach2Activity();
    }

    private void attach2Activity() {
        ViewGroup decorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(this, layoutParams);
    }

    //添加监听
    public void addListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                addListener(viewGroup.getChildAt(i));
            }
        } else {
            view.setClickable(true);
            view.setOnClickListener(getOnClickListener());
        }
    }

    public OnClickListener getOnClickListener() {
        if (onClickListener == null) {
            onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //触发粒子效果
                    explode(v);
                }
            };
        }
        return onClickListener;
    }

    //爆炸效果
    private void explode(final View view) {
        if (view.getVisibility() != View.VISIBLE || view.getAlpha() != 1)
            return;
        //确定控件的区域
        final Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        if (rect.width() == 0 || rect.height() == 0) return;
        //振动动画
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1).setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((ExplosionUtils.RANDOM.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
                view.setTranslationY((ExplosionUtils.RANDOM.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setTranslationX(0);
                view.setTranslationY(0);

                explode2(view, rect);
            }
        });
        animator.start();
    }

    private void explode2(final View view, Rect rect){
        final ExplosionAnimator explosionAnimator = new ExplosionAnimator(this, particalFactory, ExplosionUtils.createBItmapFromView(view), rect);
        explosionAnimators.add(explosionAnimator);
        explosionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.animate().setDuration(150).scaleX(0).scaleY(0).alpha(0).start();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.animate().setDuration(150).scaleX(1).scaleY(1).alpha(1).start();
                //移出
                explosionAnimators.remove(explosionAnimator);
            }
        });
        explosionAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("ExplosionView", explosionAnimators.size()+"---------");
        for (ExplosionAnimator explosionAnimator : explosionAnimators) {
            explosionAnimator.draw(canvas);
        }
    }
}
