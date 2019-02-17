package com.example.giffun01.util

import android.app.Activity
import java.lang.ref.WeakReference

/**
 * 应用中所有Activity的管理器，可用于一键杀死所有Activity
 * */
object ActivityCollector {
    private const val TAG = "ActivityCollector"

    private val activityList = ArrayList<WeakReference<Activity>?>()

    fun size(): Int {
        return activityList.size
    }

    fun add(weakReference: WeakReference<Activity>) {
        activityList.add(weakReference)
    }

    fun remove(weakReference: WeakReference<Activity>?) {
        val result = activityList.remove(weakReference)
        logDebug(TAG, "remove activity reference $result")
    }

    fun finishAll() {
        if (activityList.isNotEmpty()) {
            for (activityWeakReference in activityList) {
                val activity = activityWeakReference?.get()
                if (activity != null && !activity.isFinishing) {
                    activity.finish()
                }
            }
            activityList.clear()
        }
    }
}
