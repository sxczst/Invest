package org.sxczst.invest.fragment

import android.view.View
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.sxczst.invest.R
import org.sxczst.invest.common.BaseFragment

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/30 19:37
 * @Description :热门理财
 */
class ProductHotFragment : BaseFragment() {
    override fun getUrl(): String = ""

    override fun getRequestParams(): RequestParams? = null

    override fun initData(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
    }

    override fun initTitle(view: View) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_product_hot
}