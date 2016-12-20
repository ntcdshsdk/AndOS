package com.osshare.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by apple on 16/11/9.
 */
public class RotaryLayout extends ViewGroup {
    private float mRadius;
    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);

    public RotaryLayout(Context context) {
        this(context, null);
    }

    public RotaryLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotaryLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        final boolean measureMatchParentChildren =
                MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY ||
                        MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        mMatchParentChildren.clear();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren(l, t, r, b);
    }

    void layoutChildren(int left, int top, int right, int bottom) {

    }


}
