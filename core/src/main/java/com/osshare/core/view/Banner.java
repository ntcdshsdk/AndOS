package com.osshare.core.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.ViewFlipper;

import com.osshare.core.R;

import java.util.ArrayList;

/**
 * Created by apple on 16/12/22.
 */
public class Banner extends LinearLayout {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    private ViewDragHelper mDragHelper;

    private ArrayList<View> mRecycleViews;

    private ViewFlipper flipper;
    ViewPager viewPager;

    private float mTouchSlop;

    private int mOrientation = HORIZONTAL;

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mDragHelper = ViewDragHelper.create(this, new DragCallback());
        setOrientation(LinearLayout.HORIZONTAL);
        init();
    }

    public void init() {

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        final int scrollX = mDraggedX;//getScrollX();
//        int childLeft = getPaddingLeft() + getHorizontalFadingEdgeLength();
//        childLeft += scrollX;
//        final int count = getChildCount();
//        for (int i = 0; i < count; i++) {
//            final View child = getChildAt(i);
//            if (child.getVisibility() != View.GONE) {
//                final int width = child.getMeasuredWidth();
//                final int height = child.getMeasuredHeight();
//                child.layout(childLeft, getPaddingTop(), childLeft + width,
//                        getPaddingTop() + height);
//                childLeft += width;
//            }
//        }
//    }


    private float mMotionDownX;
    private float mMotionDownY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);

//        int action = event.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float currX = event.getX();
//                float currY = event.getY();
//                float diffX = currX - mMotionDownX;
//                float diffY = currY - mMotionDownY;
//                scrollTo(-(int) diffX, 0);
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                break;
//        }
        return true;
    }

    private int mDraggedX;

    private class DragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return mOrientation == HORIZONTAL ? left : 0;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return mOrientation == VERTICAL ? top : 0;
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            mDraggedX = left;
            changedView.offsetLeftAndRight(dx);
            invalidate();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId) {
            super.onEdgeTouched(edgeFlags, pointerId);
        }

        @Override
        public boolean onEdgeLock(int edgeFlags) {
            return super.onEdgeLock(edgeFlags);
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            super.onEdgeDragStarted(edgeFlags, pointerId);
        }

        @Override
        public int getOrderedChildIndex(int index) {
            return super.getOrderedChildIndex(index);
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

}
