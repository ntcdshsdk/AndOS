package com.osshare.core.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by apple on 16/11/3.
 */
public class SlideButton extends View {
    private Paint framePaint;
    private Paint handlePaint;
    private RectF frameRect = new RectF();
    private RectF handleRect = new RectF();
    private ArgbEvaluator frameArgbEvaluator = new ArgbEvaluator();
    private float leftOffset = 0;
    private float intervalWidth = dip2px(2);
    private float mMaxWidth;
    private float mMinWidth = 0;
    private float handleRadius;

    private int checkedFrameColor = 0xFF3378D4;
    private int unCheckFrameColor = 0x667f7f7f;
    private int handleColor = Color.WHITE;

    private boolean mChecked;


    public SlideButton(Context context) {
        this(context, null);
    }

    public SlideButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        framePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        framePaint.setStrokeCap(Paint.Cap.ROUND);
        framePaint.setStrokeJoin(Paint.Join.ROUND);
        framePaint.setColor(mChecked ? checkedFrameColor : unCheckFrameColor);

        handlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        handlePaint.setStrokeCap(Paint.Cap.ROUND);
        handlePaint.setColor(handleColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        handleRadius = getMeasuredHeight() / 2 - intervalWidth;
        frameRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mMaxWidth = frameRect.right - 2 * intervalWidth - handleRadius * 2;
    }

    @Override
    public void onDraw(Canvas canvas) {
        drawFrame(canvas);
        drawHandle(canvas);
    }

    private float downX;
    private float downLeftOffset;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(!handleRect.contains(event.getX(),event.getY())){
                    return true;
                }
                downX = event.getRawX();
                downLeftOffset = handleRect.left - intervalWidth;
                break;
            case MotionEvent.ACTION_MOVE:
                float fraction = leftOffset * 1.0f / mMaxWidth;
                gradualChangeFrameColor(fraction, unCheckFrameColor, checkedFrameColor);

                float dx = event.getRawX() - downX;
                if (Math.abs(dx) > 5) {
                    leftOffset = downLeftOffset + dx;
                    leftOffset = fixLeftOffset(leftOffset);
                    if (leftOffset <= mMaxWidth && leftOffset >= mMinWidth) {
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                onRelease(event);
                break;
            case MotionEvent.ACTION_UP:
                onRelease(event);
                break;
        }

        return true;
    }

    public void drawFrame(Canvas canvas) {
        canvas.drawRoundRect(frameRect, dip2px(15), dip2px(15), framePaint);
    }

    public void drawHandle(Canvas canvas) {
        handleRect.set(intervalWidth + leftOffset, intervalWidth, intervalWidth + handleRadius * 2 + leftOffset,
                handleRadius * 2 + intervalWidth);
        canvas.drawOval(handleRect, handlePaint);
    }


    public void onRelease(MotionEvent event) {
        touchEventUp();
    }

    public void touchEventUp() {
        if (leftOffset <= mMaxWidth / 2 && mChecked) {
            mChecked = false;
        } else if (leftOffset >= mMaxWidth / 2 && !mChecked) {
            mChecked = true;
        }

        final int startColor = framePaint.getColor();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(handleRect.left - intervalWidth,
                mChecked ? mMaxWidth : mMinWidth);
        //该动画执行400毫秒
        valueAnimator.setDuration(400);
        //定义该运动为先加速再减速 (还有很多)
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        //开启动画
        valueAnimator.start();
        //增加动画执行监听 这里就可以每次给你返回执行进度和执行值
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //当前动画执行了多少 也就是我们的偏移量
                float offset = (Float) animation.getAnimatedValue();
                //当前动画执行进度，这个值是用来以后改变背景颜色的~
                float fraction = animation.getAnimatedFraction();
                gradualChangeFrameColor(fraction, startColor, mChecked ? checkedFrameColor : unCheckFrameColor);
                //赋值给我们的leftOffset吧
                leftOffset = (int) offset;
                //重新绘制
                invalidate();
            }

        });
    }

    public void gradualChangeFrameColor(float fraction, int startColor, int endColor) {
        framePaint.setColor((int) frameArgbEvaluator.evaluate(fraction, startColor, endColor));
    }

    private float fixLeftOffset(float leftOffset) {
        leftOffset = (int) (leftOffset > mMaxWidth ? mMaxWidth : leftOffset);
        leftOffset = (int) (leftOffset < mMinWidth ? mMinWidth : leftOffset);
        return leftOffset;
    }

    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
