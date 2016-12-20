package com.osshare.andos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.osshare.andos.R;
import com.osshare.andos.TestActivity;
import com.osshare.andos.activity.ImageSelectActivity;
import com.osshare.andos.module.ecb.ECBActivity;
import com.osshare.andos.activity.IjkPlayerActivity;
import com.osshare.andos.module.manager.ManagerActivity;
import com.osshare.andos.module.news.NewsActivity;
import com.osshare.andos.base.BaseFragment;
import com.osshare.framework.base.BaseAdapter;
import com.osshare.framework.base.BaseViewHolder;

import java.util.Arrays;

/**
 * Created by apple on 16/11/17.
 */
public class HomeFragment extends BaseFragment {
    private RecyclerView rvContent;
    private BaseAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        rvContent = (RecyclerView) view.findViewById(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BaseAdapter<String>(getActivity(), Arrays.asList(
                new String[]{"news","ecb","video","manager"})) {
            @Override
            public View getItemView(ViewGroup parent, int viewType) {
                return inflater.inflate(R.layout.layout_item_main_home, parent, false);
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                String itemBean = getItem(position);
                TextView tvModule = holder.getView(R.id.tv_module);
                tvModule.setText(itemBean);
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseViewHolder holder) {
                String itemBean = adapter.getItem(holder.getLayoutPosition());
                switch (itemBean){
                    case "news":
                        startActivity(new Intent(getActivity(),NewsActivity.class));
                        break;
                    case "ecb":
                        startActivity(new Intent(getActivity(),ECBActivity.class));
                        break;
                    case "video":
                        startActivity(new Intent(getActivity(),TestActivity.class));
                        break;
                    case "manager":
                        startActivity(new Intent(getActivity(),ManagerActivity.class));
                        break;
                }
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
