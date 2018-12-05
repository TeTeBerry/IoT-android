package iot.smart.water.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import iot.smart.water.bean.Member;

public class SPUtil {

    public static void saveUser(Context context, Member member) {
        SharedPreferences sp = context.getSharedPreferences(Constants.APP_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constants.SP_MEMBERNAME, member.getMembername());
        editor.putString(Constants.SP_SESSION, member.getPassword());
        editor.apply();
    }

    public static void clearUserSeesion(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.APP_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Constants.SP_SESSION);
        editor.apply();
    }

    public static void clearUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.APP_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(Constants.SP_MEMBERNAME);
        editor.remove(Constants.SP_SESSION);
        editor.apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.APP_SP, Context.MODE_PRIVATE);
        return sp.getString(Constants.SP_MEMBERNAME, null);
    }

    public static Pair<String, String> getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.APP_SP, Context.MODE_PRIVATE);
        String username = sp.getString(Constants.SP_MEMBERNAME, null);
        String session = sp.getString(Constants.SP_SESSION, null);
        return new Pair<>(username, session);
    }

}
