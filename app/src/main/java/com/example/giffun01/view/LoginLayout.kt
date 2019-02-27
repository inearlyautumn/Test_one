package com.example.giffun01.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class LoginLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    var keyboardShowed = false

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            val width = right - left
            val height = bottom - top
            if (height.toFloat() / width.toFloat() < 4f / 3f) {//如果高宽比小于4：3说明此时健盘弹出
                post {

                }
            }
        }
    }
}