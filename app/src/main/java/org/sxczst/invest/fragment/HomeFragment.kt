package org.sxczst.invest.fragment

import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.fastjson.JSON
import com.loopj.android.http.RequestParams
import com.squareup.picasso.Picasso
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.ZoomOutPageTransformer
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_home.*
import org.sxczst.invest.R
import org.sxczst.invest.bean.Image
import org.sxczst.invest.bean.Index
import org.sxczst.invest.bean.Product
import org.sxczst.invest.common.AppNetConfig
import org.sxczst.invest.common.BaseFragment

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/22 19:22
 * @Description :首页
 */
class HomeFragment : BaseFragment() {

    /**
     * 网络请求下的数据
     */
    private lateinit var index: Index

    /**
     * 提供布局。
     */
    override fun getLayoutId(): Int = R.layout.fragment_home

    /**
     * 提供请求的URL。
     */
    override fun getUrl(): String = AppNetConfig.INDEX

    /**
     * 提供请求传递的参数。
     */
    override fun getRequestParams(): RequestParams? = null

    /**
     * 初始化数据
     */
    override fun initData(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
        // 响应成功 : 200
        if (statusCode == 200) {
            // 解析Json数据
            val jsonObject = JSON.parseObject(responseBody.toString())
            val proInfo = jsonObject.getString("proInfo")
            val product = JSON.parseObject(proInfo, Product::class.java)
            val imageArr = jsonObject.getString("imageArr")
            val images = JSON.parseArray(imageArr, Image::class.java)
            index = Index(product, images)

            // 更新页面数据
            tv_product_common.text = product.name
            val value = product.progress.toFloat()
            Thread {
                for (progress in 0..value.toInt()) {
                    rp_home.setProgress(progress.toFloat())
                    SystemClock.sleep(20)
                }
            }.start()
            tv_home_year_rate.text = "${product.yearRate}％"

            // 加载显示图片信息
            banner.apply {
                addBannerLifecycleObserver(this@HomeFragment)
                adapter = MyBannerImageAdapter(images)
                indicator = CircleIndicator(context)
                setPageTransformer(ZoomOutPageTransformer())
                setLoopTime(2000)
                start()
            }
        }
    }

    /**
     * 初始化Title
     */
    override fun initTitle(view: View) {
        view.findViewById<ImageView>(R.id.iv_title_back).visibility = View.GONE
        view.findViewById<TextView>(R.id.tv_title).text =
            activity?.resources?.getText(R.string.main_bottom_home)
        view.findViewById<ImageView>(R.id.iv_title_setting).visibility = View.GONE
    }

    /**
     * BannerImageAdapter
     * 使用Picasso库显示网络图片。
     */
    inner class MyBannerImageAdapter(mData: List<Image>) : BannerImageAdapter<Image>(mData) {
        override fun onBindView(
            holder: BannerImageHolder?,
            data: Image?,
            position: Int,
            size: Int
        ) {
            // 图片加载
            Picasso.get().load(
                data?.IMAURL
            ).into(holder?.imageView)
        }
    }
}