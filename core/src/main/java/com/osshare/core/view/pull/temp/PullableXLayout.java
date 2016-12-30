package com.osshare.core.view.pull.temp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

public class PullableXLayout extends ViewGroup {
    private static final String TAG = PullableXLayout.class.getSimpleName();

    private static final long RETURN_TO_ORIGINAL_POSITION_TIMEOUT = 300;
    private static final float ACCELERATE_INTERPOLATION_FACTOR = 1.5f;
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2f;
    private static final int INVALID_POINTER = -1;


    private static final int PULL_STATE_RESET = 0;
    private static final int STATE_PULLING_DOWN = 1;
    private static final int STATE_PULLING_UP = 2;
    private static final float DRAG_RATE = .5f;
    private int mPullState = 0;
    //临界值即：release 后会执行刷新的下拉或上拉高度
    private float tippingHeight;

    private PullProgressBar mPullDownProgressBar;
    private PullProgressBar mPullUpProgressBar;
    private View mTarget;
    private OnPullListener mPullListener;
    private boolean mPullingDown = false;
    private boolean mPullingUp = false;


    private SwipeRefreshLayout refreshLayout;
    private int mOriginalOffsetTop;
    private int mFrom;
    private int mTouchSlop;
    private int mMediumAnimationDuration;
    private float mFromPercentage = 0;
    private float mCurrPercentage = 0;
    private int mCurrentTargetOffsetTop;

    private float mInitialMotionY;
    private float mLastMotionY;
    private boolean mIsBeingDragged;
    private int mActivePointerId = INVALID_POINTER;

    // Target is returning to its start offset because it was cancelled or a
    // refresh was triggered.
    private boolean mReturningToStart;
    private final DecelerateInterpolator mDecelerateInterpolator;
    private final AccelerateInterpolator mAccelerateInterpolator;
    private static final int[] LAYOUT_ATTRS = new int[]{
            android.R.attr.enabled
    };
    private Mode mMode = Mode.getDefault();
    //之前手势的方向，为了解决同一个触点前后移动方向不同导致后一个方向会刷新的问题，
    //这里Mode.DISABLED无意义，只是一个初始值，和上拉/下拉方向进行区分
    private Mode mLastDirection = Mode.DISABLED;
    private int mDirection = 0;
    //当子控件移动到尽头时才开始计算初始点的位置
    private float mStartPoint;


