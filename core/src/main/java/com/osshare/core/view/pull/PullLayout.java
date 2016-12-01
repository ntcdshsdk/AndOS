package com.osshare.core.view.pull;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.osshare.core.R;

/**
 * Created by apple on 16/11/20.
 */
public class PullLayout extends ViewGroup {
    private static final int INVALID_POINTER = -1;
    private static final int MAX_ALPHA = 255;
    private static final float DRAG_RATE = .5f;
    private static final int STARTING_PROGRESS_ALPHA = (int) (.3f * MAX_ALPHA);
    private SwipeRefreshLayout refreshLayout;

    private View mProgress;
    private View mTarget;
    private Drawable pullDrawable;
    private int pullState = 0;
    private OnPullListener pullListener;
    private int mOriginalOffsetTop;
    private int mTouchSlop;
    private boolean mIsBeingDragged = false;

    public PullLayout(Context context) {
        this(context, null);
    }

    public PullLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        createProgressView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
        mTarget.measure(MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));

        mOriginalOffsetTop = -mProgress.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
        final View child = mTarget;
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
    }

    private float mInitialDownY;
    private float mInitialMotionY;
    private int mActivePointerId;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                setTargetOffset(mOriginalOffsetTop - mProgress.getTop());
                mActivePointerId = ev.getPointerId(0);
                final float initialDownY = getMotionEventY(ev, mActivePointerId);
                if (initialDownY == -1) {
                    return false;
                }
                mInitialDownY = initialDownY;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }

                final float y = getMotionEventY(ev, mActivePointerId);
                if (y == -1) {
                    return false;
                }
                final float yDiff = y - mInitialDownY;
                if (yDiff > mTouchSlop && !mIsBeingDragged) {
                    mInitialMotionY = mInitialDownY + mTouchSlop;
                    mIsBeingDragged = true;
                    mProgress.setAlpha(.3f);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        super.onTouchEvent(ev);
        int pointerIndex = -1;
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                mIsBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE:{
                pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
                if (mIsBeingDragged) {
                    pullStart();
                    if (overscrollTop > 0) {
                        moveSpinner(overscrollTop);
                    } else {
                        return false;
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }

                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
                mIsBeingDragged = false;
                finishSpinner(overscrollTop);
                mActivePointerId = INVALID_POINTER;
                return false;
            }
            case MotionEvent.ACTION_CANCEL:
                return false;
        }
        return false;
    }

    private void moveSpinner(float overscrollTop) {

    }

    private void finishSpinner(float overscrollTop) {

    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(mProgress)) {
                    mTarget = child;
                    break;
                }
            }
        }
    }

    public void pullStart() {
        mProgress.setVisibility(View.VISIBLE);
    }

    public void setTargetOffset(int offset) {
        mProgress.bringToFront();
        mProgress.offsetTopAndBottom(offset);
    }

    private void createProgressView() {
        mProgress = new ImageView(getContext());
        ((ImageView) mProgress).setImageResource(R.drawable.pull);
        mProgress.setVisibility(View.GONE);
        addView(mProgress);
    }

    public int getPullState() {
        return pullState;
    }

    public void setPullState(int pullState) {
        this.pullState = pullState;
    }

    public OnPullListener getPullListener() {
        return pullListener;
    }

    public void setPullListener(OnPullListener pullListener) {
        this.pullListener = pullListener;
    }

    interface OnPullListener {
        void onPull(int pullState);
    }
}
