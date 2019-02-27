package com.example.giffun01.http

import com.example.giffun01.global.GifFun
import com.example.giffun01.model.Callback

class PhoneLoginRequest : Request() {
    override fun method(): Int {
        return Request.POST
    }

    override fun url(): String {
        return URL
    }

    private var number: String = ""
    private var code: String = ""

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(PhoneLogin::class.java)
    }

    companion object {
        private val URL = GifFun.BASE_URL + "/login/phone"
    }
}
