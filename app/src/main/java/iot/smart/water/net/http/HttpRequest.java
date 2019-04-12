package iot.smart.water.net.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import iot.smart.water.net.pair.NameValuePair;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequest {

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * get请求
     */
    public static HttpResult doGet(String url) {
        HttpResult result = new HttpResult();
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = OkHttpHelper.execute(request);
            result.httpCode = response.networkResponse().code();
            if (response.isSuccessful()) {
                result.content = response.body().string();
            }
        } catch (IOException e) {
            result.isNetExcept = true;
        }

        return result;
    }

    public static <T> void doGetAsync(String url, Class beanClz, final RequestCallback<T> callback) {
        Request request = new Request.Builder().url(url).build();

        OkHttpHelper.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onFailed(e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                Log.d("tete", "onResponse: " + result);
                T t = null;
                try {
                    t = (T) new Gson().fromJson(result, beanClz);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("tete", e.toString());
                }
                T finalT = t;
                mHandler.post(() -> {
                    if (callback != null) {
                        if (finalT != null) {
                            callback.onSuccess(finalT);
                        } else {
                            callback.onFailed(result);
                        }
                    }
                });
            }
        });
    }

    /**
     * post请求
     * 默认contentType为json
     */
    public static HttpResult doPost(String url, String jsonParams) {
        HttpResult result = new HttpResult();
        RequestBody body = RequestBody.create(OkHttpHelper.APPLICATION_JSON, jsonParams);
        Request request = new Request.Builder().url(url).post(body).build();

        try {
            Response response = OkHttpHelper.execute(request);
            result.httpCode = response.networkResponse().code();
            if (response.isSuccessful()) {
                result.content = response.body().string();
            }
        } catch (IOException e) {
            result.isNetExcept = true;
        }

        return result;
    }

    /**
     * put 请求
     * 默认contentType为json
     */
    public static HttpResult doPut(String url, String jsonParams) {
        HttpResult result = new HttpResult();
        RequestBody body = RequestBody.create(OkHttpHelper.APPLICATION_JSON, jsonParams);
        Request request = new Request.Builder().url(url).put(body).build();

        try {
            Response response = OkHttpHelper.execute(request);
            result.httpCode = response.networkResponse().code();
            if (response.isSuccessful()) {
                result.content = response.body().string();
            }
        } catch (IOException e) {
            result.isNetExcept = true;
        }

        return result;
    }

    /**
     * post请求
     */
    public static HttpResult doPost(String url, String jsonParams, MediaType
            contentType) {
        HttpResult result = new HttpResult();
        RequestBody body = RequestBody.create(contentType, jsonParams);
        Request request = new Request.Builder().url(url).post(body).build();

        try {
            Response response = OkHttpHelper.execute(request);
            result.httpCode = response.networkResponse().code();
            if (response.isSuccessful()) {
                result.content = response.body().string();
            } else {
                result.failMsg = response.body().string();
            }
        } catch (IOException e) {
            result.isNetExcept = true;
        }

        return result;
    }

    public static <T> void doPostAsync(String url, String jsonParams, Class beanClz, final RequestCallback<T> callback) {
        RequestBody body = RequestBody.create(OkHttpHelper.APPLICATION_JSON, jsonParams);
        Log.d("tete", "url:"  + url);
        final Request request = new Request.Builder().url(url).post(body).build();

        OkHttpHelper.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(() -> {
                    if (callback != null) {
                        callback.onFailed(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String result = response.body().string();
                Log.d("tete", "onResponse: " + result);
                T t = null;
                try {
                    t = (T) new Gson().fromJson(result, beanClz);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("tete", e.toString());
                }
                T finalT = t;
                mHandler.post(() -> {
                    if (callback != null) {
                        if (finalT != null) {
                            callback.onSuccess(finalT);
                        } else {
                            callback.onFailed(result);
                        }
                    }
                });
            }
        });
    }

    public static HttpResult doPostBytes(String url, byte[] bytes) {
        HttpResult result = new HttpResult();

        RequestBody body = RequestBody.create(OkHttpHelper.APPLICATION_FORM_URLENCODED, bytes);
        Request request = new Request.Builder().url(url).post(body).build();
        try {
            Response response = OkHttpHelper.execute(request);
            result.httpCode = response.networkResponse().code();
            if (response.isSuccessful()) {
                result.content = response.body().string();
            }
        } catch (IOException e) {
            result.isNetExcept = true;
        }

        return result;
    }

    public static HttpResult uploadFile(String url, String filePath, String name, String fileName,
                                        String fileType, List<NameValuePair> params) {
        HttpResult result = new HttpResult();

        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();
        requestBodyBuilder.setType(MultipartBody.FORM);
        requestBodyBuilder.addFormDataPart(name, fileName, RequestBody.create(MediaType.parse(fileType), new File
                (filePath)));
        for (NameValuePair pair : params) {
            requestBodyBuilder.addFormDataPart(pair.getName(), pair.getValue());
        }
        RequestBody requestBody = requestBodyBuilder.build();

        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = OkHttpHelper.execute(request);
            result.httpCode = response.networkResponse().code();
            if (response.isSuccessful()) {
                result.content = response.body().string();
            }
        } catch (IOException e) {
            result.isNetExcept = true;
        }

        return result;
    }
}
