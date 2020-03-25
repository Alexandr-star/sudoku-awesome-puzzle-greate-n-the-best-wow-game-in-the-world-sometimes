package com.app.game.sudoku.ui.gameboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.app.game.sudoku.back.Cell

class GameboardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet){

    fun GameboardView() {

    }

    private var sqrtSize = 3
    private var size = 9

    private var cellSizePixels = 0F

    private var selectedCall = 0
    private var selectedRow = 0

    private var listener: GameboardView.OnTouchListener? = null

    private var cells: List<Cell>? = null

    interface OnTouchListener {
        fun onCellTouched(row: Int, col: Int )
    }

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

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textSize = 24F
    }

    override  fun onDraw(canvas: Canvas) {
        cellSizePixels = (width / size).toFloat()

        fillCells(canvas)
        drawLines(canvas)
        drawText(canvas)
        
    }

    private fun drawText(canvas: Canvas) {
        cells?.forEach {
            val row = it.row
            val col = it.col
            val stringValue = it.value.toString()
            val textBounds = Rect()
            textPaint.getTextBounds(stringValue, 0, stringValue.length, textBounds)
            val textWidth = textPaint.measureText(stringValue)
            val textHeidth = textBounds.height()

            canvas.drawText(
                stringValue,
                (col * cellSizePixels) + cellSizePixels / 2 - textWidth /2,
                (row * cellSizePixels) + cellSizePixels / 2 - textHeidth / 2,
                textPaint
            )
        }
    }

    private fun fillCells(canvas: Canvas) {
        cells?.forEach {
            val row = it.row
            val col = it.col

            if (row == selectedRow && col == selectedCall) {
                fillCell(canvas, row, col, selectedCellPaint)
            } else if (row == selectedRow || col == selectedCall) {
                fillCell(canvas, row, col, conflictedCellPaint)
            } else if (row / sqrtSize == selectedRow / sqrtSize && col / sqrtSize == selectedCall / sqrtSize) {
                fillCell(canvas, row, col, conflictedCellPaint)
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
        val possibleSelectedRow = (y / cellSizePixels).toInt()
        val possibleSelectedCall = (x / cellSizePixels).toInt()
        listener?.onCellTouched(possibleSelectedRow, possibleSelectedCall)
    }

    fun updateSelectedCellUI(row: Int, col: Int) {
        selectedCall = col
        selectedRow = row
        invalidate()
    }

    fun registerListener(listener: OnTouchListener) {
        this.listener = listener
    }

    fun updateCells(cells: List<Cell>) {
        this.cells = cells
        invalidate()
    }

}

