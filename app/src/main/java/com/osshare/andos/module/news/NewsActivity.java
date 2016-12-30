package com.osshare.andos.module.news;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.CollapsibleActionView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.osshare.andos.R;
import com.osshare.andos.activity.WebViewActivity;
import com.osshare.andos.base.abs.AbsActivity;
import com.osshare.andos.bean.User;
import com.osshare.andos.module.news.bean.News;
import com.osshare.andos.module.news.bean.NewsResData;
import com.osshare.andos.module.news.module.NewsModule;
import com.osshare.andos.util.CharSeqUtil;
import com.osshare.core.view.pull.PullLayout;
import com.osshare.core.view.pull.PullableLayout;
import com.osshare.framework.base.BaseActivity;
import com.osshare.framework.base.BaseAdapter;
import com.osshare.framework.base.BaseViewHolder;
import com.osshare.framework.manager.Constant;
import com.osshare.framework.util.ImageLoader;
import com.osshare.framework.util.RxEventBus;
import com.osshare.framework.util.RxUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by apple on 16/11/27.
 */
public class NewsActivity extends AbsActivity {
        CollapsibleActionView actionView;
    AppBarLayout barLayout; //✓
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout toolbarLayout;
    private PullableLayout plContainer;
    private RecyclerView rvContent;
    private BaseAdapter<News> adapter;

    private int pageNum = 15;
    private int page = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        immersiveHeaderContainer(R.id.layout_title_bar);

        ((TextView) findViewById(R.id.tv_title)).setText(R.string.module_news);
        plContainer = (PullableLayout) findViewById(R.id.pl_container);
        View mBottomView = new ImageView(this);
        mBottomView.setBackgroundColor(Color.GREEN);
        plContainer.setFooterView(mBottomView);
//        plContainer.s(new PullLayout.OnPullListener() {
//            @Override
//            public void onPullDownRelease() {
////                adapter.clearData();
//                page = 1;
//                getData();
//            }
//
//            @Override
//            public void onPullUpRelease() {
//                page++;
//                getData();
//            }
//        });
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

                String imgUrl = itemBean.getPicUrl();
                if (CharSeqUtil.isEmpty(imgUrl)) {
                    ivPic.setVisibility(View.GONE);
                } else {
                    ivPic.setVisibility(View.VISIBLE);
                    ImageLoader.loadImage(NewsActivity.this, imgUrl, ivPic);
                }

                tvTitle.setText(itemBean.getTitle());
                tvDescription.setText(itemBean.getDescription());
                tvDate.setText(itemBean.getCtime());
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, BaseViewHolder holder) {
                Intent intent = new Intent(NewsActivity.this, WebViewActivity.class);
                intent.putExtra(Constant.KEY_URL, adapter.getItem(holder.getLayoutPosition()).getUrl());
                startActivity(intent);
            }
        });
        rvContent.setAdapter(adapter);

        User user=new User();user.setName("user");
        RxEventBus.getInstance().publish(user);
        RxEventBus.getInstance().publish("111");
        getData();
    }

    private void getData() {
        NewsModule.getWordNews(pageNum, page, new Observer<NewsResData>() {
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        plContainer.setPullResult(false, false);
                    }
                }, 2000);
            }
        });
    }

    public void onGetNewsSuccess(List<News> data) {
        adapter.addData(data);
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
