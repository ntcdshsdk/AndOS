package com.osshare.andos.view.single;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by apple on 16/10/6.
 */
public class LineLayout extends FrameLayout {
    private static final String TAG = LineLayout.class.getSimpleName();
    private Paint mPaint;
    private View mTarget;

//    private int[] mStateColors;
//    private int[][] mStateSets;
//    private int[] mStateSet = StateSet.WILD_CARD;

    private float mLineSize = -1;
    private boolean mState;
    private int mStateColor1;
    private int mStateColor;

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

        if (mLineSize > 0 && mStateColor > 0) {
            mPaint.setColor(mState ? mStateColor : mStateColor1);
            mPaint.setStrokeWidth(mLineSize);
            canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), mPaint);
        }

    }

    public void setState(boolean state) {
        if (mState != state) {
            this.mState = state;
//            mPaint.setColor(mStateColors.get(state));
            invalidate();
        }
    }


    public void setLineSize(float lineSize) {
        if (mLineSize != lineSize) {
            this.mLineSize = lineSize;
//            invalidate();
        }
    }

    public void setTarget(View target) {
        this.mTarget = target;
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

    public void attactState() {
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }



}
