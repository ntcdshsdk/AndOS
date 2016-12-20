package com.osshare.andos.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.osshare.andos.R;
import com.osshare.andos.base.BaseFragment;
import com.osshare.framework.base.BaseAdapter;
import com.osshare.framework.base.BaseViewHolder;

/**
 * Created by apple on 16/11/17.
 */
public class ProfileFragment extends BaseFragment {
    private RecyclerView rvContent;
    private BaseAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_profile, container, false);

        rvContent = (RecyclerView) view.findViewById(R.id.rv_content);
        adapter = new BaseAdapter<String>(getActivity(), null) {
            @Override
            public View getItemView(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {

            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseViewHolder holder) {

            }
        });
        rvContent.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
