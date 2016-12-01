package com.osshare.andos.module.login.module;

import com.osshare.andos.module.login.service.LoginService;
import com.osshare.framework.util.OkHttpUtil;
import com.osshare.framework.util.RetrofitUtil;

import dagger.Module;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by apple on 16/11/9.
 */
@Module
public class LoginModule {

    public static void login(int start, int count, Observer observer) {
        RetrofitUtil.provideRetrofit(OkHttpUtil.instance().getClient())
                .create(LoginService.class).gitHubTest(0, 10)
                .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void test(){
        RetrofitUtil.provideRetrofit(OkHttpUtil.instance().clone().build())
                .create(LoginService.class).getMovies(0,15)
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return null;
                    }
                }).subscribe();

    }
}
