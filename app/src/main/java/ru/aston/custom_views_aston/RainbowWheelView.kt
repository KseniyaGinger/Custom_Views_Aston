package ru.aston.custom_views_aston

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import kotlin.math.min

class RainbowWheelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val colors = listOf(
        Color.RED,
        0xFFFFA500.toInt(),
        Color.YELLOW,
        Color.GREEN,
        Color.CYAN,
        Color.BLUE,
        Color.MAGENTA
    )

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val textSegments = setOf(0, 2, 4, 6)

    private var rotationAngle = 0f
    private var isSpinning = false

    var onSegmentSelected: ((Int) -> Unit)? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val radius = width.coerceAtMost(height) / 2f
        val centerX = width / 2f
        val centerY = height / 2f
        val segmentAngle = 360f / colors.size

        for (i in colors.indices) {
            paint.color = colors[i]
            canvas.drawArc(
                centerX - radius, centerY - radius,
                centerX + radius, centerY + radius,
                rotationAngle + i * segmentAngle,
                segmentAngle, true, paint
            )
        }
    }

    fun spin() {
        if (isSpinning) return

        isSpinning = true
        val randomStopAngle = (360 * 3 + (0..360).random()).toFloat()
        ValueAnimator.ofFloat(rotationAngle, rotationAngle + randomStopAngle).apply {
            duration = 2000
            interpolator = DecelerateInterpolator()
            addUpdateListener { animation ->
                rotationAngle = animation.animatedValue as Float
                invalidate()
            }
            doOnEnd {
                isSpinning = false
                val selectedSegment = ((rotationAngle % 360) / (360f / colors.size)).toInt()
                onSegmentSelected?.invoke(selectedSegment)
            }
            start()
        }
    }
}

