package iot.smart.water.utils;

import java.security.MessageDigest;

public class HashUtil {
    private static final String[] hexDigits = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private static String hex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte aByte : bytes) {
            builder.append(hex(aByte));
        }
        return builder.toString();
    }

    private static String hex(byte b) {
        return hexDigits[(b >> 4 & 0xF)] + hexDigits[(b & 0xF)];
    }

    public static String get(String data) {
        try {
            return hex(MessageDigest.getInstance("SHA1").digest(data.getBytes()));
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
