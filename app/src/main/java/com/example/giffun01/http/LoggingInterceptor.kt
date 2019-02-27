package com.example.giffun01.http

import com.example.giffun01.util.logVerbose
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * OkHttp网络请求日志拦截器，通过日志记录OKHttp所有请求以及响应的细节
 * */
class LoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val t1 = System.nanoTime()
        logVerbose(TAG, "Sending request: " + request.url() + "\n" + request.headers())

        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        logVerbose(TAG, "Received response for" + response.request().url() + "in" +
                    (t2 - t1) / 1e6 + "ms\n" + response.headers())
        return response
    }

    companion object {
        val TAG = "LoggingInterceptor"
    }
}
