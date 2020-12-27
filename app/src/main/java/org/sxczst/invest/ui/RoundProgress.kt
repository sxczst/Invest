package org.sxczst.invest.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import org.sxczst.invest.R
import org.sxczst.invest.util.UIUtils

/**
 * @Author      :sxczst
 * @Date        :Created in 2020/12/27 13:58
 * @Description :圆形进度条
 */
class RoundProgress : View {

    /**
     * 圆环的颜色
     */
    private var roundColor = Color.GRAY

    /**
     * 圆弧的颜色
     */
    private var roundProgressColor = Color.RED

    /**
     * 文本的颜色
     */
    private var textColor = Color.BLUE

    /**
     * 圆环和圆弧的宽度
     */
    private var roundWidth = UIUtils.dp2px(10)

    /**
     * 文本的字体大小
     */
    private var mTextSize = UIUtils.dp2px(20)

    /**
     * 文本的范围信息
     */
    private var textBounds = Rect()

    /**
     * 圆弧的最大值
     */
    private var maxValue = 100F

    /**
     * 圆弧的进度值
     */
    private var progressValue = 80F

    /**
     * 当前视图的宽度。
     */
    private var mWidth: Int = 0

    /**
     * 当前视图的高度。
     */
    private var mHeight: Int = 0

    /**
     * 画笔
     */
    private var mPaint: Paint = Paint()

    init {
        // 抗锯齿，去毛边。
        mPaint.isAntiAlias = true
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        // 获取自定义属性值

        // 1. 获取TypeArray的对象
        val typedArray =
            context?.obtainStyledAttributes(attrs, R.styleable.RoundProgress)

        // 2. 取出所有的自定义属性
        typedArray?.getColor(R.styleable.RoundProgress_roundColor, roundColor)?.let {
            roundColor = it
        }
        typedArray?.getColor(R.styleable.RoundProgress_roundProgressColor, roundProgressColor)
            ?.let {
                roundProgressColor = it
            }
        typedArray?.getColor(R.styleable.RoundProgress_textColor, textColor)
            ?.let {
                textColor = it
            }
        typedArray?.getDimension(R.styleable.RoundProgress_textSize, -1F)
            ?.let {
                if (it != -1F) {
                    mTextSize = it.toInt()
                }
            }
        typedArray?.getDimension(R.styleable.RoundProgress_roundWidth, -1F)
            ?.let {
                if (it != -1F) {
                    roundWidth = it.toInt()
                }
            }
        typedArray?.getFloat(R.styleable.RoundProgress_maxValue, 100F)
            ?.let {
                maxValue = it
            }
        typedArray?.getFloat(R.styleable.RoundProgress_progressValue, 60F)
            ?.let {
                progressValue = it
            }

        // 3. 回收资源
        typedArray?.recycle()
    }

    /**
     * 测量
     * 1. 获取当前视图的宽高
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 获取当前视图的宽高
        mWidth = measuredWidth
        mHeight = measuredHeight
    }

    override fun onDraw(canvas: Canvas?) {
        // 1. 绘制圆环
        mPaint.apply {
            color = roundColor
            style = Paint.Style.STROKE
            strokeWidth = roundWidth.toFloat()
        }
        canvas?.drawCircle(mWidth / 2F, mHeight / 2F, mWidth / 2F - roundWidth / 2F, mPaint)

        // 2. 绘制圆弧
        mPaint.apply {
            color = roundProgressColor
            strokeCap = Paint.Cap.ROUND
        }
        canvas?.drawArc(
            roundWidth / 2F,
            roundWidth / 2F,
            mWidth - roundWidth / 2F,
            mHeight - roundWidth / 2F,
            0F, progressValue * 360 / maxValue, false, mPaint
        )

        // 3. 绘制文本
        mPaint.apply {
            style = Paint.Style.FILL
            strokeWidth = 0F
            color = textColor
            textSize = mTextSize.toFloat()
        }
        val text = "${progressValue * 100 / maxValue}%"
        mPaint.getTextBounds(text, 0, text.length, textBounds)
        canvas?.drawText(
            text,
            mWidth / 2F - textBounds.width() / 2F,
            mHeight.toFloat() / 2F + textBounds.height().toFloat() / 2F,
            mPaint
        )
    }

    /**
     * 更新进度信息
     */
    fun setProgress(value: Float) {
        progressValue = value
        postInvalidate()
    }
}