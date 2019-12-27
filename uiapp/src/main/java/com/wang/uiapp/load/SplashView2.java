package com.wang.uiapp.load;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.Nullable;

import com.wang.uiapp.R;

public class SplashView2 extends View {
    private ValueAnimator mAnimator;
    // 大圆(里面包含很多小圆的)的半径
    private float mRotationRadius = 90;
    // 每一个小圆的半径
    private float mCircleRadius = 18;
    // 小圆圈的颜色列表，在initialize方法里面初始化
    private int[] mCircleColors;
    // 大圆和小圆旋转的时间
    private long mRotationDuration = 1200; //ms
    // 第二部分动画的执行总时间(包括二个动画时间，各占1/2)
    private long mSplashDuration = 1200; //ms
    // 整体的背景颜色
    private int mSplashBgColor = Color.WHITE;

    /**
     * 参数，保存了一些绘制状态，会被动态地改变* */
    //空心圆初始半径
    private float mHoleRadius = 0F;
    //当前大圆旋转角度(弧度)
    private float mCurrentRotationAngle = 0F;
    //当前大圆的半径
    private float mCurrentRotationRadius = mRotationRadius;

    // 绘制圆的画笔
    private Paint mPaint = new Paint();
    // 绘制背景的画笔
    private Paint mPaintBackground = new Paint();

    // 屏幕正中心点坐标
    private float mCenterX;
    private float mCenterY;
    //屏幕对角线一半
    private float mDiagonalDist;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w/2;
        mCenterY = h/2;

        mDiagonalDist = (float) Math.sqrt((w*w+h*h))/2f;//勾股定律
    }

    public SplashView2(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mCircleColors = context.getResources().getIntArray(R.array.splash_circle_colors);
        //画笔初始化
        //消除锯齿
        mPaint.setAntiAlias(true);
        mPaintBackground.setAntiAlias(true);
        //设置样式---边框样式--描边
        mPaintBackground.setStyle(Paint.Style.STROKE);
        mPaintBackground.setColor(mSplashBgColor);
    }

    public SplashView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SplashView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mState == null){
            mState = new RotateState();
        }
        mState.drawState(canvas);
    }


    /**
     * 状态转换
     */
    public void splashDisappear(){
        if(mState != null && mState instanceof RotateState){
            mState.cancel();

            post(new Runnable() {
                @Override
                public void run() {
                    mState = new MergingState();
                }
            });
        }
    }



    SplashState mState = null;
    //1.刚进来的时候执行旋转动画
    //2.数据加载完毕之后进行调用我们的聚合逃逸动画
    //3.聚合逃逸完成之后，进行扩散


    //策略模式
    private abstract class SplashState{
        public abstract void drawState(Canvas canvas);

        public void cancel(){
            mAnimator.cancel();
        }

    }


    /**
     * 旋转动画
     */
    private class RotateState extends SplashState{

        public RotateState(){
            //整体总共运行
            mAnimator = ValueAnimator.ofFloat(0f, (float) (Math.PI * 2));
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mAnimator.setDuration(mRotationDuration);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.start();
        }


        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircles(canvas);
        }



    }

    private void drawCircles(Canvas canvas) {

        //x = r*cos(a) + centerX
        //y = r*sin(a) + centerY

        //
        float rotationAngle = (float) (2*Math.PI / mCircleColors.length);


        for (int i = 0;i < mCircleColors.length ; i++){
            double angle = i * rotationAngle + mCurrentRotationAngle;
            float cx = (float) (mCurrentRotationRadius * Math.cos(angle) + mCenterX);
            float cy = (float) (mCurrentRotationRadius * Math.sin(angle) + mCenterY);
            mPaint.setColor(mCircleColors[i]);
            canvas.drawCircle(cx,cy,mCircleRadius,mPaint);

        }


    }

    private void drawBackground(Canvas canvas) {

        if(mHoleRadius > 0){

            float strokeWidth = mDiagonalDist - mHoleRadius;
            mPaintBackground.setStrokeWidth(strokeWidth);

            float radius = mHoleRadius + strokeWidth / 2;
            canvas.drawCircle(mCenterX,mCenterY,radius,mPaintBackground);

        }else {
            canvas.drawColor(mSplashBgColor);
        }

    }
    /**
     * 聚合
     */
    private class MergingState extends SplashState{

        public MergingState(){
            mAnimator = ValueAnimator.ofFloat(mRotationRadius,0);
            mAnimator.setDuration(mRotationDuration);
            mAnimator.setInterpolator(new OvershootInterpolator(10));
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentRotationRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ExpandState();
                }
            });
            mAnimator.reverse();
        }


        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawCircles(canvas);
        }
    }

    /**
     * 扩散
     */
    private class ExpandState extends SplashState{

        public ExpandState(){
            mAnimator = ValueAnimator.ofFloat(mCircleRadius,mDiagonalDist);
            mAnimator.setDuration(mRotationDuration);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mHoleRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mAnimator.start();


        }


        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }




}
