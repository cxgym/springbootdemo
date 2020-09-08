package com.wondersgroup.springbootshirodemo.utils;

import java.util.Date;

public class TokenGenUtils {

    private final static String KEY = "wonders_heath_2019";

    public static String genToken(String username) {
        String data = username + "=>" + System.currentTimeMillis() + "=>" + IdGen.uuid();
        try {
            return DESUtil.encrypt(data, KEY);
        } catch (Exception e) {
            return null;
        }
    }

    public static String desToken(String token) {
        try {
            String data = DESUtil.decrypt(token, KEY);
            String[] arr = data.split("=>");
            if (arr.length != 3) {
                return null;
            }
            Date time = new Date(Long.parseLong(arr[1]));
            Date start = DateUtils.addDays(time, -1);
            Date end = DateUtils.addDays(time, 1);
            Date now = new Date();
            if (start.before(now) && end.after(now)) {
                return arr[0];
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String deKey = genToken("admin");
        System.out.println(deKey);
        System.out.println(desToken(deKey));
    }
}
