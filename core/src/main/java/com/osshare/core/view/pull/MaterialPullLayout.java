package com.osshare.core.view.pull;

import android.content.Context;
import android.graphics.Color;
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

/**
 * Created by apple on 16/12/16.
 */
public class MaterialPullLayout extends ViewGroup {
    private SwipeRefreshLayout refreshLayout;
    private static final int INVALID_POINTER = -1;
    private final String TAG = MaterialPullLayout.class.getSimpleName();
    private static final float DRAG_RATE = .5f;
    private static final int STATE_PULL_ORIGINAL = 0X00;
    private static final int STATE_PULLING_DOWN = 0X10;
    private static final int STATE_PULL_DOWN_TIPPING = 0x11;
    private static final int STATE_PULL_DOWN_RELEASE = 0x12;
    private static final int STATE_PULLING_UP = 0X20;
    private static final int STATE_PULL_UP_TIPPING = 0x21;
    private static final int STATE_PULL_UP_RELEASE = 0x22;
    private static final int STATE_PULL_CANCEL = 0x31;


    private int mTouchSlop;
    private int mActivePointerId = INVALID_POINTER;
    private int HEADER_WIDTH = 150;
    private int HEADER_HEIGHT = 150;
    private int FOOTER_WIDTH = 150;
    private int FOOTER_HEIGHT = 150;

    //计算开始时便宜量的标志位
    private boolean mOriginalOffsetCalculated = false;
    private int mOriginalOffsetTop;
    private int mCurrentTargetOffsetTop;

    private View mTopView;
    private View mBottomView;
    public View mTarget;

    private int mTopViewIndex;
    private int mBottomViewIndex;

    public int mPullState = STATE_PULL_ORIGINAL;


    public MaterialPullLayout(Context context) {
        this(context, null);
    }

