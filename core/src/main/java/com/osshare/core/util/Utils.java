package com.osshare.core.util;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * Created by apple on 16/10/8.
 */
public class Utils {
    public static void changeLanguage(Context context, String language) {

        Resources resources = Resources.getSystem();
        resources = context.getResources();
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
}
