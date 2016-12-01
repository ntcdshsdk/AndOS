package com.osshare.framework.http;

/**
 * Created by apple on 16/11/7.
 */
public class HttpException extends Throwable {
    private int statusCode;

    public HttpException() {
        super();
    }

    public HttpException(int statusCode, String detailMessage) {
        super(detailMessage);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
