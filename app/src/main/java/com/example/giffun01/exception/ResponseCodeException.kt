package com.example.giffun01.exception

class ResponseCodeException(val responseCode: Int) :
    RuntimeException("Http request failed with response code $responseCode") {
}