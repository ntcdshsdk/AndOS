package com.osshare.core.view.recycler;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;

/**
 * Created by apple on 16/12/15.
 */
public class TableLayoutManager extends RecyclerLayoutManager {
    private SparseIntArray colMaxWidth = new SparseIntArray();
    private SparseIntArray rowMaxHeight = new SparseIntArray();

    public TableLayoutManager() {
        super();
    }

    @Override
    public void setMeasuredDimension(Rect childrenBounds, int wSpec, int hSpec) {
        super.setMeasuredDimension(childrenBounds, wSpec, hSpec);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
    }

    @Override
    public void assertInLayoutOrScroll(String message) {
        super.assertInLayoutOrScroll(message);
    }

    @Override
    public void assertNotInLayoutOrScroll(String message) {
        super.assertNotInLayoutOrScroll(message);
    }

    @Override
    public void setAutoMeasureEnabled(boolean enabled) {
        super.setAutoMeasureEnabled(enabled);
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return super.isAutoMeasureEnabled();
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return super.supportsPredictiveItemAnimations();
    }

    @Override
    public boolean isAttachedToWindow() {
        return super.isAttachedToWindow();
    }

    @Override
    public void postOnAnimation(Runnable action) {
        super.postOnAnimation(action);
    }

    @Override
    public boolean removeCallbacks(Runnable action) {
        return super.removeCallbacks(action);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
    }

