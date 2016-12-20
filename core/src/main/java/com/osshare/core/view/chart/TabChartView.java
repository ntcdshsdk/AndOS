package com.osshare.core.view.chart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by apple on 16/7/12.
 */
public class TabChartView extends View {

    public interface ITabChartAdapter {
        int getViewColor(int row, int col);

        View getView();
    }

    public TabChartView(Context context) {
        super(context);
    }

    public TabChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void headerTop(boolean fixed) {

    }

    public void headerBottom(boolean fixed) {

    }

    public void headerLeft(boolean fixed) {

    }

    public void headerRight(boolean fixed) {

    }

    public void addTop() {

    }

    public void addBottom() {

    }

    public void addLeft() {

    }

    public void addRight() {

    }
}
