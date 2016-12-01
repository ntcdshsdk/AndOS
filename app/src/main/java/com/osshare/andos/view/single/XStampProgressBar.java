package com.osshare.andos.view.single;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


/**
 * 旗舰店圆形图章进度条
 * Created by caodongsheng
 */
public class XStampProgressBar extends View {
    private static final String TAG = "StampProgressBar";
    private Paint mPaint;

    private int bgColor = Color.parseColor("#FFF3DE");
    private int progressColor = Color.parseColor("#CFA767");
    private int dotColor = Color.parseColor("#FFF3DE");
    private int titleColor = Color.parseColor("#CFA767");
    private int subTitleColor = Color.parseColor("#CFA767");

    private int textSpacing = dip2px(getContext(), 5);
    private float circleDotSpacing = dip2px(getContext(), 8);
    private int dotAngleSpacing = 10;
    private float strokeWidth = dip2px(getContext(), 4);
    private float titleTextSize = sp2px(getContext(), 9);
    private float subTitleTextSize = sp2px(getContext(), 17);

    private int progress = 0;

    private float cx, cy;

    public XStampProgressBar(Context context) {
        this(context, null);
    }

    public XStampProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XStampProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

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

        canvas.drawCircle(cx, cy, radius, mPaint);
    }

    void drawProgressCircle(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(progressColor);
        mPaint.setStrokeWidth(strokeWidth);

        float radius = Math.min(cx, cy) - strokeWidth / 2;
        RectF oval = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawArc(oval, 270, 360 * progress / 100, false, mPaint);
    }

    void drawDotCircle(Canvas canvas) {
        mPaint.reset();
//        mPaint.setStrokeWidth(strokeWidth / 2);
        mPaint.setColor(dotColor);

        for (int i = 0; i < 360; i++) {
            float angle = i * dotAngleSpacing;
            float x = (float) ((cx - circleDotSpacing) * Math.cos(angle * Math.PI / 180f));
            float y = (float) ((cy - circleDotSpacing) * Math.sin(angle * Math.PI / 180f));
//            canvas.drawPoint(cx - x, cy - y, mPaint);
            canvas.drawCircle(cx - x, cy - y, strokeWidth / 3, mPaint);
        }
    }



    public void initDefault(int width, int height) {
        this.setMinimumWidth(width);
        this.setMinimumHeight(height);
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

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
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
        this.strokeWidth = strokeWidth;
    }

    public int getTextSpacing() {
        return textSpacing;
    }

    public void setTextSpacing(int textSpacing) {
        this.textSpacing = textSpacing;
    }

    public float getTitleTextSize() {
        return titleTextSize;
    }

    public void setTitleTextSize(float titleTextSize) {
        this.titleTextSize = titleTextSize;
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
