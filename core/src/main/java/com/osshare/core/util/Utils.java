package com.osshare.core.util;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by apple on 16/10/8.
 */
public class Utils {
    public static void changeLanguage(Context context, String language) {

//        Resources resources = Resources.getSystem();
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        if (TextUtils.isEmpty(language)) {
            language = "en";
        }

        try {
            String[] parts = language.split("-");
            String lowerCaseLanguageCode = parts[0];
            String upperCaseCountryCode = "";
            if (parts.length > 1) {
                upperCaseCountryCode = parts[1].substring(1);
            }
            Constructor<Locale> constructor = Locale.class.getDeclaredConstructor(boolean.class, String.class, String.class);
            constructor.setAccessible(true);
            config.locale = constructor.newInstance(true, lowerCaseLanguageCode, upperCaseCountryCode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        resources.updateConfiguration(config, metrics);
    }

    public static boolean isNotificationEnabled(Context context) {
        //19以下没有AppOpsManager，不支持
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.KITKAT){
            return true;
        }
        String methodName = "checkOpNoThrow";
        String fieldName = "OP_POST_NOTIFICATION";

        try {
            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = context.getApplicationContext().getPackageName();
            int uid = appInfo.uid;

            Class appOpsClass = AppOpsManager.class;
            Method checkOpNoThrowMethod = appOpsClass.getMethod(methodName, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(fieldName);
            int value = (int) opPostNotificationValue.get(Integer.class);
            return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 将value的unit转换为px
     *
     * @param unit  TypedValue
     * @param value unit值
     * @return px
     */
    public static float unit2px(int unit, float value,DisplayMetrics metrics) {
        return TypedValue.applyDimension(unit, value, metrics);
    }

    /**
     * 将value的px转换为unit
     * @param unit
     * @param value
     * @param metrics
     * @return
     */
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
}
