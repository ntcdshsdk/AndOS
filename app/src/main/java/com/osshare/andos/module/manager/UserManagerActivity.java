package com.osshare.andos.module.manager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.osshare.andos.R;
import com.osshare.andos.bean.User;
import com.osshare.andos.module.manager.bean.WealthInvestment;
import com.osshare.framework.base.BaseActivity;
import com.osshare.framework.base.BaseAdapter;
import com.osshare.framework.base.BaseViewHolder;
import com.osshare.framework.util.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/12/11.
 */
public class UserManagerActivity extends BaseActivity {

    private RecyclerView rvContent;
    private BaseAdapter<User> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        ((TextView) findViewById(R.id.tv_title)).setText("用户管理");

        List<User> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            data.add(user);
        }
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(UserManagerActivity.this));
        adapter = new BaseAdapter<User>(UserManagerActivity.this, data) {
            @Override
            public View getItemView(ViewGroup parent, int viewType) {
                return inflater.inflate(R.layout.layout_item_friend, parent, false);
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                User itemBean = getItem(position);
                ImageView ivAvatar = holder.getView(R.id.iv_avatar);
                TextView tvNickname = holder.getView(R.id.tv_nickname);
                TextView tvFeature = holder.getView(R.id.tv_feature);
                TextView tvLastMsg = holder.getView(R.id.tv_last_msg);


            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseViewHolder holder) {
                User itemBean = adapter.getItem(holder.getLayoutPosition());

            }
        });
        rvContent.setAdapter(adapter);
    }

}
