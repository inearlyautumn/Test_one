package com.example.giffun01.util

import android.os.Looper
import android.widget.Toast
import com.example.giffun01.global.GifFun

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

    /**
     * 获取资源文件中定义的字符串。
     * */
    fun getString(resId: Int): String {
        return GifFun.getContext().resources.getString(resId)
    }

    /**
     * 弹出Toast信息，如果不是在主线程中调用此方法，Toast信息将不显示。
     * */
    fun showToast(content: String, duration: Int = Toast.LENGTH_SHORT) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            if (toast == null) {
                toast = Toast.makeText(GifFun.getContext(), content, duration)
            } else {
                toast?.setText(content)
            }
            toast?.show()
        }
    }

    /**
     * 切换到主线程后弹出Toast信息，此方法不管是在子线程还是在主线程中，都可以成功弹出Toast信息。
     * */
    fun showToastOnUiThread(content: String, duration: Int = Toast.LENGTH_SHORT) {
        GifFun.getHandler().post {
            if (toast == null) {
                toast = Toast.makeText(GifFun.getContext(), content, duration)
            } else {
                toast?.setText(content)
            }
            toast?.show()
        }
    }

    private var TAG = "GlobalUtil"

    private var toast: Toast? = null
}