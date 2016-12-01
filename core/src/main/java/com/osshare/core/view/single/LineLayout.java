package com.osshare.core.view.single;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.StateSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 16/10/6.
 */
public class LineLayout extends FrameLayout {
    private static final String TAG = LineLayout.class.getSimpleName();
    private Paint mPaint;

    private float mLineSize = -1;
    private int mState;
    private SparseArray<Integer> mStateColors;

    public LineLayout(Context context) {
        this(context, null);
    }

    public LineLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "[w:" + w + "] [h:" + h + "] [oldw:" + oldw + "] [oldw:" + oldh + "]");
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mStateColors != null) {
            mPaint.setColor(mStateColors.get(mState));
            mPaint.setStrokeWidth(mLineSize);
            canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), mPaint);
        }

    }

    public void setState(int state) {
        if (mState != state && mStateColors != null) {
            this.mState = state;
            mPaint.setColor(mStateColors.get(state));
            invalidate();
        }
    }

    public void addStateColor(int state, Integer color) {
        if (mStateColors == null) {
            mStateColors = new SparseArray<>();
        }
        mStateColors.put(state, color);
    }

    public void setStateColors(SparseArray<Integer> stateColors) {
        mStateColors = stateColors;
    }


    public void setLineSize(float lineSize) {
        if (mLineSize != lineSize) {
            this.mLineSize = lineSize;
//            invalidate();
        }
    }

//    public void addState(int[] stateSet, int color) {
//        if (color > 0) {
//
//        }
//    }

//    public boolean setState(final int[] stateSet) {
//        if (!Arrays.equals(mStateSet, stateSet)) {
//            mStateSet = stateSet;
//            return onStateChange(stateSet);
//        }
//        return false;
//    }
//
//    public boolean onStateChange(int[] stateSet) {
//        int stateIndex = indexOfStateSet(stateSet);
//        if (stateIndex < 0) {
//            return false;
//        }
//        mLineColor = mStateColors[stateIndex];
//        invalidate();
//        return true;
//    }
//
//    int indexOfStateSet(int[] stateSet) {
//        final int[][] stateSets = mStateSets;
//        final int N = getChildCount();
//        for (int i = 0; i < N; i++) {
//            if (StateSet.stateSetMatches(stateSets[i], stateSet)) {
//                return i;
//            }
//        }
//        return -1;
//    }


}
