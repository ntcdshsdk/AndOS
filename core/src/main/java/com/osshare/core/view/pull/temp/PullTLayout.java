package com.osshare.core.view.pull.temp;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by apple on 16/12/8.
 */
public class PullTLayout extends ViewGroup {
    private final String TAG = PullTLayout.class.getSimpleName();
    private static final float DRAG_RATE = .5f;
    private static final int INVALID_POINTER = -1;

    private static final int DIRECT_RESET = 0;
    private static final int DIRECT_PULL_DOWN = 1;
    private static final int DIRECT_PULL_UP = 2;
    private int mTouchSlop;

    private int mPullState;
    private int mPullDirection = DIRECT_RESET;
    private float mTippingHeight;
    private View mTarget;

    private int mCurrentTargetOffsetTop;

    private OnPullListener mPullListener;

    public boolean mEnablePullDown;
    public boolean mEnablePullUp;

    public SwipeRefreshLayout refreshLayout;

    public PullTLayout(Context context) {
        this(context, null);
    }

    public PullTLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullTLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        setWillNotDraw(false);

        mTippingHeight = 65 * getDisplayMetrics(context).density;
    }

    public void init() {

    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        mTarget = getChildAt(0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            mTarget = getChildAt(0);
        }
        if (mTarget == null) {
            return;
        }
        mTarget.measure(MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mTarget == null) {
            return;
        }
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        final View child = mTarget;
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
    }

    private int mActivePointerId = INVALID_POINTER;
    private float mInitialDownY;
    private float mLastMotionY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPullDirection = DIRECT_RESET;
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                mLastMotionY = mInitialDownY = MotionEventCompat.getY(ev, mActivePointerId);
                break;
            case MotionEvent.ACTION_MOVE:
                float yCurr = MotionEventCompat.getY(ev, mActivePointerId);
                float yDiff = yCurr - mInitialDownY;

                if (yDiff > mTouchSlop && !canTargetScrollDown()) {
                    mPullDirection = DIRECT_PULL_DOWN;
                } else if (-yDiff > mTouchSlop && !canTargetScrollUp()) {
                    mPullDirection = DIRECT_PULL_UP;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return mPullDirection != DIRECT_RESET;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                float yCurr = MotionEventCompat.getY(ev, mActivePointerId);
                float yDiff = (yCurr - mLastMotionY) * DRAG_RATE;
                if ((canTargetScrollDown() && yDiff > 0) || (canTargetScrollUp() && yDiff < 0)) {
                    mLastMotionY = yCurr;
                }
                if (mPullDirection == DIRECT_PULL_DOWN) {
                    setTargetOffsetTopAndBottom((int) (yDiff - mTarget.getTop()));
                } else if (mPullDirection == DIRECT_PULL_UP) {
                    setTargetOffsetTopAndBottom((int) (yDiff - mTarget.getTop()));
                }
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "mCurrentTargetOffsetTop:" + mCurrentTargetOffsetTop + "  mTippingHeight:" + mTippingHeight);
                if (mCurrentTargetOffsetTop > mTippingHeight) {
                    Log.i(TAG, "ACTION_UP-->1-->" + mCurrentTargetOffsetTop);
                    setTargetOffsetTopAndBottom((int) (-mCurrentTargetOffsetTop + mTippingHeight));
                    pullDownRelease();
                } else if (-mCurrentTargetOffsetTop > mTippingHeight) {
                    Log.i(TAG, "ACTION_UP-->2-->" + mCurrentTargetOffsetTop);
                    setTargetOffsetTopAndBottom((int) (-mCurrentTargetOffsetTop - mTippingHeight));
                    pullUpRelease();
                } else {
                    Log.i(TAG, "ACTION_UP-->3-->" + mCurrentTargetOffsetTop);
                    setTargetOffsetTopAndBottom(-mCurrentTargetOffsetTop);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.i(TAG, "ACTION_CANCEL");
                setTargetOffsetTopAndBottom(-mCurrentTargetOffsetTop);
                break;
        }

        return false;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going u.p. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionY = MotionEventCompat.getY(ev, newPointerIndex);
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    private void setTargetOffsetTopAndBottom(int offset) {
        mTarget.offsetTopAndBottom(offset);
        mCurrentTargetOffsetTop = mTarget.getTop();
    }

    //清除资源（动画等）防止泄漏
    public void reset() {

    }

    public void setPullResult(boolean pullDown, boolean pullUp) {
        setTargetOffsetTopAndBottom(-mCurrentTargetOffsetTop);
    }

    public boolean canTargetScrollDown() {
        return ViewCompat.canScrollVertically(mTarget, -1);
    }

    public boolean canTargetScrollUp() {
        return ViewCompat.canScrollVertically(mTarget, 1);
    }

    public void setOnPullListener(OnPullListener listener) {
        this.mPullListener = listener;
    }

    public void pullDownRelease() {
        if (mPullListener != null) {
            mPullListener.onPullDownRelease();
        }
    }

    public void pullUpRelease() {
        if (mPullListener != null) {
            mPullListener.onPullUpRelease();
        }
    }

    public interface OnPullListener {
        void onPullDownRelease();

        void onPullUpRelease();
    }


    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics;
    }
}
