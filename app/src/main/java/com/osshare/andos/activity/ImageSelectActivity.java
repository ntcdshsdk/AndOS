package com.osshare.andos.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.osshare.andos.R;
import com.osshare.andos.base.abs.AbsActivity;
import com.osshare.andos.module.news.bean.News;
import com.osshare.andos.module.news.bean.NewsResData;
import com.osshare.andos.module.news.module.NewsModule;
import com.osshare.core.view.pull.temp.PullableXLayout;
import com.osshare.framework.base.BaseActivity;
import com.osshare.framework.base.BaseAdapter;
import com.osshare.framework.base.BaseViewHolder;
import com.osshare.framework.util.ImageLoader;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by apple on 16/11/29.
 */
public class ImageSelectActivity extends AbsActivity {
    private static final int SELECT_MAX = 1;
    private static final int SELECT_MIN = 0;

    private PullableXLayout plContainer;
    private RecyclerView rvContent;
    private BaseAdapter<News> adapter;

    private int num = 30;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        immersiveHeaderContainer(R.id.layout_title_bar);

        plContainer = (PullableXLayout) findViewById(R.id.pl_container);
        plContainer.setOnPullListener(new PullableXLayout.OnPullListener() {
            @Override
            public void onPullDown() {
                num = 30;
                getNetImages(num);
            }

            @Override
            public void onPullUp() {
                getNetImages(num += 10);
            }
        });
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
        rvContent.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        adapter = new BaseAdapter<News>(ImageSelectActivity.this, null) {
            @Override
            public View getItemView(ViewGroup parent, int viewType) {
                return inflater.inflate(R.layout.layout_item_image_select, parent, false);
            }

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                News itemBean = getItem(holder.getLayoutPosition());
                ImageView ivImg = holder.getView(R.id.iv_img);

                ImageLoader.loadImage(ImageSelectActivity.this, itemBean.getPicUrl(), ivImg);
            }
        };
        rvContent.setAdapter(adapter);

        getNetImages(30);
    }

    public void getNetImages(int num) {
        NewsModule.getNetImages(num, new Observer<NewsResData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(NewsResData value) {
                onGetNetImagesSuccess(value.getNewslist());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void onGetNetImagesSuccess(List<News> data) {
        adapter.setData(data);
    }


}
