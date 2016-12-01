package com.osshare.andos.view.single;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by apple on 16/10/26.
 */
public class RateTextCircularProgressBar extends LinearLayout {
    private XStampProgressBar progressBar;
    private TextView mRateText;

    public RateTextCircularProgressBar(Context context) {
        super(context);
        init();
    }

    public RateTextCircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        progressBar = new XStampProgressBar(getContext());
        this.addView(progressBar);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.gravity = Gravity.CENTER;
        progressBar.setLayoutParams(lp);

        mRateText = new TextView(getContext());
        this.addView(mRateText);
        mRateText.setLayoutParams(lp);
        mRateText.setGravity(Gravity.CENTER);
        mRateText.setTextColor(Color.BLACK);
        mRateText.setTextSize(20);

    }


    /**
     * 设置进度
     * @param progress
     */
    public void setProgress(int progress) {
        progressBar.setProgress(progress);
    }

    /**
     * 得到 CircularProgressBar 对象，用来设置其他的一些属性
     * @return
     */
    public XStampProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * 设置中间进度百分比文字的尺寸
     * @param size
     */
    public void setTextSize(float size) {
        mRateText.setTextSize(size);
    }

    /**
     * 设置中间进度百分比文字的颜色
     * @param color
     */
    public void setTextColor( int color) {
        mRateText.setTextColor(color);
    }

    public void setText(String text){
        mRateText.setText(Html.fromHtml(text));//"10月12日<br>12:00<br>开售"
    }

}
