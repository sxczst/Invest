package org.sxczst.invest.fragment

import android.view.View
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.sxczst.invest.R
import org.sxczst.invest.common.BaseFragment

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/30 19:37
 * @Description :全部理财
 */
class ProductListFragment : BaseFragment() {
    override fun getUrl(): String = ""

    override fun getRequestParams(): RequestParams? = null

    override fun initData(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
        // 使当前的TextView获取焦点。
        tv_product_title.apply {
            isFocusable = true
            isFocusableInTouchMode = true
            requestFocus()
        }
    }

    override fun initTitle(view: View) {
    }

    override fun getLayoutId(): Int = R.layout.fragment_product_list
}