package com.osshare.andos.view.toast;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.osshare.andos.R;

/**
 * Created by apple on 16/10/6.
 */
public class XToast {
    private Context mContext;
    private View mView;
    private ViewGroup mViewGroup;
    private ViewGroup mViewRoot;
    private GradientDrawable mToastBackgound;
    private LayoutInflater mInflater;
    private TextView mTextView;
    private String message;
    private AnimatorSet mShowAnimatorSet;
    private AnimatorSet mHideAnimatorSet;
    private int mShowAnimationType;
    private int mHideAnimationType;
    private int mDuration;
    private int mBackgroundColor;
    private OnDisappearListener mOnDisappearListener;

    public static final int XTOAST_LONG = 3500;
    public static final int XTOAST_SHORT = 2000;

    public interface OnDisappearListener{
        void onDisappear(XToast xToast);
    }

    public XToast(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public XToast(Context context,String message)
    {
        this.mContext = context;
        this.message = message;
        this.mInflater = LayoutInflater.from(context);
    }

    public static XToast create(Context context){
        return new XToast(context);
    }

    public static XToast create(Context context,String message){
        return new XToast(context,message);
    }

    //省略一系列的set和get方法
    //...
    public boolean isShowing() {
        return mView != null && mView.isShown();
    }

    private void initViews() {
        mViewRoot = (ViewGroup) ((Activity)mContext).findViewById(android.R.id.content);
        mViewGroup = new LinearLayout(mContext);
        FrameLayout.LayoutParams mViewGroupParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mViewGroupParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
        mViewGroupParams.bottomMargin = 200;
        mViewGroup.setLayoutParams(mViewGroupParams);
        mViewRoot.addView(mViewGroup);

        //如果用户没有使用自己的View，那么使用默认的mView
        if(mView == null){
//            mView = mInflater.inflate(R.layout.xtoast_normal,mViewGroup,false);
            mToastBackgound = (GradientDrawable) mView.getBackground();
//            mTextView = (TextView) mView.findViewById(R.id.message);
            mTextView.setText(message);
            if(mBackgroundColor != 0){
                mToastBackgound.setColor(mBackgroundColor);
            }
        }

        //对mView的大小进行测量
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1<<30) -1, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((1<<30) -1,View.MeasureSpec.AT_MOST);
        mView.measure(widthMeasureSpec,heightMeasureSpec);

    }

    public void show(){

        //准备工作
        initViews();
        if(this.mShowAnimationType == 0)
            this.mShowAnimatorSet = AnimationUtils.getShowAnimation(this,AnimationUtils.ANIMATION_DEFAULT);
        else
            this.mShowAnimatorSet = AnimationUtils.getShowAnimation(this,mShowAnimationType);
//        if(this.mHideAnimationType == 0)
//            this.mHideAnimatorSet = AnimationUtils.getHideAnimation(this, AnimationUtils.ANIMATION_DEFAULT);
//        else
//            this.mHideAnimatorSet = AnimationUtils.getHideAnimation(this,mHideAnimationType);

        if(mDuration == 0)
            mDuration = XTOAST_SHORT;

        XToastHandler xToastHandler = XToastHandler.getInstance();
        xToastHandler.add(this);
    }
}
