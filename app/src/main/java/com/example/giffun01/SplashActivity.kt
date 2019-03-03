package com.example.giffun01

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.giffun01.callback.OriginThreadCallback
import com.example.giffun01.global.Const
import com.example.giffun01.global.GifFun
import com.example.giffun01.http.response.Init
import com.example.giffun01.http.response.Response
import com.example.giffun01.login.LoginActivity
import com.example.giffun01.model.Version
import com.example.giffun01.util.GlobalUtil
import com.example.giffun01.util.ResponseHandler
import com.example.giffun01.util.SharedUtil
import com.example.giffun01.util.logWarn

/**
 * 闪屏Activity界面，在这里进行程序初始化操作
 * */
abstract class SplashActivity : BaseActivity() {

    //记录进入SplashActivity的时间
    var enterTime: Long = 0

    //判断是否正在跳转或已经跳转到下一个界面。
    var isForwarding = false

    var hasNewVersion = false

    lateinit var logoView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTime = System.currentTimeMillis()
        delayToForward()
    }

    override fun setupViews() {
        startInitRequest()
    }

    /**
     * 开始向服务器发送初始化请求
     * */
    private fun startInitRequest() {
        Init.getResponse(object : OriginThreadCallback {
            override fun onResponse(response: Response) {
                if (activity == null) {
                    return
                }
                var version: Version? = null
                val init = response as Init
                GifFun.BASE_URL = init.base
                if (!ResponseHandler.handleResponse(init)) {
                    val status = init.status
                    if (status == 0) {
                        val token = init.token
                        val avatar = init.avatar
                        val bgImage = init.bgImage
                        val hasNewVersion = init.hasNewVersion
                        if (hasNewVersion) {
                            version = init.version
                        }
                        if (!TextUtils.isEmpty(token)) {
                            SharedUtil.save(Const.Auth.TOKEN, token)
                            if (!TextUtils.isEmpty(avatar)) {
                                SharedUtil.save(Const.User.AVATAR, avatar)
                            }
                            if (!TextUtils.isEmpty(bgImage)) {
                                SharedUtil.save(Const.User.BG_IMAGE, bgImage)
                            }
                            GifFun.refreshLoginState()
                        }
                    } else {
                        logWarn(TAG, GlobalUtil.getResponseClue(status, init.msg))
                    }
                }
                forwardToNextActivity(hasNewVersion, version)
            }

            override fun onFailure(e: Exception) {
                logWarn(TAG, e.message, e)
                forwardToNextActivity(false, null)
            }

        })
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
                        LoginActivity.actionStart(this, hasNewVersion, version)
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

/**
 * 获取请求结果的线索，即将状态码和简单描述组合成一段调试信息
 * @param status
 * 请求结果的状态码
 * @param msg
 * 请求结果的简单描述
 * @return 请求结果的调试线索
 * */
fun GlobalUtil.getResponseClue(status: Int, msg: String): String? {
    return "code: $status, msg: $msg"
}
