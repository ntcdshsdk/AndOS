package com.osshare.andos;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.osshare.andos.fragment.HomeFragment;
import com.osshare.andos.fragment.OtherFragment;
import com.osshare.andos.fragment.ProfileFragment;
import com.osshare.core.view.BottomBarRadioGroup;
import com.osshare.framework.base.BaseActivity;

/**
 * Created by apple on 16/10/13.
 */
public class MainActivity extends BaseActivity {
    private static final String TAG_HOME_FRAGMENT = "home";
    private static final String TAG_OTHER_FRAGMENT = "other";
    private static final String TAG_PROFILE_FRAGMENT = "profile";

//    private Button btnFluctuate;
//    private FluctuateImageView ivAvatar;

    private String[] tags = new String[]{TAG_HOME_FRAGMENT, TAG_OTHER_FRAGMENT, TAG_PROFILE_FRAGMENT};
    private BottomBarRadioGroup radioGroup;
    private TextView tvTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        radioGroup = (BottomBarRadioGroup) findViewById(R.id.nrg_bottom_bar);
        radioGroup.setOnCheckedChangeListener(checkedChangeListener);

        if (savedInstanceState == null) {
            ((CompoundButton)radioGroup.findViewById(R.id.rb_home)).setChecked(true);
        }

    }


    private BottomBarRadioGroup.OnCheckedChangeListener checkedChangeListener = new BottomBarRadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(BottomBarRadioGroup group, int checkedId) {
            CompoundButton button = (CompoundButton) group.findViewById(checkedId);
            switch (checkedId) {
                case R.id.rb_home:
                    if (button.isChecked()) {
                        switchFragment(TAG_HOME_FRAGMENT);
                    }
                    tvTitle.setText("首页");
                    break;
                case R.id.rb_other:
                    if (button.isChecked()) {
                        switchFragment(TAG_OTHER_FRAGMENT);
                    }
                    tvTitle.setText("预留");
                    break;
                case R.id.rb_profile:
                    if (button.isChecked()) {
                        switchFragment(TAG_PROFILE_FRAGMENT);
                    }
                    tvTitle.setText("我的");
                    break;
            }
        }
    };


    public void switchFragment(String currTag) {
        boolean isCreate = true;
        Fragment fragment = null;

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        for (String tag : tags) {
            Fragment frg = manager.findFragmentByTag(tag);
            if (tag.equals(currTag)) {
                fragment = frg;
                continue;
            }
            if (frg != null) {
                transaction.hide(frg);
            }
        }
        if (fragment == null) {
            isCreate = false;
            switch (currTag) {
                case TAG_HOME_FRAGMENT:
                    fragment = new HomeFragment();
                    break;
                case TAG_OTHER_FRAGMENT:
                    fragment = new OtherFragment();
                    break;
                case TAG_PROFILE_FRAGMENT:
                    fragment = new ProfileFragment();
                    break;
            }
        }
        if (isCreate) {
            transaction.show(fragment);
        } else {
            transaction.add(R.id.fl_container, fragment, currTag);
        }
        transaction.commit();
    }
}
