package com.example.giffun01.callback

import com.example.giffun01.http.response.Response

/**
 * 网络请求响应的回调接口
 * */
interface Callback {

    fun onResponse(response: Response)

    fun onFailure(e: Exception)
}