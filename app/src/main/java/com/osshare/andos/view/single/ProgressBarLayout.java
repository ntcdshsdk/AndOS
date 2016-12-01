package com.osshare.andos.view.single;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

/**
 * Created by apple on 16/10/26.
 */
public class ProgressBarLayout extends LinearLayout {
    private static final String TAG = "ProgressBarLayout";
    private Paint mPaint;

    private int bgColor = Color.parseColor("#FFF3DE");
    private int progressColor = Color.parseColor("#CFA767");
    private int dotColor = Color.parseColor("#FFF3DE");
    private float circleDotSpacing = dip2px(getContext(), 4);
    private int dotAngleSpacing = 10;
    private float strokeWidth = dip2px(getContext(), 4);
    private float dotStrokeWidth = dip2px(getContext(), 3);

    private int progress = 72;

    private float cx, cy;

    public ProgressBarLayout(Context context) {
        this(context, null);
    }

    public ProgressBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        cx = w / 2f;
        cy = h / 2f;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBgCircle(canvas);
        drawProgressCircle(canvas);
        drawDotCircle(canvas);

    }

    void drawBgCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(bgColor);
        mPaint.setStrokeWidth(strokeWidth);

        float radius = Math.min(cx, cy) - strokeWidth / 2;
        RectF oval = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawArc(oval, 270 + 360 * progress / 100, 270 + 360, false, mPaint);
    }

    void drawProgressCircle(Canvas canvas) {
        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(progressColor);
        mPaint.setStrokeWidth(strokeWidth);

        float radius = Math.min(cx, cy) - strokeWidth / 2;
        RectF oval = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawArc(oval, 270, 360 * progress / 100, false, mPaint);
    }

    void drawDotCircle(Canvas canvas) {
        mPaint.reset();
        mPaint.setStrokeWidth(dotStrokeWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(dotColor);

        for (int i = 0; i < 360; i++) {
            float angle = i * dotAngleSpacing;
            float x = (float) ((cx - circleDotSpacing - dotStrokeWidth - strokeWidth) * Math.cos(angle * Math.PI / 180f));
            float y = (float) ((cy - circleDotSpacing - dotStrokeWidth - strokeWidth) * Math.sin(angle * Math.PI / 180f));
            canvas.drawPoint(cx - x, cy - y, mPaint);
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
