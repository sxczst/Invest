package org.sxczst.invest.fragment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.sxczst.invest.R
import org.sxczst.invest.common.BaseFragment

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/22 19:25
 * @Description :更多
 */
class MoreFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_more

    /**
     * 初始化Title
     */
    override fun initTitle(view: View) {
        view.findViewById<ImageView>(R.id.iv_title_back).visibility = View.GONE
        view.findViewById<TextView>(R.id.tv_title).text =
            activity?.resources?.getText(R.string.main_bottom_more)
        view.findViewById<ImageView>(R.id.iv_title_setting).visibility = View.GONE
    }

    /**
     * 初始化数据
     */
    override fun initData() {

    }
}