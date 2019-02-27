package com.example.giffun01.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_login.view.*

/**
 * 自定义登录界面Layout，监听布局高度的变化，如果高度比小于4:3说明此时健盘弹出，应改变布局的比例结果经保证
 * 所有元素都不会被健盘遮挡
 * */
class LoginLayout(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    var keyboardShowed = false

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            val width = right - left
            val height = bottom - top
            if (height.toFloat() / width.toFloat() < 4f / 3f) {//如果高宽比小于4：3说明此时健盘弹出
                post {
                    loginBgWallLayout.visibility = View.INVISIBLE
                    val params = loginLayoutTop.layoutParams as LayoutParams
                    params.weight = 1.5f
                    keyboardShowed = true
                    loginLayoutTop.requestLayout()
                }
            } else {
                if (keyboardShowed) {
                    post {
                        loginBgWallLayout.visibility = View.VISIBLE
                        val params = loginLayoutTop.layoutParams as LayoutParams
                        params.weight = 6f
                        loginLayoutTop.requestLayout()
                    }
                }
            }
        }
    }
}