package com.osshare.andos.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osshare.andos.R;
import com.osshare.andos.base.BaseFragment;

/**
 * Created by apple on 16/11/17.
 */
public class ProfileFragment extends BaseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main_profile,container,false);
        return view;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }
}
