package com.osshare.core.view.recycler;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by apple on 16/12/14.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private int height;
    private int left;
    private int right;
    private int color;

    public DividerItemDecoration(int height, int color) {
        super();
        mPaint = new Paint();
        mPaint.setColor(color);
        this.height = height;
    }

    public DividerItemDecoration(int height, int color, int left, int right) {
        super();
        mPaint = new Paint();
        mPaint.setColor(color);
        this.height = height;
        this.left = left;
        this.right = right;
    }

    private DividerItemDecoration(Builder builder) {
        this.height = builder.height;
        this.left = builder.left;
        this.right = builder.right;
        this.color = builder.color;
        mPaint = new Paint();
        mPaint.setColor(color);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft() + this.left;
        int right = parent.getWidth() - parent.getPaddingRight() - this.right;
        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + height;
            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.left = left;
//        outRect.right = right;
        outRect.bottom = height;
    }


    public static final class Builder {
        private int color;
        private int left;
        private int right;
        private int height;

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        public Builder offsetLeft(int left) {
            this.left = left;
            return this;
        }

        public Builder offsetRight(int right) {
            this.right = right;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public DividerItemDecoration build() {
            return new DividerItemDecoration(this);
        }
    }

}