    @Override
    public boolean getClipToPadding() {
        return super.getClipToPadding();
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
//            cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        if (!state.isPreLayout()) {
//            mPendingSpanCountChange = false;
        }
//        clearPreLayoutSpanMappingCache();
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return super.checkLayoutParams(lp);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return super.generateLayoutParams(lp);
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return super.generateLayoutParams(c, attrs);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        super.smoothScrollToPosition(recyclerView, state, position);
    }

    @Override
    public void startSmoothScroll(RecyclerView.SmoothScroller smoothScroller) {
        super.startSmoothScroll(smoothScroller);
    }

    @Override
    public boolean isSmoothScrolling() {
        return super.isSmoothScrolling();
    }

    @Override
    public int getLayoutDirection() {
        return super.getLayoutDirection();
    }

    @Override
    public void endAnimation(View view) {
        super.endAnimation(view);
    }

    @Override
    public void addDisappearingView(View child) {
        super.addDisappearingView(child);
    }

    @Override
    public void addDisappearingView(View child, int index) {
        super.addDisappearingView(child, index);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        super.addView(child, index);
    }

    @Override
    public void removeView(View child) {
        super.removeView(child);
    }

    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
    }

    @Override
    public int getBaseline() {
        return super.getBaseline();
    }

    @Override
    public int getPosition(View view) {
        return super.getPosition(view);
    }

    @Override
    public int getItemViewType(View view) {
        return super.getItemViewType(view);
    }

    @Nullable
    @Override
    public View findContainingItemView(View view) {
        return super.findContainingItemView(view);
    }

    @Override
    public View findViewByPosition(int position) {
        return super.findViewByPosition(position);
    }

    @Override
    public void detachView(View child) {
        super.detachView(child);
    }

    @Override
    public void detachViewAt(int index) {
        super.detachViewAt(index);
    }

    @Override
    public void attachView(View child, int index, RecyclerView.LayoutParams lp) {
        super.attachView(child, index, lp);
    }

    @Override
    public void attachView(View child, int index) {
        super.attachView(child, index);
    }

    @Override
    public void attachView(View child) {
        super.attachView(child);
    }

    @Override
    public void removeDetachedView(View child) {
        super.removeDetachedView(child);
    }

    @Override
    public void moveView(int fromIndex, int toIndex) {
        super.moveView(fromIndex, toIndex);
    }

    @Override
    public void detachAndScrapView(View child, RecyclerView.Recycler recycler) {
        super.detachAndScrapView(child, recycler);
    }

    @Override
    public void detachAndScrapViewAt(int index, RecyclerView.Recycler recycler) {
        super.detachAndScrapViewAt(index, recycler);
    }

    @Override
    public void removeAndRecycleView(View child, RecyclerView.Recycler recycler) {
        super.removeAndRecycleView(child, recycler);
    }

    @Override
    public void removeAndRecycleViewAt(int index, RecyclerView.Recycler recycler) {
        super.removeAndRecycleViewAt(index, recycler);
    }

    @Override
    public int getChildCount() {
        return super.getChildCount();
    }

    @Override
    public View getChildAt(int index) {
        return super.getChildAt(index);
    }

    @Override
    public int getWidthMode() {
        return super.getWidthMode();
    }

    @Override
    public int getHeightMode() {
        return super.getHeightMode();
    }

    @Override
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    public int getPaddingLeft() {
        return super.getPaddingLeft();
    }

    @Override
    public int getPaddingTop() {
        return super.getPaddingTop();
    }

    @Override
    public int getPaddingRight() {
        return super.getPaddingRight();
    }

    @Override
    public int getPaddingBottom() {
        return super.getPaddingBottom();
    }

    @Override
    public int getPaddingStart() {
        return super.getPaddingStart();
    }

    @Override
    public int getPaddingEnd() {
        return super.getPaddingEnd();
    }

    @Override
    public boolean isFocused() {
        return super.isFocused();
    }

    @Override
    public boolean hasFocus() {
        return super.hasFocus();
    }

    @Override
    public View getFocusedChild() {
        return super.getFocusedChild();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    public void offsetChildrenHorizontal(int dx) {
        super.offsetChildrenHorizontal(dx);
    }

    @Override
    public void offsetChildrenVertical(int dy) {
        super.offsetChildrenVertical(dy);
    }

    @Override
    public void ignoreView(View view) {
        super.ignoreView(view);
    }

    @Override
    public void stopIgnoringView(View view) {
        super.stopIgnoringView(view);
    }

    @Override
    public void detachAndScrapAttachedViews(RecyclerView.Recycler recycler) {
        super.detachAndScrapAttachedViews(recycler);
    }

    @Override
    public void measureChild(View child, int widthUsed, int heightUsed) {
        super.measureChild(child, widthUsed, heightUsed);
    }

    @Override
    public boolean isMeasurementCacheEnabled() {
        return super.isMeasurementCacheEnabled();
    }

    @Override
    public void setMeasurementCacheEnabled(boolean measurementCacheEnabled) {
        super.setMeasurementCacheEnabled(measurementCacheEnabled);
    }

    @Override
    public void measureChildWithMargins(View child, int widthUsed, int heightUsed) {
        super.measureChildWithMargins(child, widthUsed, heightUsed);
    }

    @Override
    public int getDecoratedMeasuredWidth(View child) {
        return super.getDecoratedMeasuredWidth(child);
    }

    @Override
    public int getDecoratedMeasuredHeight(View child) {
        return super.getDecoratedMeasuredHeight(child);
    }

    @Override
    public void layoutDecorated(View child, int left, int top, int right, int bottom) {
        super.layoutDecorated(child, left, top, right, bottom);
    }

    @Override
    public int getDecoratedLeft(View child) {
        return super.getDecoratedLeft(child);
    }

    @Override
    public int getDecoratedTop(View child) {
        return super.getDecoratedTop(child);
    }

    @Override
    public int getDecoratedRight(View child) {
        return super.getDecoratedRight(child);
    }

    @Override
    public int getDecoratedBottom(View child) {
        return super.getDecoratedBottom(child);
    }

    @Override
    public void calculateItemDecorationsForChild(View child, Rect outRect) {
        super.calculateItemDecorationsForChild(child, outRect);
    }

    @Override
    public int getTopDecorationHeight(View child) {
        return super.getTopDecorationHeight(child);
    }

    @Override
    public int getBottomDecorationHeight(View child) {
        return super.getBottomDecorationHeight(child);
    }

    @Override
    public int getLeftDecorationWidth(View child) {
        return super.getLeftDecorationWidth(child);
    }

    @Override
    public int getRightDecorationWidth(View child) {
        return super.getRightDecorationWidth(child);
    }

    @Nullable
    @Override
    public View onFocusSearchFailed(View focused, int direction, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.onFocusSearchFailed(focused, direction, recycler, state);
    }

    @Override
    public View onInterceptFocusSearch(View focused, int direction) {
        return super.onInterceptFocusSearch(focused, direction);
    }

    @Override
    public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate) {
        return super.requestChildRectangleOnScreen(parent, child, rect, immediate);
    }

    @Override
    public boolean onRequestChildFocus(RecyclerView parent, RecyclerView.State state, View child, View focused) {
        return super.onRequestChildFocus(parent, state, child, focused);
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        super.onAdapterChanged(oldAdapter, newAdapter);
    }

    @Override
    public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> views, int direction, int focusableMode) {
        return super.onAddFocusables(recyclerView, views, direction, focusableMode);
    }

    @Override
    public void onItemsChanged(RecyclerView recyclerView) {
        super.onItemsChanged(recyclerView);
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
        super.onItemsAdded(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) {
        super.onItemsRemoved(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount) {
        super.onItemsUpdated(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount, Object payload) {
        super.onItemsUpdated(recyclerView, positionStart, itemCount, payload);
    }

    @Override
    public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) {
        super.onItemsMoved(recyclerView, from, to, itemCount);
    }

    @Override
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return super.computeHorizontalScrollExtent(state);
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return super.computeHorizontalScrollOffset(state);
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return super.computeHorizontalScrollRange(state);
    }

    @Override
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return super.computeVerticalScrollExtent(state);
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return super.computeVerticalScrollOffset(state);
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return super.computeVerticalScrollRange(state);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    @Override
    public void setMeasuredDimension(int widthSize, int heightSize) {
        super.setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    public int getMinimumWidth() {
        return super.getMinimumWidth();
    }

    @Override
    public int getMinimumHeight() {
        return super.getMinimumHeight();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
    }

    @Override
    public void removeAndRecycleAllViews(RecyclerView.Recycler recycler) {
        super.removeAndRecycleAllViews(recycler);
    }

    @Override
    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(recycler, state, info);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
    }

    @Override
    public void onInitializeAccessibilityEvent(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(recycler, state, event);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfoForItem(recycler, state, host, info);
    }

    @Override
    public void requestSimpleAnimationsInNextLayout() {
        super.requestSimpleAnimationsInNextLayout();
    }

    @Override
    public int getSelectionModeForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.getSelectionModeForAccessibility(recycler, state);
    }

    @Override
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.getRowCountForAccessibility(recycler, state);
    }

    @Override
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.getColumnCountForAccessibility(recycler, state);
    }

    @Override
    public boolean isLayoutHierarchical(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.isLayoutHierarchical(recycler, state);
    }

    @Override
    public boolean performAccessibilityAction(RecyclerView.Recycler recycler, RecyclerView.State state, int action, Bundle args) {
        return super.performAccessibilityAction(recycler, state, action, args);
    }

    @Override
    public boolean performAccessibilityActionForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, int action, Bundle args) {
        return super.performAccessibilityActionForItem(recycler, state, view, action, args);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }


    public static class LayoutParams extends RecyclerView.LayoutParams {

        /**
         * Span Id for Views that are not laid out yet.
         */
        public static final int INVALID_SPAN_ID = -1;

        private int mSpanIndex = INVALID_SPAN_ID;

        private int mSpanSize = 0;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(RecyclerView.LayoutParams source) {
            super(source);
        }

        /**
         * Returns the current span index of this View. If the View is not laid out yet, the return
         * value is <code>undefined</code>.
         * <p/>
         * Note that span index may change by whether the RecyclerView is RTL or not. For
         * example, if the number of spans is 3 and layout is RTL, the rightmost item will have
         * span index of 2. If the layout changes back to LTR, span index for this view will be 0.
         * If the item was occupying 2 spans, span indices would be 1 and 0 respectively.
         * <p/>
         * If the View occupies multiple spans, span with the minimum index is returned.
         *
         * @return The span index of the View.
         */
        public int getSpanIndex() {
            return mSpanIndex;
        }

        /**
         * Returns the number of spans occupied by this View. If the View not laid out yet, the
         * return value is <code>undefined</code>.
         *
         * @return The number of spans occupied by this View.
         */
        public int getSpanSize() {
            return mSpanSize;
        }
    }
}
