package org.sxczst.invest.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.sxczst.invest.ui.LoadingPage

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/29 19:27
 * @Description :Fragment基类
 */
abstract class BaseFragment : Fragment() {
    /**
     * 带有联网操作
     * 可以显示不同界面
     */
    private lateinit var loadingPage: LoadingPage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadingPage = object : LoadingPage(context!!, null, 0) {
            override fun getSuccessLayoutId(): Int = getLayoutId()
            override fun url(): String = getUrl()
            override fun params(): RequestParams? = getRequestParams()
            override fun onSuccess(
                resultState: ResultState,
                viewSuccess: View
            ) {
                // 初始化Title
                initTitle(viewSuccess)

                // 初始化数据
                initData(resultState.statusCode, resultState.headers, resultState.responseBody)
            }
        }
        return loadingPage
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // 如果有联网需要则开始请求数据。
        loadingPage.show()
    }

    /**
     * 提供请求的URL。
     */
    abstract fun getUrl(): String

    /**
     * 提供请求传递的参数。
     */
    abstract fun getRequestParams(): RequestParams?

    /**
     * 初始化数据
     */
    abstract fun initData(
        statusCode: Int,
        headers: Array<out Header>?,
        responseBody: ByteArray?
    )

    /**
     * 初始化Title
     */
    abstract fun initTitle(view: View)

    /**
     * 提供布局。
     */
    abstract fun getLayoutId(): Int
}