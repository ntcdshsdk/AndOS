package com.osshare.core.view.single;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.osshare.core.R;

/**
 * Created by apple on 16/11/9.
 */
public class MyTestView extends EditText {
    private Drawable mLineDrawable;
//    private View mTargetView;

    public MyTestView(Context context) {
        super(context);
    }

    public MyTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    public void init(AttributeSet attrs) {
        setWillNotDraw(false);
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyTestView);
        mLineDrawable = a.getDrawable(R.styleable.MyTestView_line);
//        int targetViewId = a.getResourceId(R.styleable.MyTestView_target, View.NO_ID);
//        mTargetView = findViewById(targetViewId);

        a.recycle();

        setClickable(true);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mLineDrawable == null) {
            return;
        }
        unscheduleDrawable(mLineDrawable);
        Rect bounds = new Rect(0, getMeasuredHeight() - 2, getMeasuredWidth(), getMeasuredHeight());
        mLineDrawable.setBounds(bounds);
        mLineDrawable.setCallback(this);
        if (mLineDrawable.isStateful()) {
            mLineDrawable.setState(getDrawableState());
        }
        mLineDrawable.draw(canvas);
    }

//    public void setLineDrawable(Drawable drawable){
//        this.mLineDrawable=drawable;
//        invalidate();
//    }

//    public void setTargetView(View targetView) {
//        this.mTargetView = targetView;
////        Rect bounds = new Rect(0, getMeasuredHeight() - 2, getMeasuredWidth(), getMeasuredHeight());
//        invalidate();
//    }

}
