package com.app.game.sudoku.ui.gameboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class GameboardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet){

    private var sqrtSize = 3
    private var size = 9

    private var cellSizePixels = 0F

    private var selectedCall = -1
    private var selectedRow = -1

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#3ee6b9")
        strokeWidth = 10F
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.parseColor("#3ee6b9")
        strokeWidth = 4F
    }

    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#3ee6b9")
    }

    private val conflictedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#daf7f7")
    }

    override  fun onDraw(canvas: Canvas) {
        cellSizePixels = (width / size).toFloat()

        fillCells(canvas)
        drawLines(canvas)
    }

    private fun fillCells(canvas: Canvas) {
        if (selectedCall == -1 || selectedRow == -1) return

        for (r in 0..size) {
            for (c in 0..size) {
                if (r == selectedRow && c == selectedCall) {
                    fillCell(canvas, r, c, selectedCellPaint)
                } else if (r == selectedRow || c == selectedCall) {
                    fillCell(canvas, r, c, conflictedCellPaint)
                } else if (r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedCall / sqrtSize) {
                    fillCell(canvas, r, c, conflictedCellPaint)
                }
            }
        }
    }

    private fun fillCell(canvas: Canvas, r: Int, c: Int, paint: Paint) {
        canvas.drawRect(
            c * cellSizePixels,
            r * cellSizePixels,
            (c + 1) * cellSizePixels,
            (r + 1) * cellSizePixels,
            paint
        )
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = Math.min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }

    private fun drawLines(canvas: Canvas) {
        canvas.drawRect(0F, 0F, width.toFloat(), width.toFloat(), thickLinePaint)
        for (i in 1 until size) {
            val paintToUse = when (1 % sqrtSize) {
                0 -> thickLinePaint
                else -> thinLinePaint
            }
            canvas.drawLine(
                i * cellSizePixels,
                0F,
                i * cellSizePixels,
                height.toFloat(),
                paintToUse
            )

            canvas.drawLine(
                0F,
                i * cellSizePixels,
                width.toFloat(),
                i * cellSizePixels,
                paintToUse
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false
        }
    }

    private fun handleTouchEvent(x: Float, y: Float) {
        selectedRow = (y / cellSizePixels).toInt()
        selectedCall = (x / cellSizePixels).toInt()
        invalidate()
    }
}