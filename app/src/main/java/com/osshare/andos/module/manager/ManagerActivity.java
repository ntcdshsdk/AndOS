package com.osshare.andos.module.manager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.osshare.andos.R;
import com.osshare.andos.module.manager.bean.WealthInvestment;
import com.osshare.andos.util.UIUtil;
import com.osshare.core.view.recycler.DividerItemDecoration;
import com.osshare.framework.base.BaseActivity;
import com.osshare.framework.base.BaseAdapter;
import com.osshare.framework.base.BaseViewHolder;
import com.osshare.framework.util.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/12/11.
 */
public class ManagerActivity extends BaseActivity {
    private RecyclerView rvContent;
    private BaseAdapter<WealthInvestment> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        ((TextView) findViewById(R.id.tv_title)).setText("后台管理");

        List<WealthInvestment> data = new ArrayList<>();
        WealthInvestment itemBean = new WealthInvestment();
        itemBean.setTitle("用户后台管理");
        itemBean.setDetail("系统用户专享");
        itemBean.setImg("");
        WealthInvestment itemBean1 = new WealthInvestment();
        itemBean1.setTitle("期货后台管理");
        itemBean1.setDetail("系统用户专享");
        itemBean1.setImg("");
        WealthInvestment itemBean2 = new WealthInvestment();
        itemBean2.setTitle("股票后台管理");
        itemBean2.setDetail("系统用户专享");
        itemBean2.setImg("");

        data.add(itemBean);
        data.add(itemBean1);
        data.add(itemBean2);

        for (int i = 0; i < 50; i++) {
            WealthInvestment itemBeanx = new WealthInvestment();
            itemBeanx.setTitle("股票后台管理");
            itemBeanx.setDetail("系统用户专享");
            itemBeanx.setImg("");
            data.add(itemBeanx);
        }

        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        int dlr = (int) UIUtil.dp2px(12);
        rvContent.addItemDecoration(new DividerItemDecoration.Builder().color(Color.LTGRAY)
                .height((int) UIUtil.dp2px(0.7f)).offsetLeft(dlr).offsetRight(dlr).build());
        rvContent.setLayoutManager(new LinearLayoutManager(ManagerActivity.this));
        adapter = new BaseAdapter<WealthInvestment>(ManagerActivity.this, data) {
            @Override
            public View getItemView(ViewGroup parent, int viewType) {
                return inflater.inflate(R.layout.layout_item_manager, parent, false);
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                WealthInvestment itemBean = getItem(position);
                ImageView ivImg = holder.getView(R.id.iv_img);
                TextView tvType = holder.getView(R.id.tv_type);
                TextView tvDesc = holder.getView(R.id.tv_desc);

                ImageLoader.loadImage(ManagerActivity.this, itemBean.getTitle(), ivImg);
                tvType.setText(itemBean.getTitle());
                tvDesc.setText(itemBean.getDetail());
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseViewHolder holder) {
                WealthInvestment itemBean = adapter.getItem(holder.getLayoutPosition());
                switch (itemBean.getTitle()) {
                    case "用户后台管理":
                        startActivity(new Intent(ManagerActivity.this, UserManagerActivity.class));
                        break;
                    case "期货后台管理":
                        startActivity(new Intent(ManagerActivity.this, FuturesManagerActivity.class));
                        break;
                    case "股票后台管理":
                        startActivity(new Intent(ManagerActivity.this, StockManagerActivity.class));
                        break;
                }
            }
        });
        rvContent.setAdapter(adapter);
    }


}
