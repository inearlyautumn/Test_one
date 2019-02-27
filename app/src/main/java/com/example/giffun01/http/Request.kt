package com.example.giffun01.http

import com.example.giffun01.model.Callback
import com.example.giffun01.util.AuthUtil
import com.example.giffun01.util.NetworkConst
import com.example.giffun01.util.Utility
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.OkHttpClient

/**
 * 网络请求模式的基类，所有的请求封装都应该要继承此类，这里会提供网络模块的配置，
 * 以及请求的具体逻辑等。
 * */
abstract class Request {
    private var callback: Callback? = null

    private lateinit var okHttpClient: OkHttpClient

    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder().addNetworkInterceptor(LoggingInterceptor())

    var getParamsAlready = false

    private var params: Map<String, String>? = null

    lateinit var deviceSerial: String

    abstract fun listen(callback: Callback?)

    /**
     * 构建和服务器身份认证相关的请求参数。
     * @param params 构建参数的param对象
     * @return 如果完成了身份认证参数构建返回true,否则返回false
     * */
    fun buildAuthParams(params: MutableMap<String, String>?): Boolean {
        if (params != null && AuthUtil.isLogin) {
            val userId = AuthUtil.userId.toString()
            val token = AuthUtil.token
            params[NetworkConst.UID] = userId
            params[NetworkConst.DEVICE_SERIAL] = deviceSerial
            params[NetworkConst.TOKEN] = token
            return true
        }
        return false
    }

    /**
     * 设置响应回调接口
     * */
    fun setListener(callback: Callback?) {
        this.callback = callback
    }

    /**
     * 组装网络请求后添加到HTTP发送队列，并监听响应回调
     * @param requestModel 网络请求对应的实体类
     * */
    fun <T : Response> inFlight(requestModel: Class<T>) {
        build()
        val requestBuilder = okhttp3.Request.Builder()
        if (method() == GET && getParams() != null) {
            requestBuilder.url(urlWithParam())
        } else {
            requestBuilder.url(url())
        }
        requestBuilder.headers(headers(Headers.Builder()).build())//---???这个.build()不太懂
        when {
            method() == POST -> requestBuilder.post(formBody())
        }

    }

    open fun headers(builder: Headers.Builder): Headers.Builder {
        builder.add(NetworkConst.HEADER_USER_AGENT, NetworkConst.HEADER_USER_AGENT_VALUE)
        builder.add(NetworkConst.HEADER_APP_VERSION, Utility.appVersion)
        builder.add(NetworkConst.HEADER_APP_SIGN, Utility.appSign)
        return builder
    }

    /**
     * 构建POST、PUT、DELETE请求的参数
     * @return 组装参数后的FormBody
     * */
    private fun formBody(): FormBody {
        val builder = FormBody.Builder()
        val params = getParams()
        if (params != null) {
            val keys = params.keys
            if (!keys.isEmpty()) {
                for (key in keys) {
                    val value = params[key]
                    if (value != null) {
                        builder.add(key, value)
                    }
                }
            }
        }
        return builder.build()
    }

    /**
     * 当GET请求带参数的时候，将参数以key = value的形式拼装到GET请求URL的后面，并且中间以？符号隔开
     * @return 携带参数的URL请求地址
     * */
    private fun urlWithParam(): String {
        val params = getParams()
        if (params != null) {
            val keys = params.keys
            if (!keys.isEmpty()) {
                val paramsBuilder = StringBuilder()
                var needAnd = false

                for (key in keys) {
                    if (needAnd) {
                        paramsBuilder.append("&")
                    }
                    paramsBuilder.append("=").append(params[key])
                    needAnd = true
                }
                return url() + "?" + paramsBuilder.toString()
            }
        }
        return url()
    }

    /**
     * 获取本次请求所携带的所有参数。
     *@return 本次请求所携带的所有参数，以Map形式返回。
     * */
    private fun getParams(): Map<String, String>? {
        if (!getParamsAlready) {
            params = null
            getParamsAlready = true
        }
        return params
    }

    open fun params(): Map<String, String>? {
        return null
    }

    private fun build() {
        okHttpClient = okHttpBuilder.build()
    }

    abstract fun method(): Int
    abstract fun url(): String

    companion object {
        const val GET = 0
        const val POST = 1
        const val PUT = 2
        const val DELETE = 3
    }

}
