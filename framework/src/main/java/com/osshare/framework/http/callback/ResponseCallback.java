package com.osshare.framework.http.callback;

import okhttp3.Call;

/**
 * Created by apple on 16/11/7.
 */
public interface ResponseCallback<R> {
    void onStart(Call call);

    void onSuccess(Call call, R r);

    void onFailure(Call call, Throwable e);

//    void onError(Call call, Exception e);

    void onFinish(Call call);
}
