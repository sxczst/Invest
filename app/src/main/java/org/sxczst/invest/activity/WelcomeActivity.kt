package org.sxczst.invest.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import org.sxczst.invest.R
import org.sxczst.invest.common.ActivityManager

/**
 * 欢迎页面
 */
class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 去除掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        // 隐藏顶部状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // 隐藏ActionBar
        supportActionBar?.hide()
        setContentView(R.layout.activity_welcome)
        // 将当前的Activity添加到ActivityManager中
        ActivityManager.getInstance().add(this@WelcomeActivity)
        // 启动 动画
        startAnimation()
    }

    /**
     * 启动 动画
     */
    private fun startAnimation() {
        val alphaAnimation = AlphaAnimation(0.5F, 1F)
        alphaAnimation.duration = 3_000
        // 设置动画的变化率
        alphaAnimation.interpolator = AccelerateInterpolator()
        // 设置动画的监听
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                // 结束Activity的显示，并从栈空间移除。
                ActivityManager.getInstance().remove(this@WelcomeActivity)
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        // 启动 动画
        cl_welcome.startAnimation(alphaAnimation)
    }
}