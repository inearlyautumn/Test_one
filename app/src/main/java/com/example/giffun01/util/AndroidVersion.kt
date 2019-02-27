package com.example.giffun01.util

import android.os.Build

/**
 * 以更加可读的方式提供Android 系统版本号的判断方法。
 *
 * (在Java中，单例的声明可能具有多种方式：如懒汉式、饿汉式、静态内部类、枚举等；
在Kotlin中，单例模式的实现只需要一个 object 关键字即可；)
 * */
object AndroidVersion {
    /**
     * 判断当前手机系统版本api是否是16以上。
     * @return 16以上返回true,
     * */
    fun hasJellyBean(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
    }

    /**
     * 判断当前手机系统版本API是否是17以上
     * @return 17以上返回true.
     * */
    fun hasJellyBeanMrR1(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
    }

    /**
     * 判断当前手机系统版本API是否是21以上
     * */
    fun hasLollipop(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    /**
     * 判断当前手机系统版本API是否是23以上
     * */
    fun hasMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }
}
