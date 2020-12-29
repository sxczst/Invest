package org.sxczst.invest.fragment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.sxczst.invest.R
import org.sxczst.invest.common.BaseFragment

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/22 19:23
 * @Description :投资
 */
class InvestFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_invest

    /**
     * 初始化Title
     */
    override fun initTitle(view: View) {
        view.findViewById<ImageView>(R.id.iv_title_back).visibility = View.GONE
        view.findViewById<TextView>(R.id.tv_title).text =
            activity?.resources?.getText(R.string.main_bottom_invest)
        view.findViewById<ImageView>(R.id.iv_title_setting).visibility = View.GONE
    }

    /**
     * 初始化数据
     */
    override fun initData() {

    }
}