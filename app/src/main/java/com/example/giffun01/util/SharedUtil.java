package com.example.giffun01.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.example.giffun01.global.GifFun;

/**
 * SharedPreferences 工具类，提供简单的封装接口，简化SharedPreferences的用法
 */
public class SharedUtil {
    /**
     * 存储boolean类型的健值对到SharedPreferences文件当中。
     *
     * @param key
     * @param value
     */
    public static void save(String key, boolean value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(GifFun.getContext()).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 存储float类型的健值对到SharedPreferences文件当中。
     * @param key
     * @param value
     */
    public static void save(String key, float value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(GifFun.getContext()).edit();
        editor.putFloat(key, value);
        editor.apply();
    }
}
