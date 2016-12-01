package com.osshare.andos.view.pull;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by apple on 16/7/12.
 */
public class PullableLayout extends ViewGroup {

    private static final int PULL_STATE_INIT = 0X01;
    private static final int PULL_STATE_REDEAD = 0X01;
    private static final int PULL_STATE_REFRESHING = 0X02;


    private int pullState = PULL_STATE_INIT;

    private View lView;
    private View rView;
    private View tView;
    private View bView;

    private View targetView;

    public PullableLayout(Context context) {
        super(context);
    }

    public PullableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

//            SwipeRefreshLayout

    }


    private void reset() {
        pullState = PULL_STATE_INIT;
    }


    interface PullListener {
        void onPullLeft(View v);

        void onPullUp(View v);

        void onPullRight(View v);

        void onPullDown(View v);
    }

}
