package com.example.giffun01

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 开源版内屏Activity界面，在这里进行程序初始化操作
 * */
class OpenSourceSplashActivity : SplashActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        logoView = logo
    }
}