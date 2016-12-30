package com.osshare.andos.module.profiles;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.osshare.andos.R;
import com.osshare.andos.base.abs.AbsActivity;
import com.osshare.framework.base.BaseActivity;
import com.osshare.framework.base.BaseAdapter;
import com.osshare.framework.base.BaseViewHolder;

/**
 * Created by apple on 16/10/18.
 */
public class ProfilesActivity extends AbsActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        immersiveHeaderContainer(R.id.layout_title_bar);



    }
}
