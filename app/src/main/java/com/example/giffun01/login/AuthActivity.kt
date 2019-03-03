package com.example.giffun01.login

import com.example.giffun01.BaseActivity
import com.example.giffun01.R
import com.example.giffun01.callback.Callback
import com.example.giffun01.getResponseClue
import com.example.giffun01.global.Const
import com.example.giffun01.global.GifFun
import com.example.giffun01.http.response.GetBaseinfo
import com.example.giffun01.http.response.Response
import com.example.giffun01.util.*
import com.example.giffun01.util.GlobalUtil.showToast

abstract class AuthActivity : BaseActivity() {

    /**
     * 存储用户身份的信息
     * @param userId        用户id
     * @param token         用户token
     * @param loginType     登录类型
     * */
    protected fun saveAuthData(userId: Long, token: String, loginType: Int) {
        SharedUtil.save(Const.Auth.USER_ID, userId)
        SharedUtil.save(Const.Auth.TOKEN, token)
        SharedUtil.save(Const.Auth.LOGIN_TYPE, loginType)
        GifFun.refreshLoginState()
    }

    /**
     * 获取当前登录用户的基本信息，包括昵称、头象等。
     * */
    protected fun getUserBaseinfo() {
        GetBaseinfo.getResponse(object : Callback {
            override fun onResponse(response: Response) {
                if (activity == null) {
                    return
                }
                if (!ResponseHandler.handleResponse(response)) {
                    val baseinfo = response as GetBaseinfo
                    val status = baseinfo.status
                    when (status) {
                        0 -> {
                            UserUtil.saveNickname(baseinfo.nickname)
                            UserUtil.saveAvatar(baseinfo.avatar)
                            UserUtil.saveDescription(baseinfo.description)
                            UserUtil.saveBgImage(baseinfo.bgImage)
                            forwardToMainActivity()
                        }
                        10202 -> {
                            showToast(GlobalUtil.getString(R.string.get_baseinfo_failed_user_not_exist))
                            GifFun.logout()
                            finish()
                        }
                        else -> {
                            logWarn(
                                TAG,
                                "Get user baseinfo failed. " + GlobalUtil.getResponseClue(status, baseinfo.msg)
                            )
                            showToast(GlobalUtil.getString(R.string.get_baseinfo_failed))
                            GifFun.logout()
                            finish()
                        }
                    }
                } else {
                    activity?.let {
                        if (it.javaClass.name == "club.giffun.app.LoginDialogActivity") {
                            finish()
                        }
                    }
                }
            }

            override fun onFailure(e: Exception) {
                logWarn(TAG, e.message, e)
                showToast(GlobalUtil.getString(R.string.get_baseinfo_failed))
                GifFun.logout()
                finish()
            }
        })
    }

    protected abstract fun forwardToMainActivity()

    companion object {
        private const val TAG = "AuthActivity"

        /**
         * QQ第三方登录的类型
         * */
        const val TYPE_QQ_LOGIN = 1

        /**
         * 微信第三方登录的类型
         * */
        const val TYPE_WECHAT_LOGIN = 2

        /**
         * 微博第三方登录的类型。
         */
        const val TYPE_WEIBO_LOGIN = 3

        /**
         * 手机号登录的类型。
         */
        const val TYPE_PHONE_LOGIN = 4

        /**
         * 游客登录的类型，此登录只在测试环境下有效，线上环境没有此项功能。
         */
        const val TYPE_GUEST_LOGIN = -1
    }
}
