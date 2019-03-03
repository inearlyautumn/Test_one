package com.example.giffun01.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密辅助工具类
 * */
public class MD5 {
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 对传入的字符串进行MD5加密
     * @param origin 原始字符串。
     * @return       经过MD5加密后的字符串。
     */
    public static String encrypt(String origin) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(origin.getBytes(Charset.defaultCharset()));
            return new String(toHex(digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static char[] toHex(byte[] data) {
        char[] toDigits = DIGITS_UPPER;
        int l = data.length;
        char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
            out[j++] = toDigits[0x0F & data[i]];
        }
        return out;
    }
}
