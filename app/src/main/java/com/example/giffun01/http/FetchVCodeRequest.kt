package com.example.giffun01.http

import com.example.giffun01.global.GifFun
import com.example.giffun01.model.Callback


/**
 * 获取短信验证吗请求，对应服务器接口：/login/fetch_verify_code
 * */
class FetchVCodeRequest : Request() {
    override fun method(): Int {
        return Request.POST
    }

    override fun url(): String {
        return URL
    }

    private var number = ""
    fun number(number: String): FetchVCodeRequest {
        this.number = number
        return this
    }

    override fun listen(callback: Callback?) {
        setListener(callback)
    }

    companion object {
        private val URL = GifFun.BASE_URL + "/login/fetch_verify_code"
    }

}
