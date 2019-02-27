package com.example.giffun01.model

import com.example.giffun01.http.FetchVCodeRequest
import com.example.giffun01.http.Response

class FetchVCode : Response() {
    companion object {
        fun getResponse(number: String, callback: Callback) {
            FetchVCodeRequest().number(number).listen(callback)
        }
    }

}
