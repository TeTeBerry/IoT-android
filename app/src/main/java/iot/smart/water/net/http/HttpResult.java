package iot.smart.water.net.http;

public class HttpResult {

    public static final int REQUEST_OK = 200;

    public int httpCode = REQUEST_OK;
    public boolean isNetExcept = false;
    public String content = "";
    public String errMsg = "";

    public String failMsg = "";

}