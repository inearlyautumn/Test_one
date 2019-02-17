package com.example.giffun01.util

import android.widget.Toast

/**
 * 应用程序全局的通用工具类，功能比较单一，经常被复用的功能，应该封装到此工具
 * 类当中，从而给全局代码提供方面的操作。
 * */
object GlobalUtil {

    /**
     * 将当前线程睡眼指定毫秒数
     * @param millis
     * 睡眼的时长，单位毫秒。
     * */
    fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private var TAG = "GlobalUtil"

    private var toast: Toast? = null
}