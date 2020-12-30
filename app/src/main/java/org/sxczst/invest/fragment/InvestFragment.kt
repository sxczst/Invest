package org.sxczst.invest.fragment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_invest.*
import org.sxczst.invest.R
import org.sxczst.invest.common.AppNetConfig
import org.sxczst.invest.common.BaseFragment
import org.sxczst.invest.util.UIUtils

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/22 19:23
 * @Description :投资
 */
class InvestFragment : BaseFragment() {
    /**
     * 记录三个不同的Fragment。
     */
    private val fragmentList = mutableListOf<Fragment>()

    /**
     * 记录三个不同Fragment的Title。
     */
    private val fragmentPageTitleList = mutableListOf<String>()

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
        // 1. 加载三个不同的Fragment : ProductListFragment，ProductRecommendFragment，ProductHotFragment。
        initFragments()
        // 2. ViewPager 设置三个Fragment的显示。
        val adapter = fragmentManager?.let { MyAdapter(it) }
        vp_invest.adapter = adapter
        // 3. 绑定 ViewPager 与 TabLayout
        tl_invest.setupWithViewPager(vp_invest)
    }

    /**
     * 加载三个不同的Fragment。
     */
    private fun initFragments() {
        val productListFragment = ProductListFragment()
        val productRecommendFragment = ProductRecommendFragment()
        val productHotFragment = ProductHotFragment()
        // 添加到集合中。
        fragmentList.add(productListFragment)
        fragmentPageTitleList.add(UIUtils.getText(R.string.invest_product_list))
        fragmentList.add(productRecommendFragment)
        fragmentPageTitleList.add(UIUtils.getText(R.string.invest_product_recommend))
        fragmentList.add(productHotFragment)
        fragmentPageTitleList.add(UIUtils.getText(R.string.invest_product_hot))
    }

    inner class MyAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment = fragmentList[position]
        override fun getCount(): Int = fragmentList.size
        override fun getPageTitle(position: Int): CharSequence? = fragmentPageTitleList[position]
    }
}