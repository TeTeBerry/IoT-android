package iot.smart.water.net.http;

public interface RequestCallback<T> {
    void onSuccess(T data);

    void onFailed(String msg);
}
