package com.osshare.framework.http;

import java.io.File;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.ByteString;

/**
 * Created by apple on 16/11/6.
 */
public class HttpRequest {
    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("application/octet-stream");
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/octet-stream");
    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
    private final Request request;

    private HttpRequest(Request request) {
        this.request = request;
    }

//    public RequestCall build(){
//        return new RequestCall().buildCall();
//    }

    public Request getRequest() {
        return request;
    }

    public Request clone() {
        return request.newBuilder().build();
    }

    public static Request buildRequest(String url, String method, Map<String, String> headers, RequestBody requestBody, Object tag) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        builder.method(method, requestBody);
        return builder.tag(tag).build();
    }

    public static Request buildRequest(String url, String method, Map<String, String> headers, MediaType contentType, Object content, Object tag) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        RequestBody requestBody = null;
        if (content instanceof byte[]) {
            requestBody = RequestBody.create(contentType, (byte[]) content);
        } else if (content instanceof String) {
            requestBody = RequestBody.create(contentType, (String) content);
        } else if (content instanceof File) {
            requestBody = RequestBody.create(contentType, (File) content);
        } else if (content instanceof ByteString) {
            requestBody = RequestBody.create(contentType, (ByteString) content);
        }
        builder.method(method, requestBody);
        return builder.tag(tag).build();
    }

    public static Request buildRequest(String url, String method, Map<String, String> headers, Map<String, String> params, Object tag) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        if (params != null) {
            FormBody.Builder formBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.method(method, formBuilder.build());
        }
        return builder.tag(tag).build();
    }


    public static Request buildRequest(String url, String method, Map<String, String> headers, Map<String, String> params, Map<String, File> fileParams, Object tag) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.header(entry.getKey(), entry.getValue());
            }
        }
        if (params != null || fileParams != null) {
            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, File> entry : fileParams.entrySet()) {
                File file = entry.getValue();
                multipartBuilder.addFormDataPart(entry.getKey(), file.getName(), RequestBody.create(MEDIA_TYPE_STREAM, file));
            }
            builder.method(method, multipartBuilder.build());
        }
        return builder.tag(tag).build();
    }

    private static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentType = null;
        try {
            contentType = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (contentType == null) {
            contentType = "contentType = application/octet-stream";
        }
        return contentType;
    }


    public static class Builder {
        private String url;
        private String method;
        private MediaType mediaType;
        private Object content;
        private Map<String, String> headers;
        private Map<String, String> params;
        private Map<String, File> files;
        private Object tag;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder method(String method) {
            this.method = method;
            return this;
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder content(MediaType mediaType, Object content) {
            this.mediaType = mediaType;
            this.content = content;
            return this;
        }

        public Builder addFile(String key, File file) {
            if (this.files == null) {
                this.files=new HashMap<>();
            }
            this.files.put(key, file);
            return this;
        }

        public Builder addFiles(Map<String, File> files) {
            if (this.files == null) {
                this.files=new HashMap<>();
            }
            this.files.putAll(files);
            return this;
        }

        public Builder files(Map<String, File> files) {
            this.files = files;
            return this;
        }

        public Builder addParam(String key, String value) {
            if (this.params == null) {
                this.params=new HashMap<>();
            }
            this.params.put(key, value);
            return this;
        }

        public Builder addParams(Map<String, String> params) {
            if (this.params == null) {
                this.params=new HashMap<>();
            }
            this.params.putAll(params);
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder addHeader(String name, String value) {
            if (this.headers == null) {
                this.headers=new HashMap<>();
            }
            this.headers.put(name, value);
            return this;
        }

        public Builder addHeaders(Map<String, String> headers) {
            if (this.headers == null) {
                this.headers=new HashMap<>();
            }
            this.headers.putAll(headers);
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers=headers;
            return this;
        }


        public HttpRequest build() {
            if (content != null && (params != null || files != null)) {
                throw new NullPointerException("content == null");
            }
            if (content != null) {
                new HttpRequest(buildRequest(url, method, headers, mediaType, content, tag));
            } else if (files != null) {
                return new HttpRequest(buildRequest(url, method, headers, params, files, tag));
            } else if (params != null) {
                return new HttpRequest(buildRequest(url, method, headers, params, tag));
            }
            return new HttpRequest(buildRequest(url, method, headers, (RequestBody) null, tag));
        }
    }
}
