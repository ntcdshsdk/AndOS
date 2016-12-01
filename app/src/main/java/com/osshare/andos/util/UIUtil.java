package com.osshare.andos.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.osshare.framework.manager.Constant;

/**
 * Created by apple on 16/9/14.
 */
public class UIUtil {

    /**
     * 将value的unit转换为px
     *
     * @param unit  TypedValue
     * @param value unit值
     * @return px
     */
    public static float unit2px(Context context,int unit, float value) {
        return TypedValue.applyDimension(unit, value, getDisplayMetrics(context));
    }

    /**
     * @param unit  TypedValue
     * @param value px值
     * @return unit
     */
    public static float px2unit(Context context,int unit, float value) {
        return px2unit(unit, value, getDisplayMetrics(context));
    }

    private static float px2unit(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case TypedValue.COMPLEX_UNIT_PX:
                return value;
            case TypedValue.COMPLEX_UNIT_DIP:
                return value / metrics.density;
            case TypedValue.COMPLEX_UNIT_SP:
                return value / metrics.scaledDensity;
            case TypedValue.COMPLEX_UNIT_PT:
                return value / metrics.xdpi / (1.0f / 72);
            case TypedValue.COMPLEX_UNIT_IN:
                return value * metrics.xdpi;
            case TypedValue.COMPLEX_UNIT_MM:
                return value / metrics.xdpi / (1.0f / 25.4f);
        }
        return 0;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        if (Constant.METRICS == null) {
            Constant.METRICS = context.getResources().getDisplayMetrics();
        }
        return Constant.METRICS;
    }
}
