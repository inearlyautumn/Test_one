package com.example.giffun01.http.response

import com.example.giffun01.callback.Callback
import com.example.giffun01.http.request.FetchVCodeRequest

/**
 * 获取短信验证码请求的实体类封装
 * */
class FetchVCode : Response() {
    companion object {
        fun getResponse(number: String, callback: Callback) {
            FetchVCodeRequest().number(number).listen(callback)
        }
    }
}
