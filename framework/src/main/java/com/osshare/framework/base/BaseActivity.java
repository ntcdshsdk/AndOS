package com.osshare.framework.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.osshare.framework.manager.AppManager;

/**
 * Created by apple on 16/11/1.
 */
public class BaseActivity extends Activity {
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppManager.getInstance().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AppManager.getInstance().finishActivity(this);
    }

    protected void setStatusBarColor() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flag = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if ((params.flags & flag) == 0) {
                params.flags |= flag;
                getWindow().setAttributes(params);
            }
        }
    }

    /**
     * backView onClick事件
     * @param v backView
     */
    public void onBackClick(View v){
        finish();
    }

}
