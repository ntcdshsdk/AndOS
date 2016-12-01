package com.osshare.andos.view.single;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by apple on 16/10/27.
 */
public class ProgressLayout extends FrameLayout {
    private Paint mPaint;
    private TextView mTextView;

    private int bgColor = Color.parseColor("#FFF3DE");
    private int progressColor = Color.parseColor("#CFA767");
    private float strokeWidth = dip2px(getContext(), 4);

    private int progress = 72;

    private float cx, cy;

    public ProgressLayout(Context context) {
        super(context);
        init();
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

        mTextView = new TextView(getContext());
        this.addView(mTextView);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        mTextView.setLayoutParams(lp);
        mTextView.setGravity(Gravity.CENTER);
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


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public void setText(String text) {
        mTextView.setText(Html.fromHtml(text));//"10月12日<br>12:00<br>开售"
    }
}
