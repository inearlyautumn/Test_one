package com.example.giffun01

import android.app.Application
import com.example.giffun01.global.GifFun

/**
 * GifFun自定义Application,在这里进行全局的初始化操作。
 * */
class GifFunApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        GifFun.initialize(this)

    }

}