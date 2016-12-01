package com.osshare.andos.module.news.service;

import com.osshare.andos.module.news.bean.NewsResData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by apple on 16/11/27.
 */
public interface NewsService {

    @Headers("apikey:5e42f9ac25afbc450eb98e53cc2e6223")
    @GET("world/world")
    Observable<NewsResData> getNews(@Query("num") int num, @Query("page") int page);

    @Headers("apikey:5e42f9ac25afbc450eb98e53cc2e6223")
    @GET("mvtp/meinv")
    Observable<NewsResData> getNetImages(@Query("num") int num);
}
