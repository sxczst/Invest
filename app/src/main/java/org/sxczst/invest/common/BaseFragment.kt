package org.sxczst.invest.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.sxczst.invest.util.UIUtils

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/29 19:27
 * @Description :Fragment基类
 */
abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(activity).inflate(getLayoutId(), null)

        // val view = UIUtils.getView(getLayoutId())

        // 初始化Title
        initTitle(view)

        // 初始化数据
        initData()

        return view
    }

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化Title
     */
    abstract fun initTitle(view: View)

    /**
     * 提供布局。
     */
    abstract fun getLayoutId(): Int
}