package com.osshare.andos.activity;

import android.os.Bundle;

import com.osshare.andos.R;
import com.osshare.andos.base.abs.AbsActivity;
import com.osshare.framework.base.BaseActivity;

/**
 * Created by apple on 16/12/9.
 */
public class NoticeActivity extends AbsActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        immersiveHeaderContainer(R.id.layout_title_bar);

    }
}
