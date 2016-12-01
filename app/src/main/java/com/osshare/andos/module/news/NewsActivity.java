package com.osshare.andos.module.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.osshare.andos.R;
import com.osshare.andos.activity.WebViewActivity;
import com.osshare.andos.module.news.bean.News;
import com.osshare.andos.module.news.bean.NewsResData;
import com.osshare.andos.module.news.module.NewsModule;
import com.osshare.andos.util.CharSeqUtil;
import com.osshare.framework.base.BaseActivity;
import com.osshare.framework.base.BaseAdapter;
import com.osshare.framework.base.BaseViewHolder;
import com.osshare.framework.manager.Constant;
import com.osshare.framework.util.ImageLoader;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by apple on 16/11/27.
 */
public class NewsActivity extends BaseActivity {
    //    CollapsibleActionView actionView;
//    AppBarLayout barLayout; ✓
//    CoordinatorLayout coordinatorLayout;
//    CollapsingToolbarLayout toolbarLayout;
    private RecyclerView rvContent;
    private BaseAdapter<News> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ((TextView) findViewById(R.id.tv_title)).setText(R.string.module_news);
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        rvContent.setLayoutManager(new LinearLayoutManager(NewsActivity.this));
        adapter = new BaseAdapter<News>(NewsActivity.this, null) {
            @Override
            public View getItemView(ViewGroup parent, int viewType) {
                return inflater.inflate(R.layout.layout_item_news, parent, false);
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                News itemBean = getItem(position);

                ImageView ivPic = holder.getView(R.id.iv_pic);
                TextView tvTitle = holder.getView(R.id.tv_title);
                TextView tvDescription = holder.getView(R.id.tv_description);
                TextView tvDate = holder.getView(R.id.tv_date);

                String imgUrl=itemBean.getPicUrl();
                if(CharSeqUtil.isEmpty(imgUrl)){
                    ivPic.setVisibility(View.GONE);
                }else{
                    ivPic.setVisibility(View.VISIBLE);
                    ImageLoader.loadImage(NewsActivity.this,imgUrl,ivPic);
                }

                tvTitle.setText(itemBean.getTitle());
                tvDescription.setText(itemBean.getDescription());
                tvDate.setText(itemBean.getCtime());
            }
        };
        adapter.setItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseViewHolder holder) {
                Intent intent=new Intent(NewsActivity.this, WebViewActivity.class);
                intent.putExtra(Constant.KEY_URL,adapter.getItem(holder.getLayoutPosition()).getUrl());
                startActivity(intent);
            }
        });
        rvContent.setAdapter(adapter);

        getData();
    }

    private void getData() {
        NewsModule.getWordNews(15, 1, new Observer<NewsResData>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe" + d.isDisposed());
            }

            @Override
            public void onNext(NewsResData value) {
                Log.i(TAG, "onNext==>" + new Gson().toJson(value));
                onGetNewsSuccess(value.getNewslist());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.i(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        });
    }

    public void onGetNewsSuccess(List<News> data) {
        adapter.setData(data);
    }


    class SearchBehavior extends CoordinatorLayout.Behavior<View> {
        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
            return true;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
            return true;
        }
    }
}
