package com.example.giffun01.util

import com.example.giffun01.R
import com.example.giffun01.event.ForceToLoginEvent
import com.example.giffun01.exception.ResponseCodeException
import com.example.giffun01.global.GifFun
import com.example.giffun01.http.response.Response
import com.example.giffun01.util.GlobalUtil.showToastOnUiThread
import org.greenrobot.eventbus.EventBus
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException

/**
 * 对服务器的返回进行相应的逻辑处理。注意此类只处理公众的返回逻辑，涉及具有业务逻辑，
 * 仍然交由接口调用处理自行处理
 * */
object ResponseHandler {
    /**
     * 当网络请求没有正常响应的时候，根据异常类型进行相应的处理
     * 异常实体类
     * */
    fun handleFailure(e: Exception) {
        when (e) {
            is ConnectException -> showToastOnUiThread(GlobalUtil.getString(R.string.network_connect_error))
            is SocketTimeoutException -> showToastOnUiThread(GlobalUtil.getString(R.string.network_connect_timeout))
            is ResponseCodeException -> showToastOnUiThread(GlobalUtil.getString(R.string.network_response_code_error) + e.responseCode)
            is NoRouteToHostException -> showToastOnUiThread(GlobalUtil.getString(R.string.no_route_to_host))
            else -> {
                logWarn(TAG, "handleFailure exception is $e")
                showToastOnUiThread(GlobalUtil.getString(R.string.unknown_error))
            }
        }
    }

    /**
     * 当网络请求正确响应的时候，根据状态处理通用部分的逻辑
     * @param response 响应实体类
     * @return 如果已经将该响应处理掉了，返回true,否则返回false。
     * */
    fun handleResponse(response: Response?): Boolean {
        if (response == null) {
            logWarn(TAG, "handleResponse:response is null")
            showToastOnUiThread(GlobalUtil.getString(R.string.unknown_error))
            return true
        }
        val status = response.status
        when (status) {
            10001, 10002, 10003 -> {
                logWarn(TAG, "handleResponse: status code is $status")
                GifFun.logout()
                showToastOnUiThread(GlobalUtil.getString(R.string.login_status_expired))
                val event = ForceToLoginEvent()
                EventBus.getDefault().post(event)
                return true
            }
            19000 -> {
                logWarn(TAG, "handleResponse: status code is 19000")
                showToastOnUiThread(GlobalUtil.getString(R.string.unknown_error))
                return true
            }
            else -> return false
        }
    }

    private val TAG = "ResponseHandler"


}