    public MaterialPullLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialPullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        createTopView();
        ViewCompat.setChildrenDrawingOrderEnabled(this, true);
    }

    public void createTopView() {
        mTopView = new ImageView(getContext());
        mTopView.setBackgroundColor(Color.RED);
        mTopView.setVisibility(GONE);
        addView(mTopView);

        mBottomView = new ImageView(getContext());
        mBottomView.setBackgroundColor(Color.GREEN);
        mBottomView.setVisibility(GONE);
        addView(mBottomView);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != mTopView && child != mBottomView) {
                mTarget = child;
                break;
            }
        }
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            return;
        }
        mTarget.measure(MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));
        mTopView.measure(MeasureSpec.makeMeasureSpec(HEADER_WIDTH, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(HEADER_HEIGHT, MeasureSpec.EXACTLY));
        mBottomView.measure(MeasureSpec.makeMeasureSpec(FOOTER_WIDTH, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(FOOTER_HEIGHT, MeasureSpec.EXACTLY));
        if (!mOriginalOffsetCalculated) {
            mOriginalOffsetCalculated = true;
            mCurrentTargetOffsetTop = mOriginalOffsetTop = -mTopView.getMeasuredHeight();
        }

        mTopViewIndex = -1;
        mBottomViewIndex = -1;
        for (int index = 0; index < getChildCount(); index++) {
            if (getChildAt(index) == mTopView) {
                mTopViewIndex = index;
            } else if (getChildAt(index) == mBottomView) {
                mBottomViewIndex = index;
            }
            if (mTopViewIndex != -1 && mBottomViewIndex != -1) {
                break;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (mTarget == null) {
            return;
        }
        final View child = mTarget;
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
//        Log.i(TAG, "onLayout-->mTargetWidth-->" + childWidth);
//        Log.i(TAG, "onLayout-->mTargetHeight-->" + childHeight);
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        int topViewWidth = mTopView.getMeasuredWidth();
        int topViewHeight = mTopView.getMeasuredHeight();
//        Log.i(TAG, "onLayout-->circleWidth-->" + circleWidth);
//        Log.i(TAG, "onLayout-->circleHeight-->" + circleHeight);

        Log.i(TAG, "onLayout-->mCurrentTargetOffsetTop-->" + mCurrentTargetOffsetTop);
        mTopView.layout((width / 2 - topViewWidth / 2), mCurrentTargetOffsetTop - topViewHeight,
                (width / 2 + topViewWidth / 2), mCurrentTargetOffsetTop);
        int footViewWidth = mBottomView.getMeasuredWidth();
        int footViewHeight = mBottomView.getMeasuredHeight();
        mBottomView.layout((width / 2 - footViewWidth / 2), height + mCurrentTargetOffsetTop,
                (width / 2 + footViewWidth / 2), height + footViewHeight + mCurrentTargetOffsetTop);
    }

    protected int getChildDrawingOrder(int childCount, int i) {
        if (mTopViewIndex < 0) {
            return i;
        } else if (i == childCount - 1) {
            // Draw the selected child last
            return mTopViewIndex;
        } else if (i >= mTopViewIndex) {
            // Move the children after the selected child earlier one
            return i + 1;
        } else {
            // Keep the children before the selected child the same
            return i;
        }
    }


    private float mInitialDownY;
    private float mLastMotionY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPullState = STATE_PULL_ORIGINAL;
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                final float initialDownY = getMotionEventY(ev, mActivePointerId);
                mInitialDownY = mLastMotionY = initialDownY;
                break;
            case MotionEvent.ACTION_MOVE:
                float yCurr = MotionEventCompat.getY(ev, mActivePointerId);
                float yDiff = yCurr - mInitialDownY;
                Log.i(TAG, "yDiff" + yDiff + "mTarget.getTop()" + mTarget.getTop());
                if (!canTargetScrollDown() && yDiff > 0) {
                    mPullState = STATE_PULLING_DOWN;
                    if (yDiff > HEADER_HEIGHT) {
                        mPullState = STATE_PULL_DOWN_TIPPING;
                    }
                } else if (!canTargetScrollUp() && yDiff < 0) {
                    mPullState = STATE_PULLING_UP;
                    if (-yDiff > FOOTER_HEIGHT) {
                        mPullState = STATE_PULL_UP_TIPPING;
                    }
                } else {
                    mPullState = STATE_PULL_ORIGINAL;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mPullState = STATE_PULL_CANCEL;
                break;
            case MotionEvent.ACTION_UP:
                if (mPullState == STATE_PULL_DOWN_TIPPING) {
                    mPullState = STATE_PULL_DOWN_RELEASE;
                } else if (mPullState == STATE_PULL_UP_TIPPING) {
                    mPullState = STATE_PULL_UP_RELEASE;
                } else {
                    mPullState = STATE_PULL_CANCEL;
                }
                break;
        }
        return mPullState == STATE_PULLING_DOWN
                || mPullState == STATE_PULLING_UP
                || mPullState == STATE_PULL_DOWN_TIPPING
                || mPullState == STATE_PULL_UP_TIPPING;
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
                mLastMotionY = yCurr;
                if (mPullState == STATE_PULLING_DOWN
                        || mPullState == STATE_PULLING_UP
                        || mPullState == STATE_PULL_DOWN_TIPPING
                        || mPullState == STATE_PULL_UP_TIPPING) {
//                    Log.i(TAG, "yDiff-->" + yDiff + "mTarget.getTop()-->" + mTarget.getTop());
                    setTargetOffsetTopAndBottom((int) yDiff, true);
                }
                break;
            case MotionEvent.ACTION_UP:
                setTargetOffsetTopAndBottom(-mCurrentTargetOffsetTop, true);
                break;
            case MotionEvent.ACTION_CANCEL:
                setTargetOffsetTopAndBottom(-mCurrentTargetOffsetTop, true);
                break;
        }
        return false;
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    private void setTargetOffsetTopAndBottom(int offset, boolean requiresUpdate) {
        Log.i(TAG, "mPullState" + mPullState);
        if (mPullState == STATE_PULLING_DOWN
                || mPullState == STATE_PULL_DOWN_TIPPING
                || mPullState == STATE_PULL_DOWN_RELEASE) {
            mTopView.setVisibility(VISIBLE);
            mTopView.bringToFront();
            mTopView.offsetTopAndBottom(offset);
            mCurrentTargetOffsetTop = mTopView.getTop();
        } else if (mPullState == STATE_PULLING_UP
                || mPullState == STATE_PULL_UP_TIPPING
                || mPullState == STATE_PULL_UP_RELEASE) {
            mBottomView.setVisibility(VISIBLE);
            mBottomView.bringToFront();
            mBottomView.offsetTopAndBottom(offset);
            mCurrentTargetOffsetTop = mBottomView.getTop();
        }
//        mTarget.offsetTopAndBottom(offset);
//        mCurrentTargetOffsetTop = mTarget.getTop();

        if (requiresUpdate && android.os.Build.VERSION.SDK_INT < 11) {
            invalidate();
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }

    public boolean canTargetScrollDown() {
        return ViewCompat.canScrollVertically(mTarget, -1);
    }

    public boolean canTargetScrollUp() {
        return ViewCompat.canScrollVertically(mTarget, 1);
    }

}
