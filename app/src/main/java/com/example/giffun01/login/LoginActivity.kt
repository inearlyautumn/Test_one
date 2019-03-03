package com.example.giffun01.login

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import com.example.giffun01.MainActivity
import com.example.giffun01.R
import com.example.giffun01.global.GifFun
import com.example.giffun01.model.Version
import com.example.giffun01.util.AndroidVersion

/**
 * 应用程序登录界面的基类
 * */
abstract class LoginActivity : AuthActivity() {

    //是否正在进行transition动画
    protected var isTransitioning = false

    /**
     * 启动LoginActivity，并附带Transition动画
     *
     * @param activity
     * 原Activity的实例
     * @param logo
     * 要执行transition动画的控件
     * */
    fun actionStartWithTransition(activity: Activity, logo: View, hasNewVersion: Boolean, version: Version) {
        Intent()
    }

    override fun forwardToMainActivity() {
        //登录成功，跳转到应用主界面
        MainActivity.actionStart(this)
        finish()
    }

    companion object {

        private const val TAG = "LoginActivity"

        @JvmStatic
        val START_WITH_TRANSITION = "start_with_transition"

        @JvmStatic
        val INTENT_HAS_NEW_VERSION = "intent_has_new_version"

        @JvmStatic
        val INTENT_VERSION = "intent_version"

        private val ACTION_LOGIN = "${GifFun.getPackageName()}.ACTION_LOGIN"

        private val ACTION_LOGIN_WITH_TRANSITION = "${GifFun.getPackageName()}.ACTION_LOGIN_WITH_TRANSITION"


        /**
         * 启动LoginActivity，并附带Transition动画
         * @param activity
         * 原Activity的实例
         * @param logo
         * 要执行transition动画控件
         * */
        fun actionStartWithTransition(activity: Activity, logo: View, hasNewVersion: Boolean, version: Version?) {
            val intent = Intent(ACTION_LOGIN_WITH_TRANSITION).apply {
                putExtra(INTENT_VERSION, version)
            }
            if (AndroidVersion.hasLollipop()) {
                intent.putExtra(START_WITH_TRANSITION, true)
                val options = ActivityOptions.makeSceneTransitionAnimation(activity, logo, activity.getString(R.string.transition_logo_splash))
                activity.startActivity(intent, options.toBundle())
            } else {
                activity.startActivity(intent)
                activity.finish()
            }
        }

        /**
         * 启动LoginActivity
         * @param activity
         * 原Activity的实例
         * @param hasNewVersion
         * 是否存在版本更新
         * */
        fun actionStart(activity: Activity, hasNewVersion: Boolean, version: Version?) {
            val intent = Intent(ACTION_LOGIN).apply {
                putExtra(INTENT_HAS_NEW_VERSION, hasNewVersion)
                putExtra(INTENT_VERSION, version)
            }
            activity.startActivity(intent)
        }
    }
}