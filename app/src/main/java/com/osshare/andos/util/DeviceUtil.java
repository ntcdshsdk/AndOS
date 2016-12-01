package com.osshare.andos.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by apple on 16/9/2.
 */
public class DeviceUtil {

    public static boolean isNotificationEnabled(Context context) {
        String methodName = "checkOpNoThrow";
        String fieldName = "OP_POST_NOTIFICATION";
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();

        try {
            Class appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(methodName, Integer.TYPE, Integer.TYPE, String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(fieldName);
            int value = (int) opPostNotificationValue.get(Integer.class);
            return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, appInfo.uid, context.getPackageName()) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void openSetting(Context context, String action) {
        Intent intent;
        switch (action) {
            case Settings.ACTION_APPLICATION_DETAILS_SETTINGS:
                intent = new Intent(action, Uri.parse("package:" + context.getPackageName()));
                break;
            default:
                intent = new Intent(action);
                break;
        }
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
        context.startActivity(intent);
    }


    public static boolean isAppStartUp(Context context, Intent intent) {
        List<ResolveInfo> list = context.getPackageManager()
                .queryIntentActivities(intent, 0);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }

    public static void getRunningTaskInfos(Context context, String tag) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfos = am.getRunningTasks(30);
        for (ActivityManager.RunningTaskInfo taskInfo : taskInfos) {
            ComponentName baseComponentName = taskInfo.baseActivity;
            ComponentName topComponentName = taskInfo.topActivity;
            Log.i(tag, "[baseName:" + baseComponentName.toString() + "]    [topName:" + topComponentName.toString() + "]");
        }
    }

    /**
     * 5.0以后已不支持获取其它包名的进程和TASK信息
     *
     * @param context
     * @return
     */
    public static boolean isAppRunningBackground(Context context) {
        int importance = -1;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(context.getPackageName())) {
                    importance = appProcess.importance;
                    break;
                }
            }
        }

        if (importance != -1 && importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                //可能是无界面APP
                || importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
            List<ActivityManager.RunningTaskInfo> taskInfos = activityManager.getRunningTasks(50);
            for(ActivityManager.RunningTaskInfo taskInfo:taskInfos){
                if(taskInfo.baseActivity.getPackageName().equals(context.getPackageName())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 5.0以后已不支持获取其它包名的进程和TASK信息
     *
     * @param context
     * @return
     */
    public static boolean isAppRunningForeground(Context context) {
        int importance = -1;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(context.getPackageName())) {
                    importance = appProcess.importance;
                    break;
                }
            }
        }

        if (importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                //可能是无界面APP
                || importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
            List<ActivityManager.RunningTaskInfo> taskInfos = activityManager.getRunningTasks(50);
            String forePackage = taskInfos.get(0).baseActivity.getPackageName();
            return forePackage.equals(context.getPackageName());
        }
        return false;
    }

    /**
     * 5.0以后已不支持获取其它包名的进程和TASK信息
     *
     * @param context
     * @param cls
     * @return
     */
    public static boolean isBaseActivity(Context context, Class<? extends Activity> cls) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfos = activityManager.getRunningTasks(50);
        for (ActivityManager.RunningTaskInfo taskInfo : taskInfos) {
            String className = taskInfo.baseActivity.getClassName();
            if (className.equals(cls.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 5.0以后已不支持获取其它包名的进程和TASK信息
     *
     * @param context
     * @param cls
     * @return
     */
    public static boolean isTopActivity(Context context, Class<? extends Activity> cls) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfos = activityManager.getRunningTasks(50);
        for (ActivityManager.RunningTaskInfo taskInfo : taskInfos) {
            String className = taskInfo.topActivity.getClassName();
            if (className.equals(cls.getName())) {
                return true;
            }
        }
        return false;
    }

    public static ComponentName getTopComponentName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfos = activityManager.getRunningTasks(50);
        for (ActivityManager.RunningTaskInfo taskInfo : taskInfos) {
            ComponentName componentName = taskInfo.topActivity;
            if (componentName.getPackageName().equals(context.getPackageName())) {
                return componentName;
            }
        }
        return null;
    }

    public static void moveTaskToFront(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfos = activityManager.getRunningTasks(50);
        for (ActivityManager.RunningTaskInfo taskInfo : taskInfos) {
            ComponentName componentName = taskInfo.topActivity;
            if (componentName.getPackageName().equals(context.getPackageName())) {
                activityManager.moveTaskToFront(taskInfo.id, 0);
            }
        }
    }
}
