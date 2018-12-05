package iot.smart.water.utils;

public class Constants {

    public static final String URL_ROOT     = "http://192.168.50.2:3000";
    public static final String URL_LOGIN    = URL_ROOT + "/api/members/login";
    public static final String URL_REGISTER = URL_ROOT + "/api/members/memberadd";
    public static final String URL_LOGOUT   = URL_ROOT + "/api/members/logout";

    public static final String APP_SP        = "iot_app";
    public static final String SP_MEMBERNAME = "membername";
    public static final String SP_SESSION    = "session";
    public static final String SP_TOKEN      = "token";
    public static final String SP_CREATE     = "create";
    public static final String SP_CREDIT     = "credit";
    public static final String SP_WATERFLOW  = "waterflow";
}
