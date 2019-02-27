package com.example.giffun01.login

import com.example.giffun01.BaseActivity
import com.example.giffun01.global.Const
import com.example.giffun01.global.GifFun
import com.example.giffun01.model.GetBaseinfo
import com.example.giffun01.util.SharedUtil

abstract class AuthActivity : BaseActivity() {

    companion object {
        private const val TAG = "AuthActivity"

        /**
         * 手机号登录的类型
         * */
        const val TYPE_PHONE_LOGIN = 4
    }

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
    protected fun getUserBaseinfo(){
//        GetBaseinfo.getResponse
    }
}
