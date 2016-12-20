package com.osshare.core.view.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.osshare.core.util.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 16/7/12.
 */
public class PieChartView extends View {
    private String TAG = PieChartView.class.getSimpleName();
    private static final int VERTICAL = 0;
    private static final int HORIZONTAL = 1;

    private int mBuildSdk;
    private RadialGradient mPieShadowGradient;
    private int mControlHalfHeight;

    private RectF oval;
    private int mTextWidth;
    private int mTextListHeight;
    private int mTextListMarginTop;
    private int mPieMarginLeft;
    private int mLumpMarginLeft;
    private int mTextMarginLeft;


    private Map<String, Integer> mNVData;
    private List<Integer> mColors;
    private int mTotalVal;

    /**
     * 画笔
     */
    private Paint mPaint;

    private int ORIENTATION;
    /**
     * 饼图的半径
     */
    private int mPieRadius;

    /**
     * 阴影宽度
     */
    public float mShadowWidth = 20;

    /**
     * 文字颜色
     */
    private int mTextColor = Color.parseColor("#666666");

    /**
     * 文字大小
     */
    private int mTextSize;

    /**
     * 描述区域各部分文字之间的间隔
     */
    private int mTextSpacing;
    /**
     * 饼图和描述区域的间隔
     */
    private int mPieLumpSpacing;

    /**
     * 色块和文字之间的间隔
     */
    private int mLumpTextSpacing;

    /**
     * 描述区色块高度
     */
    private int mLumpLength;

    /**
     * 描述区域色块的圆角半径
     */
    private int mLumpRadius;

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mBuildSdk = Build.VERSION.SDK_INT;

        init();
    }

    public void init() {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        mTextSize = (int) Utils.unit2px(TypedValue.COMPLEX_UNIT_SP, 13, metrics);
        mTextSpacing = (int) Utils.unit2px(TypedValue.COMPLEX_UNIT_DIP, 12, metrics);
        mPieLumpSpacing = (int) Utils.unit2px(TypedValue.COMPLEX_UNIT_DIP, 30, metrics);
        mLumpTextSpacing = (int) Utils.unit2px(TypedValue.COMPLEX_UNIT_DIP, 6, metrics);
        mLumpLength = (int) Utils.unit2px(TypedValue.COMPLEX_UNIT_DIP, 12, metrics);
        mLumpRadius = (int) Utils.unit2px(TypedValue.COMPLEX_UNIT_DIP, 2, metrics);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "w" + w + "h" + h + "oldw" + oldw + "oldh" + oldh);
        int maxLimit = Math.min(w, h);


        mControlHalfHeight = h / 2;
        mPieRadius = mControlHalfHeight;

        //内容宽度：饼图直径+ 间隔+ 颜色色块宽度 + 文字宽度
        int contentWidth = mPieRadius * 2 + mPieLumpSpacing + mLumpLength + mLumpTextSpacing + mTextWidth;
        int contentMarginLeft = (w - contentWidth) / 2;


        mPieMarginLeft = contentMarginLeft + mPieRadius;
        mLumpMarginLeft = mPieMarginLeft + mPieRadius + mPieLumpSpacing;
        mTextMarginLeft = mLumpMarginLeft + mLumpLength + mLumpTextSpacing;
        mTextListMarginTop = (h - mTextListHeight) / 2;

        mPieShadowGradient = new RadialGradient(mPieMarginLeft, mControlHalfHeight, mPieRadius + mShadowWidth, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        oval = new RectF(contentMarginLeft, mControlHalfHeight - mPieRadius, contentMarginLeft + mPieRadius * 2, mControlHalfHeight + mPieRadius);
    }

    /**
     * bind data
     *
     * @param nvData the data will bind with name and value
     */
    public void bindValues(LinkedHashMap<String, Integer> nvData, List<Integer> colors) {
        mNVData = nvData;
        mColors = colors;
        String maxString = "";
        int value = 0;

        for (Map.Entry<String, Integer> entry : nvData.entrySet()) {
            if (entry.getKey().length() > maxString.length()) {
                maxString = entry.getKey();
            }
            value += entry.getValue();
        }

        mTotalVal = value;
        int size = nvData.size();
        mTextWidth = (int) mPaint.measureText(maxString);
        mTextListHeight = mTextSize * size + (mTextSpacing * (size - 1));
        postInvalidate();
    }


    /**
     * bind data
     *
     * @param nvData the data will bind with name and value
     */
    public void bindValues(LinkedHashMap<String, Integer> nvData) {
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.MAGENTA);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        bindValues(nvData, colors);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setShader(null);
        float starAngle = -90;
        int colorSize = mColors.size();
        int textMarginTop;

        int index = 0;
        for (Map.Entry<String, Integer> entry : mNVData.entrySet()) {
            int color = mColors.get(index % colorSize);
            textMarginTop = mTextListMarginTop + (index * mTextSize);
            if (index > 0) {
                textMarginTop += index * mTextSpacing;
            }
            //画扇形
            mPaint.setColor(color);
            mPaint.setStyle(Paint.Style.FILL);
            float angle = 360f * entry.getValue() / mTotalVal;
            canvas.drawArc(oval, starAngle, angle, true, mPaint);
            starAngle += angle;
            //画色块
            RectF rect = new RectF(mLumpMarginLeft, textMarginTop, mLumpMarginLeft + mLumpLength, textMarginTop + mLumpLength);
            if (mBuildSdk >= Build.VERSION_CODES.LOLLIPOP)
                canvas.drawRoundRect(rect, mLumpRadius, mLumpRadius, mPaint);
            else {
                canvas.drawRect(rect, mPaint);
            }
            //画文字
            mPaint.setColor(mTextColor);
            canvas.drawText(entry.getKey(), mTextMarginLeft, textMarginTop + mTextSize * 0.8f, mPaint);
            index++;
        }

        //画阴影
        mPaint.setShader(mPieShadowGradient);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mShadowWidth);
        canvas.drawCircle(mPieMarginLeft, mControlHalfHeight, mPieRadius + mShadowWidth / 2, mPaint);
    }


}
