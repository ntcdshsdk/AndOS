package com.osshare.andos.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * 旗舰店圆形图章进度条
 */
public class StampView extends View {
    private final String TAG = getClass().getSimpleName();
    private Paint mPaint;
    private Paint titlePaint;
    private Paint subTitlePaint;
    private Paint percentPaint;

    private int bgColor;
    private int progressColor;
    private int dotColor;
    private int titleColor;
    private int subTitleColor;

    private int textSpacing;
    private float circleDotSpacing;
    private float dotDegreeSpacing;
    private float strokeWidth;
    private float dotStrokeWidth;
    private float titleTextSize;
    private float subTitleTextSize;

    private int textAngle = 0;
    private String[] titles;
    private int progress = 0;

    private float cx, cy;

    public StampView(Context context) {
        this(context, null);
    }

    public StampView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StampView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }


//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//        cx=getMeasuredWidth()/2f;
//        cy=getMeasuredHeight()/2f;
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Log.i(TAG, "onSizeChanged");
        cx = w / 2f;
        cy = h / 2f;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw");
        drawBgCircle(canvas);
        drawProgressCircle(canvas);
        drawDotCircle(canvas);
        drawText(canvas);


    }

    void drawBgCircle(Canvas canvas) {
        Log.i(TAG, "drawBgCircle");
        mPaint.reset();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(bgColor);
        mPaint.setStrokeWidth(strokeWidth);

        float radius = Math.min(cx, cy) - strokeWidth / 2;

        RectF oval = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        canvas.drawArc(oval, 270 + 360 * progress / 100, 270 + 360, false, mPaint);

    }

    void drawProgressCircle(Canvas canvas) {
        Log.i(TAG, "drawProgressCircle");
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
        Log.i(TAG, "drawDotCircle");
        mPaint.reset();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStrokeWidth(dotStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(dotColor);

        float R = cx - strokeWidth - circleDotSpacing - dotStrokeWidth / 2;

        float unitDegree = dotDegreeSpacing;
//        float dotCount = 360 / unitDegree;
//        for (int i = 0; i < dotCount; i++) {
//            canvas.drawPoint(cx, cy - R, mPaint);
//            canvas.rotate(unitDegree, cx, cy);
//        }

        Path path = new Path();
        path.addCircle(cx, cy, R, Path.Direction.CW);
        PathEffect effect = new DashPathEffect(new float[]{1, (float) (Math.PI*R*unitDegree/180)}, 0);
        mPaint.setPathEffect(effect);
        canvas.drawPath(path, mPaint);
    }

    void drawText(Canvas canvas) {
        Log.i(TAG, "drawText");
        float totalTextHeight = 0;
        float titleHeight = 0;
        float subTitleWidth = 0;
        float subTitleHeight = 0;
        float titleOffset = 0;
        float subTitleOffset = 0;

        if (titles != null) {
            titlePaint = new Paint();
            titlePaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            titlePaint.setColor(titleColor);
            titlePaint.setTextSize(titleTextSize);
            //        float titleWidth = titlePaint.measureText(titles[0]);
            Paint.FontMetrics titleMetrics = titlePaint.getFontMetrics();
            titleHeight = titleMetrics.descent - titleMetrics.ascent;
            totalTextHeight = titleHeight * titles.length;

            titleOffset = (titleMetrics.ascent - titleMetrics.top + titleMetrics.bottom - titleMetrics.descent);
            if (titles.length >= 3) {
                titleOffset = titleOffset - 3;
            }
        }
        if (progress != 0) {
            subTitlePaint = new Paint();
            subTitlePaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            subTitlePaint.setColor(subTitleColor);
            subTitlePaint.setTextSize(subTitleTextSize);
            subTitleWidth = subTitlePaint.measureText(String.valueOf(progress));
            Paint.FontMetrics subTitleMetrics = subTitlePaint.getFontMetrics();
            subTitleHeight = subTitleMetrics.descent - subTitleMetrics.ascent;
            totalTextHeight += subTitleHeight;
            subTitleOffset = (subTitleMetrics.ascent - subTitleMetrics.top + subTitleMetrics.bottom - subTitleMetrics.descent);
        }
        totalTextHeight += textSpacing;

        float radius = Math.min(cx, cy) - strokeWidth / 2;
        Path path = new Path();
        path.moveTo((float) (cx - radius * Math.cos(textAngle * Math.PI / 180f)), (float) (cx + radius * Math.sin(textAngle * Math.PI / 180f)));
        path.lineTo((float) (cx + radius * Math.cos(textAngle * Math.PI / 180f)), (float) (cx - radius * Math.sin(textAngle * Math.PI / 180f)));
        if (titles != null) {
            for (int i = 0; i < titles.length; i++) {
                float titleWidth = titlePaint.measureText(titles[i]);
                canvas.drawTextOnPath(titles[i], path, radius - titleWidth / 2, -totalTextHeight / 2 + titleHeight * (i + 1) - titleOffset, titlePaint);
            }
        }

        if (progress != 0) {
            percentPaint = new Paint();
            percentPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
            percentPaint.setColor(subTitleColor);
            percentPaint.setTextSize(titleTextSize);
            float percentWidth = percentPaint.measureText("%");
            Paint.FontMetrics percentMetrics = percentPaint.getFontMetrics();
            float percentHeight = percentMetrics.bottom - percentMetrics.top;

            canvas.drawTextOnPath(String.valueOf(progress), path, radius - (subTitleWidth + percentWidth) / 2, totalTextHeight / 2 - subTitleOffset, subTitlePaint);
            canvas.drawTextOnPath("%", path, radius - (subTitleWidth + percentWidth) / 2 + subTitleWidth, totalTextHeight / 2 - subTitleHeight + percentHeight, percentPaint);
        }

    }

    public void initDefault(int width, int height) {
        this.setMinimumWidth(width);
        this.setMinimumHeight(height);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 显示进度条
     *
     * @param titles           按照数组换行
     * @param titleColor       标题文字颜色
     * @param titleTextSize    标题文字颜色
     * @param textAngle        文字旋转角度
     * @param subTitleTextSize 副标题文字旋转角度
     * @param subTitleColor    副标题文字颜色
     * @param textSpacing      标题和副标题之间的间距
     * @param strokeWidth      进度条背景及进度条宽度
     * @param bgColor          进度条背景颜色
     * @param progress         进度（1～100）
     * @param progressColor    进度条颜色
     * @param dotStrokeWidth   小点点的宽度（直径）
     * @param dotColor         小点点的颜色
     * @param dotDegreeSpacing 小点点圆心之间的角度
     */
    public void showProgressWithLabel(String[] titles, int titleColor, int titleTextSize, int textAngle,
                                      int subTitleTextSize, int subTitleColor, int textSpacing,
                                      int strokeWidth, int bgColor, int progress, int progressColor,
                                      int dotStrokeWidth, int dotColor, float dotDegreeSpacing) {
        this.titles = titles;
        this.titleColor = titleColor;
        this.titleTextSize = titleTextSize;
        this.textAngle = textAngle;
        this.textSpacing = textSpacing;
        this.strokeWidth = strokeWidth;
        this.bgColor = bgColor;
        this.progress = progress;
        this.subTitleTextSize = subTitleTextSize;
        this.subTitleColor = subTitleColor;
        this.progressColor = progressColor;
        this.dotStrokeWidth = dotStrokeWidth;
        this.dotColor = dotColor;
        this.circleDotSpacing = dotStrokeWidth;
        this.dotDegreeSpacing = dotDegreeSpacing;
        invalidate();
    }

    /**
     * 显示进度条
     *
     * @param title            进度条内部显示的标题文字（超过两字换行，最多显示4个字）
     * @param titleColor       标题文字颜色
     * @param titleTextSize    标题文字颜色
     * @param textAngle        文字旋转角度
     * @param subTitleTextSize 副标题文字旋转角度
     * @param subTitleColor    副标题文字颜色
     * @param textSpacing      标题和副标题之间的间距
     * @param strokeWidth      进度条背景及进度条宽度
     * @param bgColor          进度条背景颜色
     * @param progress         进度（1～100）
     * @param progressColor    进度条颜色
     * @param dotStrokeWidth   小点点的宽度（直径）
     * @param dotColor         小点点的颜色
     * @param dotDegreeSpacing 小点点圆心之间的角度
     */
    public void showProgressWithLabel(String title, int titleColor, int titleTextSize, int textAngle,
                                      int subTitleTextSize, int subTitleColor, int textSpacing,
                                      int strokeWidth, int bgColor, int progress, int progressColor,
                                      int dotStrokeWidth, int dotColor, float dotDegreeSpacing) {

        if (TextUtils.isEmpty(title) || title.length() > 6) {
            titles = null;
        } else {
            if (title.length() <= 3) {
                titles = new String[]{title};
            } else {
                List<String> temps = new ArrayList<>();
                for (int i = 0; i < title.length(); i = i + 2) {
                    int end = i + 2 > title.length() ? title.length() : i + 2;
                    temps.add(title.substring(i, end));
                }
                titles = new String[temps.size()];
                temps.toArray(titles);
            }
        }
        showProgressWithLabel(titles, titleColor, titleTextSize, textAngle,
                subTitleTextSize, subTitleColor, textSpacing,
                strokeWidth, bgColor, progress, progressColor,
                dotStrokeWidth, dotColor, dotDegreeSpacing);
    }

    public void showDefault1(String title, int progress, int mainColor, int secondColor) {
        showProgressWithLabel(title, mainColor, sp2px(getContext(), 9), 0, sp2px(getContext(), 17),
                mainColor, 0, dip2px(getContext(), 2.5f), secondColor,
                progress, mainColor, dip2px(getContext(), 1.5f), secondColor, 7.2f);
    }

    public void showDefault2(String title, int mainColor) {
        showProgressWithLabel(title, mainColor, sp2px(getContext(), 13), 14, sp2px(getContext(), 17),
                mainColor, 0, dip2px(getContext(), 1.5f), mainColor,
                0, mainColor, dip2px(getContext(), 2.5f), mainColor, 7.2f);
    }

    public void showDefault3(String[] titles, int mainColor) {
        showProgressWithLabel(titles, mainColor, sp2px(getContext(), 9), 0,
                sp2px(getContext(), 17), mainColor, 0,
                dip2px(getContext(), 1.5f), mainColor, 0, mainColor,
                dip2px(getContext(), 1.5f), mainColor, 7.2f);
    }
}
