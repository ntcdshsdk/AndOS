package com.osshare.andos.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.osshare.andos.R;
import com.osshare.framework.util.ImageLoader;

import java.util.List;

/**
 * Created by apple on 16/12/2.
 */
public class BannerView extends FrameLayout {
    private int delayMillis = 1000;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private OnItemClickListener itemClickListener;
    private LinearLayout indicatorLayout;

    private ImageView[] mIndicator;

    private Handler mHandler = new Handler();

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        viewPager = new ViewPager(getContext());
        addView(viewPager);
    }


    public void setDelayTime(int delayMillis) {
        this.delayMillis = delayMillis;
    }


//    private final Runnable autoTask = new Runnable() {
//        @Override
//        public void run() {
//            viewPager.setCurrentItem(currentItem);
//            mHandler.postDelayed(autoTask, delayMillis);
//            currentItem += 1;
//        }
//    };

    public void setAdapter(PagerAdapter adapter) {
        this.adapter = adapter;
        viewPager.setAdapter(adapter);

        initIndicator();
    }

    private void initIndicator() {
        indicatorLayout = new LinearLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 15);
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        indicatorLayout.setLayoutParams(layoutParams);
        indicatorLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        addView(indicatorLayout);
        mIndicator = new ImageView[adapter.getCount()];
        for (int i = 0; i < mIndicator.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(12, 12);
            params.setMargins(6, 0, 6, 0);
            ImageView imageView = new ImageView(getContext());
            mIndicator[i] = imageView;
            if (i == viewPager.getCurrentItem()) {
                mIndicator[i].setBackgroundResource(R.drawable.jfs);
            } else {
                mIndicator[i].setBackgroundResource(R.drawable.dyq);
            }
            indicatorLayout.addView(imageView, params);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mIndicator.length; i++) {
                    ImageView iv = mIndicator[i];
                    iv.setImageResource(position == viewPager.getCurrentItem() ? R.drawable.jfs : R.drawable.dyq);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                mHandler.removeCallbacks(autoTask);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
//                mHandler.postDelayed(autoTask, delayMillis*2);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
//            mHandler.postDelayed(autoTask, delayMillis);
        } else {
//            mHandler.removeCallbacks(autoTask);
        }
    }


    interface OnItemClickListener {
        void onItemClick(int position);
    }

    interface BannerBean {
        String getImageUrl();
    }


}



