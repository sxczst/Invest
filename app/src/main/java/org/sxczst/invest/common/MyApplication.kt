package org.sxczst.invest.common

import android.app.Application
import android.content.Context
import android.os.Handler

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/23 20:51
 * @Description :
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        handler = Handler()
        // 实例化当前Application的线程即为主线程。
        mainThread = Thread.currentThread()
        // 获取当前线程的Id
        mainThreadId = android.os.Process.myTid()

        // 设置未捕获异常的处理器
        CrashHandler.getInstance().init()
    }

    companion object {
        /**
         * 需要使用的上下文对象
         */
        lateinit var context: Context

        /**
         * 需要使用的Handler
         */
        lateinit var handler: Handler

        /**
         * 提供主线程对象
         */
        lateinit var mainThread: Thread

        /**
         * 提供主线程对象的Id
         */
        var mainThreadId = 0
    }
}