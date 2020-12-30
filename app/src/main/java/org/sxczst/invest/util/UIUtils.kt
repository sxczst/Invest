package org.sxczst.invest.util

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import org.sxczst.invest.common.MyApplication

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/23 20:51
 * @Description : 专门提供为处理一些UI相关的问题而创建的工具类，
 *               提供资源获取的通用方法，
 *               避免每次都写重复的代码。
 */
object UIUtils {
    /**
     * 获取程序需要的上下文
     */
    fun getContext() = MyApplication.context

    /**
     * 获取程序需要的消息处理器的对象
     */
    fun getHandler() = MyApplication.handler

    /**
     * 返回指定colorId对应的颜色值
     */
    fun getColor(@ColorRes colorId: Int) = getContext().resources.getColor(colorId)

    /**
     * dp -> px
     */
    fun dp2px(dp: Int): Int {
        val density = getContext().resources.displayMetrics.density
        return (density * dp + 0.5).toInt()
    }

    /**
     * 通过 applicationContext 填充出新的布局。
     */
    fun getView(@LayoutRes layoutId: Int): View =
        LayoutInflater.from(getContext()).inflate(layoutId, null)

    /**
     * @param runnable 使它在主线程中执行。
     */
    fun runOnUiThread(runnable: Runnable) {
        if (isInMainThread()) {
            runnable.run()
        } else {
            getHandler().post(runnable)
        }
    }

    /**
     * 判断当前线程是否是主线程。
     */
    fun isInMainThread(): Boolean = MyApplication.mainThreadId == android.os.Process.myTid()

    /**
     * 返回指定Id的字符串数组。
     */
    fun getStringArray(@ArrayRes id: Int) = getContext().resources.getStringArray(id)

}