package com.wondersgroup.springbootshirodemo.utils;

import java.security.SecureRandom;
import java.util.UUID;

public class IdGen {

    private static SecureRandom random = new SecureRandom();

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
