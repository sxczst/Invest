package org.sxczst.invest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.alibaba.fastjson.JSON
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.squareup.picasso.Picasso
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_home.*
import org.sxczst.invest.R
import org.sxczst.invest.bean.Image
import org.sxczst.invest.bean.Index
import org.sxczst.invest.bean.Product
import org.sxczst.invest.common.AppNetConfig
import org.sxczst.invest.util.UIUtils

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/22 19:22
 * @Description :首页
 */
class HomeFragment : Fragment() {

    /**
     * 网络请求下的数据
     */
    private lateinit var index: Index

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_home, null)
        // 初始化Title
        initTitle(view)
        // 初始化数据
        initData()
        return view
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        val client = AsyncHttpClient()
        client.post(AppNetConfig.INDEX, object : AsyncHttpResponseHandler() {
            /**
             * 响应成功
             */
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
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
                    tv_home_year_rate.text = "${product.yearRate}％"

                    // 设置ViewPager
                    vp_home.adapter = MyAdapter()
                    // 设置指示器
                    tb_home_indicator.setupWithViewPager(vp_home)
                }

            }


            /**
             * 响应失败
             */
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Toast.makeText(UIUtils.getContext(), "联网获取数据失败", Toast.LENGTH_SHORT).show()
            }

        })
    }

    /**
     * 初始化Title
     */
    private fun initTitle(view: View) {
        view.findViewById<ImageView>(R.id.iv_title_back).visibility = View.GONE
        view.findViewById<TextView>(R.id.tv_title).text =
            activity?.resources?.getText(R.string.main_bottom_home)
        view.findViewById<ImageView>(R.id.iv_title_setting).visibility = View.GONE
    }

    inner class MyAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

        override fun getCount(): Int = 3

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imageView = ImageView(activity)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            // 1. 显示图片
            Picasso.get().load(
                index.images[position].IMAURL
            ).into(imageView)
            // 2. 添加到容器中
            container.addView(imageView)
            return imageView
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            // 移除操作
            container.removeView(`object` as View)
        }
    }
}