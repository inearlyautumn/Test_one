package com.example.giffun01.login

import android.os.Bundle
import android.os.CountDownTimer
import android.support.transition.Fade
import android.support.transition.TransitionManager
import android.transition.Transition
import android.view.View
import com.example.giffun01.R
import com.example.giffun01.callback.SimpleTransitionListener
import com.example.giffun01.event.FinishActivityEvent
import com.example.giffun01.http.response.PhoneLogin
import com.example.giffun01.callback.Callback
import com.example.giffun01.getResponseClue
import com.example.giffun01.http.response.FetchVCode
import com.example.giffun01.http.response.Response
import com.example.giffun01.util.AndroidVersion
import com.example.giffun01.util.GlobalUtil
import com.example.giffun01.util.GlobalUtil.showToast
import com.example.giffun01.util.ResponseHandler
import com.example.giffun01.util.logWarn
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import java.util.regex.Pattern

/**
 * 开源版登录界面，支持手机号登录。如果登录的账号还没有注册会跳转到注册界面，如果已经注册过了则会直接跳转到应用主界面
 * */
class OpenSourceLoginActivity : LoginActivity() {

    private lateinit var timer: CountDownTimer

    /**
     * 是否在正登录中。
     * */
    private var isLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun setupViews() {
        super.setupViews()
        val isStartWithTransition = intent.getBooleanExtra(LoginActivity.START_WITH_TRANSITION, false)
        if (AndroidVersion.hasLollipop() && isStartWithTransition) {
            isTransitioning = true
            window.sharedElementEnterTransition.addListener(object : SimpleTransitionListener() {
                override fun onTransitionEnd(transition: Transition?) {
                    val event = FinishActivityEvent()
                    event.activityClass = OpenSourceLoginActivity::class.java
                    EventBus.getDefault().post(event)
                    isTransitioning = false
                    fadeElementsIn()
                }
            })
        } else {
            loginLayoutBottom.visibility = View.VISIBLE
            loginBgWallLayout.visibility = View.VISIBLE
        }
        timer = SMSTimer(60 * 1000, 1000)
        getVerifyCode.setOnClickListener {
            val number = phoneNumberEdit.text.toString()
            if (number.isEmpty()) {
                showToast(GlobalUtil.getString(R.string.phone_number_is_empty))
                return@setOnClickListener
            }
            val pattern = "^1\\d{10}\$"
            if (!Pattern.matches(pattern, number)) {
                showToast(GlobalUtil.getString(R.string.phone_number_is_invalid))
                return@setOnClickListener
            }
            getVerifyCode.isClickable = false
            FetchVCode.getResponse(number, object : Callback {
                override fun onResponse(response: Response) {
                    if (response.status == 0) {
                        timer.start()
                        verifyCodeEdit.requestFocus()
                    } else {
                        showToast(response.msg)
                        getVerifyCode.isClickable = true
                    }
                }

                override fun onFailure(e: Exception) {
                    logWarn(TAG, e.message, e)
                    ResponseHandler.handleFailure(e)
                    getVerifyCode.isClickable = true
                }
            })
        }

        loginButton.setOnClickListener {
            if (isLogin) {
                return@setOnClickListener
            }
            val number = phoneNumberEdit.text.toString()
            val code = verifyCodeEdit.text.toString()
            if (number.isEmpty() || code.isEmpty()) {
                showToast(GlobalUtil.getString(R.string.phone_number_or_code_is_empty))
                return@setOnClickListener
            }
            val pattern = "^1\\d{10}\$"
            if (!Pattern.matches(pattern, number)) {
                showToast(GlobalUtil.getString(R.string.phone_number_is_invalid))
                return@setOnClickListener
            }
            processLogin(number, code)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    private fun processLogin(number: String, code: String) {
        hideSoftKeyboard()
        loginInProgress(true)
        PhoneLogin.getResponse(number, code, object : Callback {
            override fun onResponse(response: Response) {
                if (ResponseHandler.handleResponse(response)) {
                    val thirdPartyLogin = response as PhoneLogin
                    val status = thirdPartyLogin.status
                    val msg = thirdPartyLogin.msg
                    val userId = thirdPartyLogin.userId
                    val token = thirdPartyLogin.token
                    when (status) {
                        0 -> {
                            hideSoftKeyboard()
                            //处理登录成功时的逻辑，包括数据缓存，界面跳转等
                            saveAuthData(userId, token, TYPE_PHONE_LOGIN)
                            getUserBaseinfo()
                        }
                        10101 -> {//处理注册的逻辑
                            hideSoftKeyboard()
//                            OpenSourceLoginActivity.registerByPhone()
                        }
                        else -> {
                            logWarn(TAG, "Login failed. " + GlobalUtil.getResponseClue(status, msg))
                            showToast(response.msg)
                            loginInProgress(false)
                        }
                    }
                } else {
                    loginInProgress(false)
                }
            }

            override fun onFailure(e: Exception) {
                logWarn(TAG, e.message, e)
                ResponseHandler.handleFailure(e)
                loginInProgress(false)
            }
        })
    }

    /**
     * 根据用户是否正在注册来刷新界面。如果正在处理就显示进度，否则的话就显示输入框。
     * @param inProgress 是否正在注册
     * */
    private fun loginInProgress(inProgress: Boolean) {
        if (AndroidVersion.hasMarshmallow() && !(inProgress && loginRootLayout.keyboardShowed)) {
            TransitionManager.beginDelayedTransition(loginRootLayout, Fade())
        }
        isLogin = inProgress
        if (inProgress) {
            loginInputElements.visibility = View.INVISIBLE
            loginProgressBar.visibility = View.VISIBLE
        } else {
            loginProgressBar.visibility = View.INVISIBLE
            loginInputElements.visibility = View.VISIBLE
        }
    }

    inner class SMSTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        override fun onFinish() {
            getVerifyCode.text = GlobalUtil.getString(R.string.fetch_vcode)
            getVerifyCode.isClickable = true
        }

        override fun onTick(millisUntilFinished: Long) {
            getVerifyCode.text = String.format(GlobalUtil.getString(R.string.sms_is_sent), millisUntilFinished / 1000)
        }
    }

    /**
     * 将LoginActivity的界面元素使用谈入的方式显示出来
     * */
    private fun fadeElementsIn() {
        TransitionManager.beginDelayedTransition(loginLayoutBottom, Fade())
        loginLayoutBottom.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(loginBgWallLayout, Fade())
        loginBgWallLayout.visibility = View.VISIBLE
    }

    companion object {
        const val TAG = "OpenSourceLoginActivity"
    }

}
