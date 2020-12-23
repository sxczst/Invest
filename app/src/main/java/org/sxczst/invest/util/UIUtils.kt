package org.sxczst.invest.util

import org.sxczst.invest.common.MyApplication

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/23 20:51
 * @Description :
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

}