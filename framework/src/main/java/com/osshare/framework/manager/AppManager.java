package com.osshare.framework.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * Created by apple on 16/7/12.
 */
public class AppManager {
    private static AppManager mInstance;

    private static Class<? extends Activity> sourceCls;

    private static Stack<Activity> activityStack = new Stack<>();

    private AppManager() {

    }

    public static AppManager getInstance() {
        if (mInstance == null) {
            synchronized (AppManager.class) {
                if (mInstance == null) {
                    mInstance = new AppManager();
                }
            }
        }
        return mInstance;
    }

    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    public void finishActivity() {
        finishActivity(topActivity());
    }

    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            activity.finish();
        }
        activityStack.clear();
    }

    public Activity topActivity() {
        Activity activity = null;
        if (activityStack.size() > 0) {
            activity = activityStack.lastElement();
        }
        return activity;
    }

    public void appExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
