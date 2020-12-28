package org.sxczst.invest.ui

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ScrollView
import org.sxczst.invest.util.UIUtils
import kotlin.math.abs

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/28 18:59
 * @Description :自定义ScrollView
 */
class MyScrollView(context: Context?, attrs: AttributeSet?) :
    ScrollView(context, attrs) {
    /**
     * ScrollView，下唯一的子视图。
     */
    private var childView: View? = null

    /**
     * 记录上一次Y轴方向操作的坐标位置。
     */
    private var lastY: Int = 0

    private var lastX: Int = 0

    private var downY: Int = 0

    private var downX: Int = 0

    /**
     * 记录临界状态的左上右下。
     */
    private var normal = Rect()

    /**
     * 判断动画是否结束了
     */
    private var isFinishAnimation = true

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount > 0) {
            childView = getChildAt(0)
        }
    }

    /**
     * 实现父视图对子视图的拦截
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var isIntercept = false
        val eventX = ev?.x?.toInt()
        val eventY = ev?.y?.toInt()
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = eventX ?: 0
                lastX = downX
                downY = eventY ?: 0
                lastY = downY
            }
            MotionEvent.ACTION_MOVE -> {
                // 获取水平和垂直方向的移动距离。
                val absX = abs(eventX ?: 0 - downX)
                val absY = abs(eventY ?: 0 - downY)

                if (absY > absX && absY > UIUtils.dp2px(10)) {
                    isIntercept = true
                }

                lastX = eventX ?: 0
                lastY = eventY ?: 0
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return isIntercept
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (childView == null || !isFinishAnimation) {
            return super.onTouchEvent(ev)
        }

        // 获取当前的Y轴坐标。
        val eventY = ev?.y?.toInt()
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastY = eventY ?: 0
            }
            MotionEvent.ACTION_MOVE -> {
                // 微小的偏移量
                val dy = eventY ?: 0 - lastY
                if (isNeedMove()) {
                    if (normal.isEmpty) {
                        // 记录ChildView的临界状态，左上右下。
                        normal.set(
                            childView?.left!!,
                            childView?.top!!,
                            childView?.right!!,
                            childView?.bottom!!
                        )
                    }
                    // 重新布局
                    childView?.layout(
                        childView?.left!!,
                        childView?.top!! + dy / 2,
                        childView?.right!!,
                        childView?.bottom!! + dy / 2
                    )
                }
                // 重新赋值。
                lastY = eventY ?: 0
            }
            MotionEvent.ACTION_UP -> {
                if (isNeedAnimation()) {
                    // 使用平移动画。
                    val translateY = normal.bottom - childView?.bottom!!
                    val translateAnimation = TranslateAnimation(0F, 0F, 0F, translateY.toFloat())
                    translateAnimation.duration = 2000
                    // 设置动画监听
                    translateAnimation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(animation: Animation?) {
                        }

                        override fun onAnimationEnd(animation: Animation?) {
                            isFinishAnimation = true
                            childView?.clearAnimation()
                            // 重新布局
                            childView?.layout(
                                normal.left,
                                normal.top,
                                normal.right,
                                normal.bottom
                            )
                            // 清除normal
                            normal.setEmpty()
                        }

                        override fun onAnimationStart(animation: Animation?) {
                            isFinishAnimation = false
                        }
                    })
                    // 启动动画
                    childView?.startAnimation(translateAnimation)
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun isNeedAnimation(): Boolean = !normal.isEmpty

    private fun isNeedMove(): Boolean {
        // 获取子视图的高度值。
        Log.i("isNeedMove", "childView.measuredHeight: ${childView?.measuredHeight}")
        // 获取布局的高度值。
        Log.i("isNeedMove", "isNeedMove: $measuredHeight")
        // 获取用户在Y轴方向上的偏移量。
        if (scrollY <= 0 || scrollY >= (childView?.measuredHeight!! - measuredHeight)) {
            return true
        }
        return false
    }
}