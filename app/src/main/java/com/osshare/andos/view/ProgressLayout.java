package com.osshare.andos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by apple on 16/10/27.
 */
public class ProgressLayout extends FrameLayout {
    private static final String TAG = "StampProgressBar";
    private Paint mPaint;

    private int bgColor = Color.parseColor("#FFF3DE");
    private int progressColor = Color.parseColor("#CFA767");
    private int dotColor = Color.parseColor("#FFF3DE");

    private float circleDotSpacing = dip2px(getContext(), 8);
    private int dotAngleSpacing = 10;
    private float strokeWidth = dip2px(getContext(), 4);
    private float dotStrokeWidth;
    private int progress = 0;

    private float cx, cy;

    public ProgressLayout(Context context) {
        this(context,null);
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mPaint = new Paint();
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
//        drawDotCircle(canvas);

    }

    void drawBgCircle(Canvas canvas) {
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(bgColor);
        mPaint.setStrokeWidth(strokeWidth);

        float radius = Math.min(cx, cy) - strokeWidth / 2;

        RectF oval = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawArc(oval, 270 + 360 * progress / 100, 270 + 360, false, mPaint);
    }

    void drawProgressCircle(Canvas canvas) {
        mPaint.reset();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(progressColor);
        mPaint.setStrokeWidth(strokeWidth);

        float radius = Math.min(cx, cy) - strokeWidth / 2;
        RectF oval = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawArc(oval, 270, 360 * progress / 100, false, mPaint);
    }

    void drawDotCircle(Canvas canvas) {
        mPaint.reset();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
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


    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public int getDotColor() {
        return dotColor;
    }

    public void setDotColor(int dotColor) {
        this.dotColor = dotColor;
    }

    public float getCircleDotSpacing() {
        return circleDotSpacing;
    }

    public void setCircleDotSpacing(float circleDotSpacing) {
        this.circleDotSpacing = circleDotSpacing;
    }

    public int getDotAngleSpacing() {
        return dotAngleSpacing;
    }

    public void setDotAngleSpacing(int dotAngleSpacing) {
        this.dotAngleSpacing = dotAngleSpacing;
    }

    public float getStrokeWidth() {

        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        float width=dip2px(getContext(),strokeWidth);
        this.strokeWidth = width;
    }

    public float getDotStrokeWidth() {
        return dotStrokeWidth;
    }

    public void setDotStrokeWidth(float dotStrokeWidth) {
        this.dotStrokeWidth = dotStrokeWidth;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
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
