package com.parhambaghebani.utility

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.parhambaghebani.R


/**
 * https://github.com/2hamed/ProgressCircula
 * com.hmomeni.progresscircula:progresscircula:0.2.1
 */
class CustomProgressCircular(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attributeSet, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attributeSet: AttributeSet? = null) : this(context, attributeSet, 0) {
        val theme = context.theme.obtainStyledAttributes(attributeSet, R.styleable.ProgressCircular, 0, 0)

        try {
            progress = theme.getInteger(R.styleable.ProgressCircular_pgc_progress, progress)
            showProgress = theme.getBoolean(R.styleable.ProgressCircular_pgc_showProgress, showProgress)
            indeterminate = theme.getBoolean(R.styleable.ProgressCircular_pgc_indeterminate, indeterminate)
            rimColor = theme.getInteger(R.styleable.ProgressCircular_pgc_rimColor, rimColor)
            rimWidth = theme.getDimension(R.styleable.ProgressCircular_pgc_rimWidth, rimWidth)
            textColor = theme.getInteger(R.styleable.ProgressCircular_pgc_textColor, textColor)
            speed = theme.getFloat(R.styleable.ProgressCircular_pgc_speed, speed)
        } finally {
            theme.recycle()
        }
    }

    private val oval = RectF()
    private val textBounds = Rect()
    private var step = 0f
    private var isRotating = true
    private var currentProgress = 0

    var progress = 0
        set(value) {
            field = value
            indeterminate = false
            if (value < 100) {
                isRotating = true
                postInvalidate()
            }
        }

    var anim = true
        set(value) {
            field = value
            postInvalidate()
        }


    var indeterminate = true
        set(value) {
            field = value
            if (value) {
                showProgress = false
                isRotating = true
                postInvalidate()
            }
        }
    var showProgress = false
        set(value) {
            field = value
            postInvalidate()
        }
    var textColor = Color.BLACK
        set(value) {
            field = value
            textPaint.color = value
        }

    var rimColor = Color.BLACK
        set(value) {
            field = value
            outerRim.color = value
        }

    var rimWidth = 4F.toPX()
        set(value) {
            field = value
            outerRim.strokeWidth = value
        }

    var speed = 1.5f

    private val outerRim = Paint().apply {
        color = rimColor
        strokeWidth = rimWidth
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
    }
    private val textPaint = Paint().apply {
        color = textColor
        textAlign = Paint.Align.CENTER
        textSize = 16F.toPX()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!indeterminate) {
            step += 3 * speed
        }

        val width = width.toFloat()
        val height = height.toFloat()
        val radius: Float

        radius = if (width > height) {
            height / 2
        } else {
            width / 2
        } - paddingBottom - (rimWidth / 2) // subtracting (rimWidth / 2) so that the arc doesn't get out of the window by half

        val centerX = width / 2
        val centerY = height / 2

        oval.set(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius
        )
        calculateStartAngle()
        calculateSweepAngle()
        canvas.drawArc(oval, startAngle, sweepAngle, false, outerRim)
        if (isRotating)
            postInvalidate()
        if (step >= 360) {
            step = 0f
        }

        if (showProgress) {
            val text = "$currentProgress%"
            textPaint.getTextBounds(text, 0, text.length, textBounds)
            canvas.drawText(text, centerX, centerY - textBounds.exactCenterY(), textPaint)
        }
    }

    private var isIncrement = true
    private var sweepAngle: Float = 0f
    private var sweepStep = 4

    private fun calculateSweepAngle() {
        if (!indeterminate) {
            if (anim) {
                if (currentProgress < progress) {
                    currentProgress++
                } else if (currentProgress > progress) {
                    currentProgress--
                }
            } else {
                currentProgress = progress
            }
            if (currentProgress >= 100) {
                isRotating = false
            }
            sweepAngle = currentProgress * 360 / 100F
        } else {
            if (isIncrement) {
                currentProgress++
                sweepAngle += sweepStep * speed
            } else {
                currentProgress--
                sweepAngle -= sweepStep * speed
            }

            if (sweepAngle >= 360) {
                isIncrement = false
            } else if (sweepAngle <= 0) {
                isIncrement = true
            }
        }
    }

    private fun calculateStartAngle() {
        if (!indeterminate) {
            startAngle = step % 360F
            if (step > 360) {
                step = 0f
            }
        } else {
            startAngle += if (!isIncrement) {
                sweepStep * 2
            } else {
                sweepStep
            } * speed
        }
        startAngle %= 360f
    }

    private var startAngle: Float = 0f

    fun startRotation() {
        isRotating = true
        postInvalidate()
    }

    fun stopRotation() {
        isRotating = false
    }
}