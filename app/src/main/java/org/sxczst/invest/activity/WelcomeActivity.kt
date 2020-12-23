package org.sxczst.invest.activity

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import org.sxczst.invest.R

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
    }
}