package com.osshare.andos.view.single;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;


/**
 * 旗舰店圆形图章进度条
 * Created by caodongsheng
 */
public class StampProgressBar extends View {
    private static final String TAG = "StampProgressBar";
    private Paint mPaint;
    private Paint titlePaint;
    private Paint subTitlePaint;
    private Paint percentPaint;

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

    private String[] titles ;//= new String[]{"12月20日", "10:00", "开售"};
    private int progress = 42;

    private float cx, cy;

    public StampProgressBar(Context context) {
        this(context, null);
    }

    public StampProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StampProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
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
        drawText(canvas);

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
        mPaint.setStrokeWidth(strokeWidth*2/3);
        mPaint.setColor(dotColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        int dotAngle= (int) (180*700/Math.PI/(Math.min(cx, cy) - strokeWidth / 2-circleDotSpacing));

        for (int i = 0; i < 360; i++) {
            float angle = i * dotAngle;
            float x = (float) ((cx - circleDotSpacing) * Math.cos(angle * Math.PI / 180f));
            float y = (float) ((cy - circleDotSpacing) * Math.sin(angle * Math.PI / 180f));
            canvas.drawPoint(cx - x, cy - y, mPaint);
//            canvas.drawCircle(cx - x, cy - y, strokeWidth / 3, mPaint);
        }
    }

    void drawText(Canvas canvas) {

        float totalTextHeight = 0;
        float titleHeight = 0;
        float subTitleWidth = 0;
        float subTitleHeight = 0;

        if (titles != null) {
            titlePaint = new Paint();
            titlePaint.setColor(titleColor);
            titlePaint.setTextSize(titleTextSize);
            //        float titleWidth = titlePaint.measureText(titles[0]);
            Paint.FontMetrics titleMetrics = titlePaint.getFontMetrics();
            titleHeight = titleMetrics.descent - titleMetrics.ascent;
            totalTextHeight = titleHeight * titles.length;
        }
        if (progress != 0) {
            subTitlePaint = new Paint();
            subTitlePaint.setColor(subTitleColor);
            subTitlePaint.setTextSize(subTitleTextSize);
            subTitleWidth = subTitlePaint.measureText(String.valueOf(progress));
            Paint.FontMetrics subTitleMetrics = subTitlePaint.getFontMetrics();
            subTitleHeight = subTitleMetrics.descent - subTitleMetrics.ascent;
            totalTextHeight += subTitleHeight;
        }
        totalTextHeight += textSpacing;


        if (titles != null) {
            for (int i = 0; i < titles.length; i++) {
                float titleWidth = titlePaint.measureText(titles[i]);
                canvas.drawText(titles[i], cx - titleWidth / 2, cx - totalTextHeight / 2 + titleHeight * (i + 1), titlePaint);
            }
        }

        if (progress != 0) {
            percentPaint = new Paint();
            percentPaint.setColor(subTitleColor);
            percentPaint.setTextSize(titleTextSize);
            float percentWidth = percentPaint.measureText("%");
            Paint.FontMetrics percentMetrics = percentPaint.getFontMetrics();
            float percentHeight = percentMetrics.descent - percentMetrics.ascent;

            canvas.drawText(String.valueOf(progress), cx - (subTitleWidth + percentWidth) / 2, cy + totalTextHeight / 2, subTitlePaint);
            canvas.drawText("%", cx + (subTitleWidth - percentWidth) / 2, cy + totalTextHeight / 2, percentPaint);
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


    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
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
