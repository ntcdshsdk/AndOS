package com.osshare.andos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import com.osshare.andos.bean.User;
import com.osshare.andos.module.login.LoginActivity;
import com.osshare.andos.manager.AndOsApplication;
import com.osshare.framework.base.BaseActivity;

public class SplashActivity extends BaseActivity {

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

    }

    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * 2);
                    AndOsApplication.getInstance().initUser();
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

                User user = AndOsApplication.getInstance().getUser();
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
