package com.osshare.andos.module.news.module;

import com.osshare.andos.module.news.service.NewsService;
import com.osshare.framework.manager.Constant;
import com.osshare.framework.util.OkHttpUtil;
import com.osshare.framework.util.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by apple on 16/11/27.
 */
public class NewsModule {
    public static void getWordNews(int num, int page, Observer observer) {
        RetrofitUtil.provideRetrofitBuilder(OkHttpUtil.instance().getClient())
                .baseUrl(Constant.BASE_URL_API).build()
                .create(NewsService.class).getNews(num, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void getNetImages(int num, Observer observer) {
        RetrofitUtil.provideRetrofitBuilder(OkHttpUtil.instance().getClient())
                .baseUrl(Constant.BASE_URL_API).build()
                .create(NewsService.class).getNetImages(num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