    //对下拉或上拉进行复位
    private final Animation mAnimateToStartPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            int targetTop = 0;
            if (mFrom != mOriginalOffsetTop) {
                targetTop = (mFrom + (int) ((mOriginalOffsetTop - mFrom) * interpolatedTime));
            }
            int offset = targetTop - mTarget.getTop();
            setTargetOffsetTopAndBottom(offset);
        }
    };

    //设置上方进度条的完成度百分比
    private Animation mShrinkTrigger = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            float percent = mFromPercentage + ((0 - mFromPercentage) * interpolatedTime);
            mPullDownProgressBar.setTriggerPercentage(percent);
        }
    };

    //设置下方进度条的完成度百分比
    private Animation mShrinkTriggerBottom = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            float percent = mFromPercentage + ((0 - mFromPercentage) * interpolatedTime);
            mPullUpProgressBar.setTriggerPercentage(percent);
        }
    };

    //监听，回复初始位置
    private final AnimationListener mReturnToStartPositionListener = new BaseAnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            // Once the target content has returned to its start position, reset
            // the target offset to 0
            mCurrentTargetOffsetTop = 0;
            mLastDirection = Mode.DISABLED;
        }
    };

    //回复进度条百分比
    private final AnimationListener mShrinkAnimationListener = new BaseAnimationListener() {
        @Override
        public void onAnimationEnd(Animation animation) {
            mCurrPercentage = 0;
        }
    };

    //回复初始位置
    private final Runnable mReturnToStartPosition = new Runnable() {

        @Override
        public void run() {
            mReturningToStart = true;
            animateOffsetToStartPosition(mCurrentTargetOffsetTop + getPaddingTop(),
                    mReturnToStartPositionListener);
        }

    };

    // Cancel the refresh gesture and animate everything back to its original state.
    private final Runnable mCancel = new Runnable() {

        @Override
        public void run() {
            mReturningToStart = true;
            if (mPullDownProgressBar != null || mPullUpProgressBar != null) {
                mFromPercentage = mCurrPercentage;
                if (mDirection > 0 && ((mMode == Mode.PULL_DOWN) || (mMode == Mode.BOTH))) {
                    mShrinkTrigger.reset();
                    mShrinkTrigger.setDuration(mMediumAnimationDuration);
                    mShrinkTrigger.setAnimationListener(mShrinkAnimationListener);

                    mShrinkTrigger.setInterpolator(mDecelerateInterpolator);
                    startAnimation(mShrinkTrigger);
                } else if (mDirection < 0 && ((mMode == Mode.PULL_UP) || (mMode == Mode.BOTH))) {
                    mShrinkTriggerBottom.reset();
                    mShrinkTriggerBottom.setDuration(mMediumAnimationDuration);
                    mShrinkTriggerBottom.setAnimationListener(mShrinkAnimationListener);

                    mShrinkTriggerBottom.setInterpolator(mDecelerateInterpolator);
                    startAnimation(mShrinkTriggerBottom);
                }
            }
            mDirection = 0;
            animateOffsetToStartPosition(mCurrentTargetOffsetTop + getPaddingTop(),
                    mReturnToStartPositionListener);
        }

    };


    public PullableXLayout(Context context) {
        this(context, null);
    }

    public PullableXLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMediumAnimationDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);

        setWillNotDraw(false);

        final TypedArray a = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
        setEnabled(a.getBoolean(0, true));
        a.recycle();

        tippingHeight = 75 * getDisplayMetrics(context).density;


        mPullDownProgressBar = new PullProgressBar(this);
        mPullDownProgressBar.setColorScheme(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));

        mPullUpProgressBar = new PullProgressBar(this);
        mPullUpProgressBar.setColorScheme(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));

        mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
        mAccelerateInterpolator = new AccelerateInterpolator(ACCELERATE_INTERPOLATION_FACTOR);


    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks(mCancel);
        removeCallbacks(mReturnToStartPosition);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mReturnToStartPosition);
        removeCallbacks(mCancel);
    }

    //对子控件进行移动
    private void animateOffsetToStartPosition(int from, AnimationListener listener) {
        mFrom = from;
        mAnimateToStartPosition.reset();
        mAnimateToStartPosition.setDuration(mMediumAnimationDuration);
        mAnimateToStartPosition.setAnimationListener(listener);
        mAnimateToStartPosition.setInterpolator(mDecelerateInterpolator);
        mTarget.startAnimation(mAnimateToStartPosition);
    }


    //设置进度条的显示百分比
    private void setTriggerPercentage(float percent) {
        if (percent == 0f) {
            // No-op. A null trigger means it's uninitialized, and setting it to zero-percent
            // means we're trying to reset state, so there's nothing to reset in this case.
            mCurrPercentage = 0;
            return;
        }
        mCurrPercentage = percent;
        if (((mMode == Mode.PULL_DOWN) || (mMode == Mode.BOTH))
                && mLastDirection != Mode.PULL_UP && !mPullingUp) {
            mPullDownProgressBar.setTriggerPercentage(percent);
        } else if (((mMode == Mode.PULL_UP) || (mMode == Mode.BOTH))
                && mLastDirection != Mode.PULL_DOWN && !mPullingDown) {
            mPullUpProgressBar.setTriggerPercentage(percent);
        }
    }

    public void setPullingDown(boolean pullingDown) {
        if (mPullingDown != pullingDown) {
            ensureTarget();
            mCurrPercentage = 0;
            mPullingDown = pullingDown;
            if (mPullingDown) {
                mPullDownProgressBar.start();
            } else {
                mLastDirection = Mode.DISABLED;
                mPullDownProgressBar.stop();
            }
        }
    }

    public void setPullingUp(boolean pullingUp) {
        if (mPullingUp != pullingUp) {
            ensureTarget();
            mCurrPercentage = 0;
            mPullingUp = pullingUp;
            if (mPullingUp) {
                mPullUpProgressBar.start();
            } else {
                mLastDirection = Mode.DISABLED;
                mPullUpProgressBar.stop();
            }
        }
    }


    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid out yet.
        if (mTarget == null) {
            if (getChildCount() > 1 && !isInEditMode()) {
                throw new IllegalStateException(
                        "SwipeRefreshLayout can host only one direct child");
            }
            mTarget = getChildAt(0);
            mOriginalOffsetTop = mTarget.getTop() + getPaddingTop();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mPullDownProgressBar.draw(canvas);
        mPullUpProgressBar.draw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        mPullDownProgressBar.setBounds(0, 0, width, (int) tippingHeight);
        if (getChildCount() == 0) {
            return;
        }
        final View child = getChildAt(0);
        final int childLeft = getPaddingLeft();
        final int childTop = mCurrentTargetOffsetTop + getPaddingTop();
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        mPullUpProgressBar.setBounds(0, (int) (height - tippingHeight), width, height);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 1 && !isInEditMode()) {
            throw new IllegalStateException("SwipeRefreshLayout can host only one direct child");
        }
        if (getChildCount() > 0) {
            getChildAt(0).measure(
                    MeasureSpec.makeMeasureSpec(
                            getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                            MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(
                            getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
                            MeasureSpec.EXACTLY));
        }
    }

    public boolean canChildScrollDown() {
        return ViewCompat.canScrollVertically(mTarget, -1);
    }

    public boolean canChildScrollUp() {
        return ViewCompat.canScrollVertically(mTarget, 1);
    }

    private boolean canChildScrollUp;
    private boolean canChildScrollDown;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ensureTarget();

        final int action = MotionEventCompat.getActionMasked(ev);

        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }

        if (!isEnabled() || mReturningToStart) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = mInitialMotionY = ev.getY();
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;
                mCurrPercentage = 0;
                mStartPoint = mInitialMotionY;

                canChildScrollUp = canChildScrollDown();
                canChildScrollDown = canChildScrollUp();
                break;

            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }

                final float y = MotionEventCompat.getY(ev, pointerIndex);
