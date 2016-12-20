package com.osshare.andos.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.osshare.andos.manager.ImKkApplication;
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
    public static float unit2px(int unit, float value) {
        return TypedValue.applyDimension(unit, value, getDisplayMetrics(ImKkApplication.getInstance()));
    }

    /**
     * 将value的dp转换为px
     *
     * @param value unit值
     * @return px
     */
    public static float dp2px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getDisplayMetrics(ImKkApplication.getInstance()));
    }

    /**
     * 将value的sp转换为px
     *
     * @param value unit值
     * @return px
     */
    public static float sp2px(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getDisplayMetrics(ImKkApplication.getInstance()));
    }

    /**
     * @param unit  TypedValue
     * @param value px值
     * @return unit
     */
    public static float px2unit(int unit, float value) {
        return px2unit(unit, value, getDisplayMetrics(ImKkApplication.getInstance()));
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

    private static DisplayMetrics getDisplayMetrics(Context context) {
        if (Constant.METRICS == null) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            if (metrics == null) {
                metrics = Resources.getSystem().getDisplayMetrics();
            }
            Constant.METRICS = metrics;
        }
        return Constant.METRICS;
    }
}
