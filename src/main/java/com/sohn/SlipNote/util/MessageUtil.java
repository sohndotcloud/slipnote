package com.sohn.SlipNote.util;

import java.security.SecureRandom;
import java.util.UUID;

public class MessageUtil {

    private static final SecureRandom random = new SecureRandom();

    public static String randomAlphanumeric(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String generateId() {
        return randomAlphanumeric(6);
    }

    public static String generateKey() {
        return randomAlphanumeric(6);
    }
}