//                final float yDiff = y - mInitialMotionY;
                final float yDiff = y - mStartPoint;
                //若上个手势的方向和当前手势方向不一致，返回
                if ((mLastDirection == Mode.PULL_DOWN && yDiff < 0)
                        || (mLastDirection == Mode.PULL_UP && yDiff > 0)) {
                    return false;
                }
                //下拉或上拉时，子控件本身能够滑动时，记录当前手指位置，当其滑动到尽头时，
                //mStartPoint作为下拉刷新或上拉加载的手势起点
                if ((canChildScrollDown() && yDiff > 0) || (canChildScrollUp() && yDiff < 0)) {
                    mStartPoint = y;
                }

                //下拉
                if (yDiff > mTouchSlop) {
                    //若当前子控件能向下滑动，或者上个手势为上拉，则返回
                    if (canChildScrollDown() || mLastDirection == Mode.PULL_UP) {
                        mIsBeingDragged = false;
                        return false;
                    }
                    if ((mMode == Mode.PULL_DOWN) || (mMode == Mode.BOTH)) {
                        mLastMotionY = y;
                        mIsBeingDragged = true;
                        mLastDirection = Mode.PULL_DOWN;
                    }
                }
                //上拉
                else if (-yDiff > mTouchSlop) {
                    //若当前子控件能向上滑动，或者上个手势为下拉，则返回
                    if (canChildScrollUp() || mLastDirection == Mode.PULL_DOWN) {
                        mIsBeingDragged = false;
                        return false;
                    }
                    //若子控件不能上下滑动，说明数据不足一屏，若不满屏不加载，返回
                    if (!canChildScrollUp && !canChildScrollDown) {
                        mIsBeingDragged = false;
                        return false;
                    }
                    if ((mMode == Mode.PULL_UP) || (mMode == Mode.BOTH)) {
                        mLastMotionY = y;
                        mIsBeingDragged = true;
                        mLastDirection = Mode.PULL_UP;
                    }
                }
                break;

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mCurrPercentage = 0;
                mActivePointerId = INVALID_POINTER;
                mLastDirection = Mode.DISABLED;
                break;
        }

        return mIsBeingDragged;
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        // Nope.
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);

        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }

        if (!isEnabled() || mReturningToStart) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                mIsBeingDragged = false;

                mLastMotionY = mInitialMotionY = ev.getY();
                mCurrPercentage = 0;
                mStartPoint = mInitialMotionY;
                break;

            case MotionEvent.ACTION_MOVE:
                final int pointerIndex = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndex < 0) {
                    Log.e(TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }

                final float y = MotionEventCompat.getY(ev, pointerIndex);
                final float yDiff = (y - mStartPoint) * DRAG_RATE;

                if ((mLastDirection == Mode.PULL_DOWN && yDiff < 0) ||
                        (mLastDirection == Mode.PULL_UP && yDiff > 0)) {
                    return true;
                }

                if (!mIsBeingDragged && (yDiff > 0 && mLastDirection == Mode.PULL_DOWN)
                        || (yDiff < 0 && mLastDirection == Mode.PULL_UP)) {
                    mIsBeingDragged = true;
                }
                if (mIsBeingDragged) {
                    if (!canChildScrollUp && !canChildScrollDown && yDiff < 0) {
                        return true;
                    }
                    // Just track the user's movement
                    //根据手指移动距离设置进度条显示的百分比
                    setTriggerPercentage(
                            mAccelerateInterpolator.getInterpolation(
                                    Math.min(Math.abs(yDiff) / tippingHeight, 1f)
                            ));
                    updateContentOffsetTop((int) yDiff);
                    if (mTarget.getTop() == getPaddingTop()) {
                        // If the user puts the view back at the top, we
                        // don't need to. This shouldn't be considered
                        // cancelling the gesture as the user can restart from the top.
                        removeCallbacks(mCancel);
                        mLastDirection = Mode.DISABLED;
                    } else {
                        mDirection = (yDiff > 0 ? 1 : -1);
                        updatePositionTimeout();
                    }
                    mLastMotionY = y;
                }
                break;

            case MotionEventCompat.ACTION_POINTER_DOWN: {
                final int index = MotionEventCompat.getActionIndex(ev);
                mLastMotionY = MotionEventCompat.getY(ev, index);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;

            case MotionEvent.ACTION_UP:
                final int pointerIndexx = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                if (pointerIndexx < 0) {
                    Log.e(TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return false;
                }

                final float yx = MotionEventCompat.getY(ev, pointerIndexx);
//                final float yDiff = y - mInitialMotionY;
                final float yDiffx = (yx - mStartPoint) * DRAG_RATE;

                if ((mLastDirection == Mode.PULL_DOWN && yDiffx < 0) ||
                        (mLastDirection == Mode.PULL_UP && yDiffx > 0)) {
                    return true;
                }

                if (!mIsBeingDragged && (yDiffx > 0 && mLastDirection == Mode.PULL_DOWN)
                        || (yDiffx < 0 && mLastDirection == Mode.PULL_UP)) {
                    mIsBeingDragged = true;
                }
                if (mIsBeingDragged) {
                    // User velocity passed min velocity; trigger a refresh
                    if (yDiffx > tippingHeight) {
                        // User movement passed distance; trigger a refresh
                        if (mLastDirection == Mode.PULL_UP) {
                            return true;

                        }
                        if ((mMode == Mode.PULL_DOWN) || (mMode == Mode.BOTH)) {
                            mLastDirection = Mode.PULL_DOWN;
                            startPullDown();
                        }
                    } else if (-yDiffx > tippingHeight) {
                        if ((!canChildScrollUp && !canChildScrollDown) || mLastDirection == Mode.PULL_DOWN) {
                            return true;
                        }
                        if ((mMode == Mode.PULL_UP) || (mMode == Mode.BOTH)) {
                            mLastDirection = Mode.PULL_UP;
                            startPullUp();
                        }
                    } else {
                        if (!canChildScrollUp && !canChildScrollDown && yDiffx < 0) {
                            return true;
                        }
                        // Just track the user's movement
                        //根据手指移动距离设置进度条显示的百分比
                        setTriggerPercentage(
                                mAccelerateInterpolator.getInterpolation(
                                        Math.min(Math.abs(yDiffx) / tippingHeight, 1f)));
                        updateContentOffsetTop((int) yDiffx);
                        if (mTarget.getTop() == getPaddingTop()) {
                            // If the user puts the view back at the top, we
                            // don't need to. This shouldn't be considered
                            // cancelling the gesture as the user can restart from the top.
                            removeCallbacks(mCancel);
                            mLastDirection = Mode.DISABLED;
                        } else {
                            mDirection = (yDiffx > 0 ? 1 : -1);
                            updatePositionTimeout();
                        }
                    }
                    mLastMotionY = yx;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mCurrPercentage = 0;
                mActivePointerId = INVALID_POINTER;
                mLastDirection = Mode.DISABLED;
                return false;
        }

        return true;
    }

    public void setOnPullListener(OnPullListener listener) {
        this.mPullListener = listener;
    }

    private void startPullDown() {
        if (!mPullingUp && !mPullingDown) {
            removeCallbacks(mCancel);
            mReturnToStartPosition.run();
            setPullingDown(true);
            if (mPullListener != null) {
                mPullListener.onPullDown();
            }
        }
    }

    private void startPullUp() {
        if (!mPullingUp && !mPullingDown) {
            removeCallbacks(mCancel);
            mReturnToStartPosition.run();
            setPullingUp(true);
            if (mPullListener != null) {
                mPullListener.onPullUp();
            }
        }
    }

    //手指移动时更新子控件的位置
    private void updateContentOffsetTop(int targetTop) {
        final int currentTop = mTarget.getTop();
        setTargetOffsetTopAndBottom(targetTop - currentTop);
    }

    //根据偏移量对子控件进行移动
    private void setTargetOffsetTopAndBottom(int offset) {
        mTarget.offsetTopAndBottom(offset);
        mCurrentTargetOffsetTop = mTarget.getTop();
    }

    private void updatePositionTimeout() {
        removeCallbacks(mCancel);
        postDelayed(mCancel, RETURN_TO_ORIGINAL_POSITION_TIMEOUT);
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

    public interface OnPullListener {
        void onPullDown();

        void onPullUp();
    }

    public void setMode(Mode mode) {
        this.mMode = mode;
    }

    public static enum Mode {
        /**
         * Disable all Pull-to-Refresh gesture and Refreshing handling
         */
        DISABLED(0x0),

        /**
         * Only allow the user to Pull from the start of the Refreshable View to
         * refresh. The start is either the Top or Left, depending on the
         * scrolling direction.
         */
        PULL_DOWN(0x1),

        /**
         * Only allow the user to Pull from the end of the Refreshable View to
         * refresh. The start is either the Bottom or Right, depending on the
         * scrolling direction.
         */
        PULL_UP(0x2),

        /**
         * Allow the user to both Pull from the start, from the end to refresh.
         */
        BOTH(0x3);

        static Mode getDefault() {
            return BOTH;
        }

        boolean permitsPullToRefresh() {
            return !(this == DISABLED);
        }

        boolean permitsPullFromStart() {
            return (this == Mode.BOTH || this == Mode.PULL_DOWN);
        }

        boolean permitsPullFromEnd() {
            return (this == Mode.BOTH || this == Mode.PULL_UP);
        }

        private int mIntValue;

        // The modeInt values need to match those from attrs.xml
        Mode(int modeInt) {
            mIntValue = modeInt;
        }

        int getIntValue() {
            return mIntValue;
        }

    }

    /**
     * Simple AnimationListener to avoid having to implement unneeded methods in
     * AnimationListeners.
     */
    private class BaseAnimationListener implements AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics;
    }
}
