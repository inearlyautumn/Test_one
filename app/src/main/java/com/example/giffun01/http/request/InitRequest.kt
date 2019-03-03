package com.example.giffun01.http.request

import com.example.giffun01.callback.Callback
import com.example.giffun01.global.GifFun
import com.example.giffun01.http.response.Init
import com.example.giffun01.util.GlobalUtil
import com.example.giffun01.util.NetworkConst
import okhttp3.Headers

/**
 * 初始化请求，对应服务器接口：/init
 * */
class InitRequest : Request() {
    init {
        connectTimeout(5)
        readTimeout(5)
        writeTimeout(5)

    }

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(Init::class.java)
    }

    override fun url(): String {
        return URL
    }

    override fun method(): Int {
        return Request.GET
    }

    override fun params(): Map<String, String>? {
        val params = HashMap<String, String>()
        params[NetworkConst.CLIENT_VERSION] = GlobalUtil.appVersionCode.toString()
        val appChannel = GlobalUtil.getApplicationMetaData("APP_CHANNEL")
        if (appChannel != null) {
            params[NetworkConst.CLIENT_CHANNEL] = appChannel
        }
        if (buildAuthParams(params)) {
            params[NetworkConst.DEVICE_NAME] = deviceName
        }
        return params
    }

    override fun headers(builder: Headers.Builder): Headers.Builder {
        buildAuthHeaders(builder, NetworkConst.UID, NetworkConst.TOKEN)
        return super.headers(builder)
    }

    companion object {
        private val URL = GifFun.BASE_URL + "/init"
    }

}
