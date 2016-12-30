package com.osshare.andos.base.abs;


import android.os.Bundle;

import com.osshare.andos.R;
import com.osshare.framework.base.BaseActivity;

/**
 * Created by apple on 16/10/14.
 */
public abstract class AbsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        immersiveHeaderContainer(R.id.layout_title_bar);


    }


}
