package com.osshare.core.view.pull;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by apple on 16/12/1.
 */
public class PullableLayout extends LinearLayout {
    SwipeRefreshLayout sw;
    ViewDragHelper dragHelper;
    private int pullState = 0;
    private PullableHeader headerView;
    private PullableFooter footerView;
    private View mTarget;

    public PullableLayout(Context context) {
        this(context, null);
    }

    public PullableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mTarget.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() + Math.abs(getMeasuredHeight() - child.getMeasuredHeight());
//                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            //手指释放的时候回调
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == mTarget) {
                    dragHelper.settleCapturedViewAt(0, 0);
                    invalidate();
                }
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                dragHelper.captureChildView(mTarget, pointerId);
            }
        });
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (!child.equals(headerView) && !child.equals(footerView)) {
                mTarget = child;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }


//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//            if (!child.equals(headerView) && !child.equals(footerView)) {
//                mTarget = child;
//                break;
//            }
//        }
//        mTarget.measure(MeasureSpec.makeMeasureSpec(
//                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
//                MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
//                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));
//    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        final int width = getMeasuredWidth();
//        final int height = getMeasuredHeight();
//        if (mTarget == null) {
//            return;
//        }
//        final View child = mTarget;
//        final int childLeft = getPaddingLeft();
//        final int childTop = getPaddingTop();
//        final int childWidth = width - getPaddingLeft() - getPaddingRight();
//        final int childHeight = height - getPaddingTop() - getPaddingBottom();
//        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
////        int circleWidth = headerView.getMeasuredWidth();
////        int circleHeight = headerView.getMeasuredHeight();
////        headerView.layout((width / 2 - circleWidth / 2), mCurrentTargetOffsetTop,
////                (width / 2 + circleWidth / 2), mCurrentTargetOffsetTop + circleHeight);
//    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
