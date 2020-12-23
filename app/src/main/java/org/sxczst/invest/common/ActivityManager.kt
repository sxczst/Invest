package org.sxczst.invest.common

import android.app.Activity
import java.util.*

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/23 20:51
 * @Description :
 */
class ActivityManager private constructor() {
    /**
     * 操作Activity的容器
     */
    private var activityStack = Stack<Activity>()

    /**
     * Activity的添加
     */
    fun add(activity: Activity?) {
        activity?.let {
            activityStack.push(it)
        }
    }

    /**
     * 移除指定的Activity
     */
    fun remove(activity: Activity?) {
        for (mActivity in activityStack) {
            activity?.let {
                if (it.javaClass == mActivity.javaClass) {
                    // 销毁当前的Activity。
                    if (!it.isDestroyed) {
                        it.finish()
                    }
                    // 将指定的Activity对象从栈空间移除。
                    activityStack.remove(mActivity)
                }
            }
        }
    }

    /**
     * 移除当前的Activity，即栈顶的Activity。
     */
    fun removeCurrent() {
        activityStack.lastElement().finish()
        activityStack.remove(activityStack.lastElement())
    }

    /**
     * 移除所有的Activity。
     */
    fun removeAll() {
        for (activity in activityStack) {
            activity.finish()
            activityStack.remove(activity)
        }
    }

    /**
     * 返回栈的大小
     */
    fun getSize(): Int = activityStack.size

    companion object {
        // 饿汉式，实现单例
        private val sInstance = ActivityManager()

        fun getInstance(): ActivityManager = sInstance
    }
}