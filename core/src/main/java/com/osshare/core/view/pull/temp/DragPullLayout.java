package com.osshare.core.view.pull.temp;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by apple on 16/12/8.
 */
public class DragPullLayout extends ViewGroup {
    private final String TAG = DragPullLayout.class.getSimpleName();
    private static final float DRAG_RATE = .5f;
    private int mTouchSlop;
    private float mTippingHeight;
    private OnPullListener mPullListener;
    private View mTarget;
    private ViewDragHelper mDragHelper;

    public DragPullLayout(Context context) {
        this(context, null);
    }

    public DragPullLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragPullLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        setWillNotDraw(false);

        mTippingHeight = 65 * getDisplayMetrics(context).density;
        init();
    }

    public void init() {
        mDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mTarget;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return super.clampViewPositionHorizontal(child, left, dx);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
//                mTarget.offsetTopAndBottom(dy);
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                if (ViewCompat.canScrollVertically(mTarget, 1)) {
                    return 0;
                } else {
                    return (int) mTippingHeight;
                }
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
//                if (releasedChild == mTarget) {
//                    mDragHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
//                    invalidate();
//                }
            }
        });
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

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mDragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
//            ViewCompat.postInvalidateOnAnimation(this);
            invalidate();
        }
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
