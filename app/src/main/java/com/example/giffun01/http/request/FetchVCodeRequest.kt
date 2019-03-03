package com.example.giffun01.http.request

import android.net.Network
import com.example.giffun01.global.GifFun
import com.example.giffun01.callback.Callback
import com.example.giffun01.http.response.FetchVCode
import com.example.giffun01.util.NetworkConst
import okhttp3.Headers


/**
 * 获取短信验证吗请求，对应服务器接口：/login/fetch_verify_code
 * */
class FetchVCodeRequest : Request() {

    private var number = ""

    override fun method(): Int {
        return POST
    }

    override fun url(): String {
        return URL
    }

    fun number(number: String): FetchVCodeRequest {
        this.number = number
        return this
    }

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(FetchVCode::class.java)
    }

    override fun params(): Map<String, String>? {
        val params = HashMap<String, String>()
        params[NetworkConst.NUMBER] = number
        params[NetworkConst.DEVICE_NAME] = deviceName
        params[NetworkConst.DEVICE_SERIAL] = deviceSerial
        return params
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder,NetworkConst.DEVICE_NAME,NetworkConst.NUMBER,NetworkConst.DEVICE_SERIAL)
        return super.headers(builder)
    }

    companion object {
        private val URL = GifFun.BASE_URL + "/login/fetch_verify_code"
    }

}
