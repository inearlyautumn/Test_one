package com.example.giffun01

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.example.giffun01.callback.RquestLifecycle
import com.example.giffun01.event.ForceToLoginEvent
import com.example.giffun01.event.MessageEvent
import com.example.giffun01.login.LoginActivity
import com.example.giffun01.util.ActivityCollector
import com.example.giffun01.util.logWarn
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference

/**
 * 应用程序中所有Activity的基类
 * */
open class BaseActivity : AppCompatActivity(), RquestLifecycle {

    //判断当前Activity是否在前台
    protected var isActive: Boolean = false

    //当前Activity的实例
    protected var activity: Activity? = null

    private var weakRefActivity: WeakReference<Activity>? = null

    //Activity中显示加载等待的控件
    private var loading: ProgressBar? = null

    var toolbar: Toolbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        weakRefActivity = WeakReference(this)
        ActivityCollector.add(weakRefActivity!!)
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        isActive = true
    }

    override fun onPause() {
        super.onPause()
        isActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
        activity = null
        ActivityCollector.remove(weakRefActivity)
        EventBus.getDefault().unregister(this)
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setupViews()
    }

    protected open fun setupViews() {
        loading = findViewById(R.id.loading)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 隐藏软键盘
     * */
    fun hideSoftKeyboard() {
        val view = currentFocus
        try {
            if (view != null) {
                val binder = view.windowToken
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        } catch (e: Exception) {
            logWarn(TAG, e.message, e)
        }
    }


    override fun startLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadFinished() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadFailed(msg: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(messageEvent: MessageEvent) {
        if (messageEvent is ForceToLoginEvent) {
            if (isActive) {
                ActivityCollector.finishAll()
                LoginActivity.actionStart(this, false, null)
            }
        }
    }


    companion object {
        private const val TAG = "BaseActivity"
    }
}