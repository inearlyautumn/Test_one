package com.example.giffun01.callback

interface RquestLifecycle {
    fun startLoading()
    fun loadFinished()
    fun loadFailed(msg: String?)
}
