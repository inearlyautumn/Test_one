package com.example.giffun01

import android.os.Bundle
import android.view.View
import com.example.giffun01.global.GifFun
import com.example.giffun01.login.LoginActivity
import com.example.giffun01.model.Version
import com.example.giffun01.util.GlobalUtil

/**
 * 闪屏Activity界面，在这里进行程序初始化操作
 * */
abstract class SplashActivity : BaseActivity() {

    //记录进入SplashActivity的时间
    var enterTime: Long = 0

    //判断是否正在跳转或已经跳转到下一个界面。
    var isForwarding = false

    lateinit var logoView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTime = System.currentTimeMillis()
        delayToForward()
    }

    /**
     * 设置闪屏界面的最大延迟跳转，让用户不至于在闪屏界面等待太久
     * */
    private fun delayToForward() {
        Thread(Runnable {
            GlobalUtil.sleep(MAX_WAIT_TIME.toLong())
            forwardToNextActivity(false, null)
        }).start()
    }

    /**
     * 跳转到下一个Activity。如果在闪屏界面停留的时间不足规定最短停留时间，
     * 则会在这里等待一会，保证闪屏界面不至于一闪而过。
     * */
    @Synchronized
    open fun forwardToNextActivity(hasNewVersion: Boolean, version: Version?) {
        //如果正在跳转或已经跳转到下一个界面，则不再重复执行跳转
        if (!isForwarding) {
            isForwarding = true
            val currentTime = System.currentTimeMillis()
            val timeSpent = currentTime - enterTime
            if (timeSpent < MIN_WAIT_TIME) {
                GlobalUtil.sleep(MIN_WAIT_TIME - timeSpent)
            }
            runOnUiThread {
                if (GifFun.isLogin()) {
                    MainActivity.actionStart(this)
                    finish()
                } else {
                    if (isActive) {
                        LoginActivity.actionStartWithTransition(this, logoView, hasNewVersion, version)
                    } else {
                        LoginActivity.actionStart(this, hasNewVersion,version)
                        finish()
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "SplashActivity"

        //应用程序在闪屏界面最短的停留时间。
        const val MIN_WAIT_TIME = 2000

        //应用程序在闪屏界面最长的停留时间。
        const val MAX_WAIT_TIME = 5000
    }
}
