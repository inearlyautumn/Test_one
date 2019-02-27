package com.example.giffun01.util

import android.text.TextUtils
import com.example.giffun01.global.Const

/**
 * 服务器身份验证相关的工具类
 * */
object AuthUtil {
    /**
     * 判断用户是否已登录
     * */
    val isLogin: Boolean
        get() {
            val u = SharedUtil.read(Const.Auth.USER_ID, 0L)
            val t = SharedUtil.read(Const.Auth.TOKEN, "")
            val lt = SharedUtil.read(Const.Auth.LOGIN_TYPE, -1)
            return u > 0 && !TextUtils.isEmpty(t) && lt > 0
        }

    /**
     * 获取当前登录用户的id
     * @return 当前登录用户的id
     * */
    val userId: Long
        get() = SharedUtil.read(Const.Auth.USER_ID, 0L)
    /**
     * 获取当前登录用户的token
     * @return 当前登录用户的token
     * */
    val token: String
        get() = SharedUtil.read(Const.Auth.TOKEN, "")

}
