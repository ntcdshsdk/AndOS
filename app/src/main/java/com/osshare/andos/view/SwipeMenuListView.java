package com.osshare.andos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

import com.osshare.andos.util.UIUtil;

/**
 * Created by apple on 16/9/13.
 */
public class SwipeMenuListView extends ListView {

    private static final String TAG = SwipeMenuListView.class.getSimpleName();

    private static final int AUTO_OPEN_VELOCITY = 350;

    /**
     * A view is not currently being dragged or animating as a result of a fling/snap.
     */
    public static final int STATE_IDLE = 0;

    /**
     * A view is currently being dragged. The position is currently changing as a result
     * of user input or simulated user input.
     */
    public static final int STATE_DRAGGING = 1;

    /**
     * A view is currently settling into place as a result of a fling or
     * predefined non-interactive motion.
     */
    public static final int STATE_SETTLING = 2;

    public boolean isOpen = false;
    private int mDragState;
    private int currPosition;
    private View mCurrItemView;
    private int itemRightWidth = (int) UIUtil.dp2px(70);
    private int mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    /**
     * 滑动类
     */
    private Scroller mScroller;

    /**
     * 速度追踪对象
     */
    private VelocityTracker velocityTracker;

    /**
     * 移除item后的回调接口
     */
    private OnItemRightClickListener mItemRightClickListener;


    public SwipeMenuListView(Context context) {
        this(context, null);
    }

    public SwipeMenuListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScroller = new Scroller(getContext(), new Interpolator() {
            public float getInterpolation(float t) {
                t -= 1.0f;
                return t * t * t * t * t + 1.0f;
            }
        });
    }

    /**
     * 设置滑动删除的回调接口
     *
     * @param listener
     */
    public void setOnItemRightClickListener(OnItemRightClickListener listener) {
        this.mItemRightClickListener = listener;
    }


    private float downX, downY, currX, currY;

    /**
     * 分发事件，主要做的是判断点击的是那个item, 以及通过postDelayed来设置响应左右滑动事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

//                if (isOpen) {
//                    return super.dispatchTouchEvent(event);
//                }

                if (mDragState == STATE_DRAGGING) {
                    return super.dispatchTouchEvent(event);
                }

                if (!mScroller.isFinished()) {
                    return super.dispatchTouchEvent(event);
                }

                Log.i(TAG, "downX:" + downX + "  *****  " + "downY:" + downY);
                downX = currX = event.getX();
                downY = event.getY();

                int position = pointToPosition((int) downX, (int) downY);
                if (position == AdapterView.INVALID_POSITION) {
                    return super.dispatchTouchEvent(event);
                } else {
                    currPosition = position;
                    mCurrItemView = getChildAt(currPosition - getFirstVisiblePosition());
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:
                if ((Math.abs(event.getX() - downX) > mTouchSlop
                        && Math.abs(event.getY() - downY) < mTouchSlop)) {
                    setDragState(STATE_DRAGGING);
                }
                break;
            case MotionEvent.ACTION_UP:
//                if (isOpen) {
//                    mCurrItemView.scrollTo(0, 0);
//                }
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 处理我们拖动ListView item的逻辑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if ((mDragState == STATE_DRAGGING && currPosition != AdapterView.INVALID_POSITION) || isOpen) {
            int action = event.getAction();
            float x = event.getX();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    float touchDeltaX = downX - x;
                    float deltaX = currX - x;
                    currX = x;
                    Log.i(TAG, "touchDeltaX:" + touchDeltaX + " *" + itemRightWidth + "* " + "deltaX:" + deltaX);
                    if (mCurrItemView != null && Math.abs(touchDeltaX) < itemRightWidth) {
                        setDragState(STATE_DRAGGING);
//                        mCurrItemView.scrollBy((int) deltaX, 0);
                        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), (int) deltaX, 0);
                        invalidate();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    released();
                    break;
            }
            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            final int x = mScroller.getCurrX();
            final int y = mScroller.getCurrY();
            mCurrItemView.scrollTo(x, y);
            mCurrItemView.postInvalidate();
        } else {
            setDragState(STATE_IDLE);
        }
        super.computeScroll();
    }

    public void released() {
        setDragState(STATE_SETTLING);
        int sCurrX = mScroller.getCurrX();
        int finalLeft;
        if (isOpen || sCurrX > -itemRightWidth / 2) {
            finalLeft = 0;
        } else {
            finalLeft = -itemRightWidth;
        }
        final int startLeft = mCurrItemView.getLeft();
        final int startTop = mCurrItemView.getTop();
        final int dx = finalLeft - startLeft;
        if (dx == 0) {
            mScroller.abortAnimation();
            setDragState(STATE_IDLE);
            return;
        }
        mScroller.startScroll(startLeft, startTop, dx, 0);
        currPosition = AdapterView.INVALID_POSITION;
    }

    public void setDragState(int state) {
        if (mDragState != state) {
            mDragState = state;
            if (mDragState == STATE_IDLE) {
//                mCurrItemView = null;
            }
        }
        Log.i(TAG, "mDragState:" + mDragState);
    }

    public interface OnItemRightClickListener {
        void itemRightClick(AdapterView<?> parent, int position);
    }
}
