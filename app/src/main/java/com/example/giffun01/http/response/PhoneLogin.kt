package com.example.giffun01.http.response

import com.example.giffun01.callback.Callback
import com.example.giffun01.http.request.PhoneLoginRequest
import com.example.giffun01.http.response.Response
import com.google.gson.annotations.SerializedName

/**
 * 手机号登录的实体类封装
 * */
class PhoneLogin : Response() {
    /**
     * 用户的账号id。
     * */
    @SerializedName("user_id")
    var userId: Long = 0

    /**
     * 记录用户的登录身份，token有效期30天
     * */
    var token = ""

    companion object {
        fun getResponse(number: String, code: String, callback: Callback) {
            PhoneLoginRequest()
        }

    }
}
