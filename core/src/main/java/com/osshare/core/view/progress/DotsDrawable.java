package com.osshare.core.view.progress;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

/**
 * Created by apple on 16/12/13.
 */
public class DotsDrawable extends Drawable {
    private final Paint mPaint;
    private static final float POINT_WIDTH = 5;

    private float degreeSpace;
    private float degreeOffset = 0;
    private long mDuration = 100;

    Activity activity;

    public DotsDrawable(Activity activity) {
        //设置画笔的相关属性
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.GREEN);
        degreeOffset = 0;
        degreeSpace = 45;

        this.activity = activity;

        //开启动画
        AnimationsRunnable animationRunnable = new AnimationsRunnable();
        new Thread(animationRunnable).start();
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();
        int a = rect.centerX();
        int b = rect.centerY();

        if (a == 0 || b == 0) {
            return;
        }

        for (int i = 0; i < 360 / degreeSpace; i++) {

            double degree = degreeOffset + degreeSpace * i;
            double sinNum = Math.sin(degree);
            double cosNum = Math.cos(degree);

            float x = (float) (a + cosNum * (a - POINT_WIDTH));
            float y = (float) (b + sinNum * (b - POINT_WIDTH));

            canvas.drawCircle(x, y, POINT_WIDTH * 3.0f / 4.0f + (int) (i * 1.01), mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return 0;
    }


    private class AnimationsRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                SystemClock.sleep(mDuration);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        invalidateSelf();
                    }
                });
            }
        }
    }


}
