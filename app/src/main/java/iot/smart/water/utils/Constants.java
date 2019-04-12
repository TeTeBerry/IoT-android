package iot.smart.water.utils;

public class Constants {

    public static final String URL_ROOT     = "http://192.168.50.254:4000";
    public static final String URL_LOGIN    = URL_ROOT + "/members/authenticate";
    public static final String URL_REGISTER = URL_ROOT + "/members/register";
    public static final String URL_LOGOUT   = URL_ROOT + "/members/logout";

    public static final String APP_SP        = "iot_app";
    public static final String SP_MEMBERNAME = "membername";
    public static final String SP_SESSION    = "session";
    public static final String SP_TOKEN      = "token";
    public static final String SP_CREATE     = "create";
    public static final String SP_CREDIT     = "credit";
    public static final String SP_WATERFLOW  = "waterflow";
}
