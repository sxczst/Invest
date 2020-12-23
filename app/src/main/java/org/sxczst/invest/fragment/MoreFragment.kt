package org.sxczst.invest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import org.sxczst.invest.R

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/22 19:25
 * @Description :更多
 */
class MoreFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_more, null)
        // 初始化Title
        initTitle(view)
        return view
    }

    /**
     * 初始化Title
     */
    private fun initTitle(view: View) {
        view.findViewById<ImageView>(R.id.iv_title_back).visibility = View.GONE
        view.findViewById<TextView>(R.id.tv_title).text =
            activity?.resources?.getText(R.string.main_bottom_more)
        view.findViewById<ImageView>(R.id.iv_title_setting).visibility = View.GONE
    }
}