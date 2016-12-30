package com.osshare.andos;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.osshare.andos.base.abs.AbsActivity;
import com.osshare.andos.bean.User;
import com.osshare.andos.module.login.LoginActivity;
import com.osshare.andos.manager.ImKkApplication;
import com.osshare.framework.base.BaseActivity;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class SplashActivity extends AbsActivity {

    private static final int INIT_END = 0X000016;
    private static final int ANIMATION_END = 0X000017;
    private static final int MASK = 0X000017;

    private int status = MASK;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ANIMATION_END:
                    status = status & ANIMATION_END;
                    onAnimationAndInitEnd();
                    break;
                case INIT_END:
                    status = status & INIT_END;
                    onAnimationAndInitEnd();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        init();
        animation();
        View view;



    }

    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 2);
                    ImKkApplication.getInstance().initUser();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.sendMessage(mHandler.obtainMessage(INIT_END));
            }
        }).start();
    }

    public void animation() {
        mHandler.sendMessage(mHandler.obtainMessage(ANIMATION_END));
    }

    public void onAnimationAndInitEnd() {
        Log.i(TAG, status + "");
        if (status == (ANIMATION_END & INIT_END)) {
            synchronized (SplashActivity.class) {
                status = MASK;

                User user = ImKkApplication.getInstance().getUser();
//                if (user != null && TextUtils.isEmpty(user.getPassword())) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.putExtra("User", user);
                    startActivity(intent);
//                } else {
//                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                }
                SplashActivity.this.finish();
            }
        }

    }


}
