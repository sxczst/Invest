package org.sxczst.invest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.sxczst.invest.R

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/22 19:22
 * @Description :首页
 */
class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_home, null)
        return view
    }
}