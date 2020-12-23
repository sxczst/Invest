package org.sxczst.invest.common

import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import org.sxczst.invest.util.UIUtils
import kotlin.system.exitProcess

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/23 20:52
 * @Description : 捕获全局程序中未捕获的异常
 *
 * 解决了两个问题：
 * 1. 当程序出现未捕获异常时，能够给用户一个相对友好的提示。
 * 2. 在出现异常时，能够将异常信息发送给后台，便于在后续的版本中解决Bug。
 */
class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {
    /**
     * 系统中默认的处理未捕获异常的处理器。
     */
    private lateinit var defaultUncaughtExceptionHandler: Thread.UncaughtExceptionHandler

    fun init() {
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 将当前类设置为出现未捕获异常的处理器
        Thread.setDefaultUncaughtExceptionHandler(this@CrashHandler)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        Thread {
            Looper.prepare()
            // 亲，程序出现了未捕获的异常！
            Toast.makeText(UIUtils.getContext(), "亲，程序出现了未捕获的异常！", Toast.LENGTH_SHORT).show()
            Looper.loop()
        }.start()
        // 收集异常信息
        collectionException(e)
        try {
            Thread.sleep(2000)
            // 移除当前Activity
            ActivityManager.getInstance().removeAll()
            // 结束当前的进程
            android.os.Process.killProcess(android.os.Process.myPid())
            // 结束当前的虚拟机的执行
            exitProcess(0)
        } catch (e: Exception) {
        }
    }

    /**
     * 收集异常信息
     */
    private fun collectionException(e: Throwable) {
        // 收集具体的客户设备信息
        val message = "${Build.DEVICE}:${Build.MODEL}:${Build.PRODUCT}:${Build.VERSION.SDK_INT}"
        // 异常信息发送给后台
        Thread {
            // 需要按照指定的Url，将异常信息发送给后台。
            Log.e("CrashHandler", "collectionException: $e")
            Log.e("CrashHandler", "collectionException: $message")
        }.start()
    }

    companion object {
        private var crashHandler: CrashHandler? = null
            get() {
                if (field == null) field = CrashHandler()
                return field
            }

        /**
         * 单例
         */
        fun getInstance(): CrashHandler = crashHandler!!
    }
}