package com.example.giffun01.model

import com.example.giffun01.http.GetBaseinfoRequest
import com.example.giffun01.http.Response
import com.google.gson.annotations.SerializedName

/**
 * 获取当前登录用户基本信息请求的实体类封装。
 * */
class GetBaseinfo : Response() {
    /**
     * 当前登录用户的昵称
     * */
    var nickname: String = ""

    /**
     * 当前登录用户的头象
     * */
    var avatar: String = ""

    /**
     * 当前登录用户的个人简介
     * */
    var description: String = ""

    /**
     * 当前登录用户个人主页的背景图。
     * */
    @SerializedName("bg_image")
    var bgImage: String = ""

    companion object {
        fun getResponse(callback: Callback) {
            GetBaseinfoRequest().listen(callback)
        }
    }

}
