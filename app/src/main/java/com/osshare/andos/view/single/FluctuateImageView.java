package com.osshare.andos.view.single;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by apple on 16/10/17.
 */
public class FluctuateImageView extends ImageView {
    private float[] mColorMatrixSrc;

    public FluctuateImageView(Context context) {
        super(context);
    }

    public FluctuateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FluctuateImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable != null && mColorMatrixSrc != null) {
            ColorMatrix mColorMatrix = new ColorMatrix();   //新建颜色矩阵对象
            mColorMatrix.set(mColorMatrixSrc);
            drawable.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        }

        super.onDraw(canvas);
    }

    public void setColorMatrixSrc(float[] src) {
        this.mColorMatrixSrc = src;
        invalidate();
    }
}
