package org.sxczst.invest.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import org.sxczst.invest.R
import org.sxczst.invest.util.UIUtils

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/29 20:04
 * @Description :
 *
 * 1. 提供四种不同界面：
 *  - 正在加载中。
 *  - 加载失败。
 *  - 加载成功，但是数据为空。
 *  - 加载成功，且有数据。
 *
 */
abstract class LoadingPage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    /**
     * 当前显示状态默认为正在加载中。
     */
    private var stateCurrent = STATE_LOADING

    /**
     * 正在加载中的页面。
     */
    private var viewLoading = UIUtils.getView(R.layout.page_loading)

    /**
     * 加载失败的页面。
     */
    private var viewError = UIUtils.getView(R.layout.page_error)

    /**
     * 加载成功，但是数据为空的页面。
     */
    private var viewEmpty = UIUtils.getView(R.layout.page_empty)

    /**
     * 加载成功，且有数据的页面。
     */
    private var viewSuccess: View? = null

    /**
     * 布局显示的参数。
     */
    private val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

    /**
     * 记录联网响应的数据。
     */
    private lateinit var resultState: ResultState

    init {
        addView(viewLoading, params)
        addView(viewError, params)
        addView(viewEmpty, params)
        // 更新界面。
        showSafePage()
    }

    /**
     * 在UI线程中调用，更新界面。
     */
    private fun showSafePage() = UIUtils.runOnUiThread(Runnable { showPage() })

    /**
     * 根据 stateCurrent 的值，决定显示哪一个View。
     */
    private fun showPage() {
        viewLoading.visibility = if (stateCurrent == STATE_LOADING) View.VISIBLE else View.INVISIBLE
        viewError.visibility = if (stateCurrent == STATE_ERROR) View.VISIBLE else View.INVISIBLE
        viewEmpty.visibility = if (stateCurrent == STATE_EMPTY) View.VISIBLE else View.INVISIBLE

        if (viewSuccess == null) {
            viewSuccess = LayoutInflater.from(context).inflate(getSuccessLayoutId(), null)
            addView(viewSuccess, params)
        }

        viewSuccess?.visibility =
            if (stateCurrent == STATE_SUCCESS) View.VISIBLE else View.INVISIBLE
    }

    /**
     * 提供成功页面的布局。
     */
    abstract fun getSuccessLayoutId(): Int

    /**
     * 连接网络加载数据
     */
    fun show() {
        if (url().isEmpty()) {
            resultState = ResultState.SUCCESS
            loadPage()
            return
        }

        // 添加延时，显示加载中的效果。
        UIUtils.getHandler().postDelayed(
            {
                val client = AsyncHttpClient()
                client.get(url(), params(), object : AsyncHttpResponseHandler() {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        responseBody: ByteArray?
                    ) {
                        if (statusCode == 200) {
                            resultState = if (responseBody.toString().isEmpty()) {
                                ResultState.EMPTY.apply {
                                    this.statusCode = statusCode
                                    this.headers = headers
                                    this.responseBody = responseBody
                                }
                            } else {
                                ResultState.SUCCESS.apply {
                                    this.statusCode = statusCode
                                    this.headers = headers
                                    this.responseBody = responseBody
                                }
                            }
                            loadPage()
                        }
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        responseBody: ByteArray?,
                        error: Throwable?
                    ) {
                        resultState = ResultState.ERROR.apply {
                            this.statusCode = statusCode
                            this.headers = headers
                            this.responseBody = responseBody
                        }
                        loadPage()
                    }
                })
            }, 2000
        )
    }

    private fun loadPage() {
        stateCurrent = resultState.state
        showSafePage()
        if (stateCurrent == STATE_SUCCESS) {
            onSuccess(resultState, viewSuccess!!)
        }
    }

    /**
     * 请求的URL。
     */
    abstract fun url(): String

    /**
     * 请求传递的参数。
     */
    abstract fun params(): RequestParams?

    /**
     * 网络响应成功的回调。
     */
    abstract fun onSuccess(
        resultState: ResultState,
        viewSuccess: View
    )

    companion object {
        /**
         * 正在加载中。
         */
        const val STATE_LOADING = 1

        /**
         * 加载失败。
         */
        const val STATE_ERROR = 2

        /**
         * 加载成功，但是数据为空。
         */
        const val STATE_EMPTY = 3

        /**
         * 加载成功，且有数据。
         */
        const val STATE_SUCCESS = 4
    }

    enum class ResultState(
        var state: Int,
        var statusCode: Int = 200,
        var headers: Array<out Header>? = null,
        var responseBody: ByteArray? = null
    ) {
        SUCCESS(STATE_SUCCESS),
        EMPTY(STATE_EMPTY),
        ERROR(STATE_ERROR)
    }
}