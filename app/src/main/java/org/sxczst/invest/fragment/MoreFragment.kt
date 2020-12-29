package org.sxczst.invest.fragment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.sxczst.invest.R
import org.sxczst.invest.common.AppNetConfig
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
     * 提供请求的URL。
     */
    override fun getUrl(): String = AppNetConfig.BASE_URL

    /**
     * 提供请求传递的参数。
     */
    override fun getRequestParams(): RequestParams? = null

    /**
     * 初始化数据
     */
    override fun initData(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {

    }
}