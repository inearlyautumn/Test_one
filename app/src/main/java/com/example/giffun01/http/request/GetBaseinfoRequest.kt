package com.example.giffun01.http.request

import com.example.giffun01.global.GifFun
import com.example.giffun01.callback.Callback
import com.example.giffun01.http.response.GetBaseinfo

/**
 * 获取当前用户的基本信息请求，对应服务器接口：/user/baseinfo
 * */
class GetBaseinfoRequest : Request() {

    override fun listen(callback: Callback?) {
        setListener(callback)
        inFlight(GetBaseinfo::class.java)
    }

    override fun method(): Int {
        return Request.GET
    }

    override fun url(): String {
        return URL
    }

    override fun params(): Map<String, String>? {
        val params = HashMap<String, String>()
        return if (buildAuthParams(params)) {
            params
        } else super.params()
    }

    companion object {
        private val URL = GifFun.BASE_URL + "/user/baseinfo"
    }

}
