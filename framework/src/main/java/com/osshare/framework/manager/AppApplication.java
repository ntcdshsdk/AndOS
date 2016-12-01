package com.osshare.framework.manager;

import android.app.Application;
import android.content.Context;

/**
 * Created by apple on 16/7/12.
 */
public class AppApplication extends Application {
    private static AppApplication instance;

    public static AppApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        instance = this;
    }

    /**
     * 初始化
     */
    private void init() {
        initConstant();

        initUser();
    }

    /**
     * 初始化Constant
     */
    public void initConstant() {
        Constant.METRICS = getResources().getDisplayMetrics();
        Constant.PACKAGE_NAME = getPackageName();
        Constant.BASE_URL_DEBUG="https://api.github.com";
        Constant.BASE_URL_RELEASE="https://api.github.com";
    }

    /**
     * 获取登录信息（缓存）
     */
    public void initUser() {

    }

}
