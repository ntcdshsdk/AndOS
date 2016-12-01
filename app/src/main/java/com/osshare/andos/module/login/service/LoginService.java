package com.osshare.andos.module.login.service;

import com.osshare.andos.module.login.bean.LoginTest;
import com.osshare.framework.base.BaseUser;
import com.osshare.framework.http.HttpResponse;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * annotation Headers can be @Headers("name:value") or @Headers({"name:value","name1:value1",...)}
 * annotation GET can be @GET or @GET(param/param)
 * annotation POST can be @POST or @POST(param/param)
 * annotation FormUrlEncoded when POST and KEY-VALUE PARAM is need
 * annotation Multipart request body and file upload
 *
 */
public interface LoginService {

    @Multipart
    @POST("account/user")
    Observable<BaseUser> updateUserAvatar(@Part("userName") String userName,
                                          @Part("avatar") RequestBody avatar);

    @Multipart
    @POST("account/user")
    Observable<BaseUser> register(@Part("userName") String userName,
                                  @Part("password") String password,
                                  @Part("email") String email,
                                  @Part("phone") String phone,
                                  @Part("avatar") RequestBody avatar);

    @FormUrlEncoded
    @POST("account/user")
    Observable<BaseUser> login(@Field("userName") String userName,
                               @Field("password") String password,
                               @Field("sign") String sign);

    @POST("account/user")
    Observable<BaseUser> getUserInfo(@Body BaseUser user);


    @GET("top250")
    Observable<HttpResponse<LoginTest>> gitHubTest(@Query("start") int start, @Query("count") int count);

    @FormUrlEncoded
    @POST("top250")
    Observable<String> getMovies(@Field("start") int start, @Field("count") int count);
}
