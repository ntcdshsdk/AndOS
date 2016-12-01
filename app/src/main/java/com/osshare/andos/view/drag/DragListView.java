package com.osshare.andos.view.drag;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by apple on 16/9/18.
 */
public class DragListView extends ListView {
    private static final String TAG = DragListView.class.getSimpleName();

    private int mCurrPosition = AdapterView.INVALID_POSITION;
    private DragLayout mCurrItemView;
    private DragLayout mOldItemView;

    public DragListView(Context context) {
        this(context, null);
    }

    public DragListView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public DragListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    float downX, downY, currX, currY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {

                if (mOldItemView != null
                        && (mOldItemView.getDragState() == ViewDragHelper.STATE_DRAGGING || mOldItemView.isDrag())) {
                    return super.dispatchTouchEvent(event);
                }
                downX = currX = event.getX();
                downY = event.getY();

                int position = pointToPosition((int) downX, (int) downY);
                if (position == AdapterView.INVALID_POSITION) {
                    return super.dispatchTouchEvent(event);
                } else {
                    mCurrPosition = position;
                    mOldItemView = mCurrItemView;
                    mCurrItemView = (DragLayout) getChildAt(mCurrPosition - getFirstVisiblePosition());
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                if (mOldItemView != null && mOldItemView.isDrag()) {
                    mOldItemView.forceReset();
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mCurrItemView.getDragState() == ViewDragHelper.STATE_DRAGGING
                && mCurrPosition != AdapterView.INVALID_POSITION) {
            int action = event.getAction();
            float x = event.getX();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
        }
        return super.onTouchEvent(event);
    }
}
