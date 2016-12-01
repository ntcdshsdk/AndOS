package com.osshare.framework.util;

import com.osshare.framework.http.HttpException;
import com.osshare.framework.http.HttpRequest;
import com.osshare.framework.http.callback.ResponseCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by apple on 16/11/1.
 */
public class OkHttpUtil {
    private volatile static OkHttpUtil instance;
    private OkHttpClient client;

    private OkHttpUtil() {
        if (client == null) {
//            client = new OkHttpClient();
            client = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
    }

    public OkHttpUtil(OkHttpClient httpClient) {
        if (httpClient == null) {
            httpClient = new OkHttpClient();
        }
        this.client = httpClient;
    }

    public static OkHttpUtil instance() {
        if (instance == null) {
            synchronized (OkHttpUtil.class) {
                if (instance == null) {
                    instance = new OkHttpUtil();
                }
            }
        }
        return instance;
    }

    public OkHttpClient.Builder clone() {
        return client.newBuilder();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public Call buildCall(HttpRequest request) {
        return client.newCall(request.getRequest());
    }

    public Call buildCall(OkHttpClient client, HttpRequest request) {
        return client.newCall(request.getRequest());
    }

    public void execute(HttpRequest request, ResponseCallback<Response> callback) {
        final Call call = buildCall(request);
        execute(call, callback);
    }

    public void execute(OkHttpClient client, HttpRequest request, ResponseCallback<Response> callback) {
        final Call call = buildCall(client, request);
        execute(call, callback);
    }

    public void execute(Call call, ResponseCallback<Response> callback) {
        callback.onStart(call);
        try {
            Response response = call.execute();
            if (call.isCanceled()) {
                callback.onFailure(call, new IOException("Canceled"));
            } else {
                int statusCode = response.code();
                if (response.isSuccessful()) {
                    callback.onSuccess(call, response);
                } else {
                    callback.onFailure(call, new HttpException(statusCode, "HttpException==>StatusCode:" + statusCode));
                }
            }
        } catch (IOException e) {
            callback.onFailure(call, e);
        } finally {
            callback.onFinish(call);
        }
    }

    public void enqueue(HttpRequest request, ResponseCallback<Response> callback) {
        Call call = buildCall(request);
        enqueue(call, callback);
    }

    public void enqueue(OkHttpClient client, HttpRequest request, ResponseCallback<Response> callback) {
        final Call call = buildCall(client, request);
        enqueue(call, callback);
    }

    public void enqueue(Call call, final ResponseCallback<Response> callback) {
        callback.onStart(call);
        try {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(call, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        callback.onSuccess(call, response);
                    } else {
                        callback.onFailure(call, new HttpException(statusCode, "HttpException==>StatusCode:" + statusCode));
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure(call, e);
        } finally {
            callback.onFinish(call);
        }
    }

}
