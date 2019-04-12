package iot.smart.water.net;

import com.google.gson.Gson;

import iot.smart.water.bean.Member;
import iot.smart.water.utils.Constants;
import iot.smart.water.net.http.HttpRequest;
import iot.smart.water.net.http.RequestCallback;

public class InterfaceRequest {

    public static <T> void login(Member member, Class<T> beanClz, RequestCallback<T> callback) {
        String jsonParam = new Gson().toJson(member);
        HttpRequest.doPostAsync(Constants.URL_LOGIN, jsonParam, beanClz, callback);
    }

    public static <T> void register(Member member, Class<T> beanClz, RequestCallback<T> callback) {
        String jsonParam = new Gson().toJson(member);
        HttpRequest.doPostAsync(Constants.URL_REGISTER, jsonParam, beanClz, callback);
    }

    public static void logout() {
//        HttpRequest.doGetAsync(Constants.URL_LOGOUT, null, null);
    }
}
