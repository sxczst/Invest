package org.sxczst.invest.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.main_bottom.*
import org.sxczst.invest.R
import org.sxczst.invest.fragment.HomeFragment
import org.sxczst.invest.fragment.InvestFragment
import org.sxczst.invest.fragment.MeFragment
import org.sxczst.invest.fragment.MoreFragment

class MainActivity : AppCompatActivity() {
    private lateinit var transaction: FragmentTransaction
    private var homeFragment: HomeFragment? = null
    private var investFragment: InvestFragment? = null
    private var meFragment: MeFragment? = null
    private var moreFragment: MoreFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setBottomTabListener()
        // 默认加载首页Tab。
        setSelect(0)
    }

    /**
     * 配置底部四个Tab按钮的点击事件
     */
    private fun setBottomTabListener() {
        iv_main_home.setOnClickListener {
            showTab(it)
        }
        iv_main_invest.setOnClickListener {
            showTab(it)
        }
        iv_main_me.setOnClickListener {
            showTab(it)
        }
        iv_main_more.setOnClickListener {
            showTab(it)
        }
    }

    /**
     * 根据传递进来的View的Id，来判断显示哪一个Fragment。
     *
     * @param view 传递进来的被点击的View对象。
     */
    fun showTab(view: View) {
        when (view.id) {
            R.id.iv_main_home -> {
                // 首页
                setSelect(0)
            }
            R.id.iv_main_invest -> {
                // 投资
                setSelect(1)
            }
            R.id.iv_main_me -> {
                // 我的资产
                setSelect(2)
            }
            R.id.iv_main_more -> {
                // 更多
                setSelect(3)
            }
        }
    }

    /**
     * 配置相应的Fragment的显示
     */
    private fun setSelect(i: Int) {
        transaction = supportFragmentManager.beginTransaction()

        // 隐藏所有 Fragment 的显示
        hideFragments()
        // 重置Tab的图片和文字颜色
        resetTab()

        when (i) {
            0 -> {
                if (homeFragment == null) {
                    homeFragment = HomeFragment()
                    transaction.add(R.id.fl_main, homeFragment!!)
                }
                homeFragment?.let {
                    // 显示当前的Fragment
                    transaction.show(it)
                }

                // 改变选中项的图片和文字颜色
                iv_main_home.setImageResource(R.drawable.bottom02)
                tv_main_home.setTextColor(resources.getColor(R.color.main_bottom_title_selected))
            }
            1 -> {
                if (investFragment == null) {
                    investFragment = InvestFragment()
                    transaction.add(R.id.fl_main, investFragment!!)
                }
                investFragment?.let {
                    // 显示当前的Fragment
                    transaction.show(it)
                }

                // 改变选中项的图片和文字颜色
                iv_main_invest.setImageResource(R.drawable.bottom04)
                tv_main_invest.setTextColor(resources.getColor(R.color.main_bottom_title_selected))
            }
            2 -> {
                if (meFragment == null) {
                    meFragment = MeFragment()
                    transaction.add(R.id.fl_main, meFragment!!)
                }
                meFragment?.let {
                    // 显示当前的Fragment
                    transaction.show(it)
                }

                // 改变选中项的图片和文字颜色
                iv_main_me.setImageResource(R.drawable.bottom06)
                tv_main_me.setTextColor(resources.getColor(R.color.main_bottom_title_selected))
            }
            3 -> {
                if (moreFragment == null) {
                    moreFragment = MoreFragment()
                    transaction.add(R.id.fl_main, moreFragment!!)
                }
                moreFragment?.let {
                    // 显示当前的Fragment
                    transaction.show(it)
                }

                // 改变选中项的图片和文字颜色
                iv_main_more.setImageResource(R.drawable.bottom08)
                tv_main_more.setTextColor(resources.getColor(R.color.main_bottom_title_selected))
            }
        }
        // 提交事务
        transaction.commit()
    }

    /**
     * 重置Tab的图片和文字颜色
     */
    private fun resetTab() {
        iv_main_home.setImageResource(R.drawable.bottom01)
        iv_main_invest.setImageResource(R.drawable.bottom03)
        iv_main_me.setImageResource(R.drawable.bottom05)
        iv_main_more.setImageResource(R.drawable.bottom07)

        tv_main_home.setTextColor(resources.getColor(R.color.main_bottom_title_un_selected))
        tv_main_invest.setTextColor(resources.getColor(R.color.main_bottom_title_un_selected))
        tv_main_me.setTextColor(resources.getColor(R.color.main_bottom_title_un_selected))
        tv_main_more.setTextColor(resources.getColor(R.color.main_bottom_title_un_selected))
    }

    /**
     * 隐藏所有 Fragment 的显示
     */
    private fun hideFragments() {
        homeFragment?.let {
            transaction.hide(it)
        }
        investFragment?.let {
            transaction.hide(it)
        }
        meFragment?.let {
            transaction.hide(it)
        }
        moreFragment?.let {
            transaction.hide(it)
        }
    }
}