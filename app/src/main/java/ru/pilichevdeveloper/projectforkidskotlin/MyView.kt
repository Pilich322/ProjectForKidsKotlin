package ru.pilichevdeveloper.projectforkidskotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import kotlin.random.Random

class MyView(context: Context) : View(context) {

    private val BALLS_COUNT = 100
    private val BALL_RADIUS = 20f
    private val x = FloatArray(BALLS_COUNT)
    private val y = FloatArray(BALLS_COUNT)
    private val vx = FloatArray(BALLS_COUNT)
    private val vy = FloatArray(BALLS_COUNT)

    private val paint = Paint().apply {
        color = Color.RED
        isAntiAlias = true
    }

    init {
        postInvalidateOnAnimation() // Запуск первой перерисовки
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initializeBalls()
    }

    private fun initializeBalls() {
        for (i in 0 until BALLS_COUNT) {
            // Устанавливаем начальные координаты с учетом радиуса
            x[i] = rand(BALL_RADIUS, width - BALL_RADIUS)
            y[i] = rand(BALL_RADIUS, height - BALL_RADIUS)
            vx[i] = rand(-10f, 10f)
            vy[i] = rand(-10f, 10f)
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBalls(canvas)
        addValues(x, vx)
        addValues(y, vy)
        postInvalidateOnAnimation()
    }

    private fun rand(min: Float, max: Float): Float {
        return Random.nextFloat() * (max - min) + min
    }

    private fun addValues(array: FloatArray, values: FloatArray) {
        for (i in 0 until BALLS_COUNT) {
            array[i] += values[i]
            // шары остаются в пределах холста
            if (array[i] < 0 || array[i] > if (array === x) width else height)
                values[i] = -values[i]
        }
    }

    private fun drawBalls(canvas: Canvas) {
        for (i in 0 until BALLS_COUNT) {
            canvas.drawCircle(x[i], y[i], BALL_RADIUS, paint)
        }
    }
}