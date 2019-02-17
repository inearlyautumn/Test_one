package com.example.giffun01.util

import android.os.Debug
import android.util.Log
import com.example.giffun01.global.GifFun

/**
 * 日志操作的扩展工具类
 *
 *
 * */
private const val VERBOSE = 1
private const val DEBUG = 2
private const val INFO = 3
private const val WARN = 4
private const val ERROR = 5
private const val NOTHING = 6

private val level = if (GifFun.isDebug) VERBOSE else WARN

fun logWarn(tag: String, msg: String?, tr: Throwable? = null) {
    if (level <= WARN) {
        if (tr == null) {
            Log.w(tag, msg.toString())
        } else {
            Log.w(tag, msg.toString(), tr)
        }
    }
}

fun logDebug(tag: String, msg: String) {
    if (level <= DEBUG) {
        Log.d(tag, msg.toString())
    }
}