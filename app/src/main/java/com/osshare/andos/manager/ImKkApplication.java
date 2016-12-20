package com.osshare.andos.manager;

import android.content.Context;

import com.osshare.andos.bean.User;
import com.osshare.framework.manager.AppApplication;
import com.osshare.framework.manager.Constant;

/**
 * Created by apple on 16/7/12.
 */
public class ImKkApplication extends AppApplication {
    private static ImKkApplication instance;

    public User user;

    public static ImKkApplication getInstance() {
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
    }

    /**
     * 获取登录信息（缓存）
     */
    public void initUser() {

    }


    public User getUser() {
        return user;
    }
}
