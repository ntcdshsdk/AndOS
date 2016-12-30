package com.osshare.andos.module.finance;

import android.os.Bundle;

import com.osshare.andos.R;
import com.osshare.andos.base.abs.AbsActivity;
import com.osshare.core.view.chart.ChartOption;
import com.osshare.framework.base.BaseActivity;

/**
 * Created by apple on 16/12/9.
 */
public class FinanceChartActivity extends AbsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_chart);
        immersiveHeaderContainer(R.id.layout_title_bar);

    }
}
