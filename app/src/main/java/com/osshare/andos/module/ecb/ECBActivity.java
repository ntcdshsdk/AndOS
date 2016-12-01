package com.osshare.andos.module.ecb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.osshare.andos.R;
import com.osshare.andos.activity.IjkPlayerActivity;
import com.osshare.andos.activity.ImageSelectActivity;
import com.osshare.andos.module.news.NewsActivity;
import com.osshare.framework.base.BaseActivity;
import com.osshare.framework.base.BaseAdapter;
import com.osshare.framework.base.BaseViewHolder;

import java.util.Arrays;

/**
 * Created by apple on 16/11/27.
 */
public class ECBActivity extends BaseActivity {
    private RecyclerView rvContent;
    private BaseAdapter<String> adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecb);

        ((TextView) findViewById(R.id.tv_title)).setText("ECB");//R.string.module_ecb

        TextView tvTest= (TextView) findViewById(R.id.tv_test);
        tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"onClick",Toast.LENGTH_SHORT).show();
            }
        });


        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(ECBActivity.this));
        adapter = new BaseAdapter<String>(ECBActivity.this, Arrays.asList(new String[]{"news","ecb","video","other","ecb","video","other","ecb","video"
                ,"other","ecb","video","other","ecb","video","other","ecb","video","other"})) {
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
        adapter.setItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseViewHolder holder) {
                String itemBean = adapter.getItem(holder.getLayoutPosition());
                switch (itemBean){
                    case "news":
                        startActivity(new Intent(ECBActivity.this,NewsActivity.class));
                        break;
                    case "ecb":
                        startActivity(new Intent(ECBActivity.this,ECBActivity.class));
                        break;
                    case "video":
                        startActivity(new Intent(ECBActivity.this,IjkPlayerActivity.class));
                        break;
                    case "other":
                        startActivity(new Intent(ECBActivity.this,ImageSelectActivity.class));
                        break;
                }
            }
        });
        rvContent.setAdapter(adapter);
    }
}
