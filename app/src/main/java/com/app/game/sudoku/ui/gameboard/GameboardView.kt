package com.app.game.sudoku.ui.gameboard

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.app.game.sudoku.back.Cell
import kotlin.math.min

class GameboardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet){

    private var sqrtSize = 3
    private var size = 9

    private var cellSizePixels = 0F
    private var noteSizePixels = 0F

    private var selectedCall = 0
    private var selectedRow = 0

    private var listener: GameboardView.OnTouchListener? = null

    private var cells: List<Cell>? = null
    private var  mistakesCell: Cell? = null
    private var mistakesBe: Boolean = false

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
        color = Color.parseColor("#5bdede")
    }

    private val textPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textSize = 32F
    }

    private val startingCellTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
        textSize = 32F
        typeface = Typeface.DEFAULT_BOLD
    }

    private val startingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#daf7f7")
    }

    private val noteTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.BLACK
    }

    private val mistekesTextPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.RED
        textSize = 32F
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }

    override  fun onDraw(canvas: Canvas) {
        updateMeasure(width)

        fillCells(canvas)
        drawLines(canvas)
        drawText(canvas)
        //drawMistakesText(canvas)
    }

    private fun updateMeasure(width: Int) {
        cellSizePixels = width / size.toFloat()
        noteSizePixels = cellSizePixels / sqrtSize.toFloat()
        noteTextPaint.textSize = cellSizePixels / sqrtSize.toFloat()
        textPaint.textSize = cellSizePixels / 1.5F
        startingCellTextPaint.textSize = cellSizePixels / 1.5F
    }

    private fun drawMistakesText(canvas: Canvas) {
        println(mistakesCell)
        if (mistakesCell == null) {
            // ошибок нет
            Log.i("CHECKMISS GameboardView", "NOT MISTAKE")
            if (mistakesBe) {
                // перекрасить в черный
                Log.i("CHECKMISS GameboardView", "BE MISTAKE SWITCH PAINT")
            } else
                Log.i("CHECKMISS GameboardView", "NOT BE MISTAKE")
                return
        } else {

            cells?.forEach { cell ->
                if ( (cell.row == mistakesCell!!.row) && (cell.col == mistakesCell!!.col)) {
                    val stringValue = cell.value.toString()
                    val paintToUse = if (cell.isStartingCell) startingCellTextPaint else mistekesTextPaint
                    val textBounds = Rect()
                    paintToUse.getTextBounds(stringValue, 0, stringValue.length, textBounds)
                }
            }
        }
    }

        private fun drawText(canvas: Canvas) {
            cells?.forEach { cell ->
                val value = cell.value

                if (value == 0) {
                    // draw notes
                    cell.notes.forEach { note ->
                        val rowInCell = (note - 1) / sqrtSize
                        val colInCell = (note - 1) % sqrtSize
                        val stringValue = note.toString()

                        val textBounds = Rect()
                        noteTextPaint.getTextBounds(stringValue, 0, stringValue.length, textBounds)
                        val textWidth = noteTextPaint.measureText(stringValue)
                        val textHeigth = textBounds.height()

                        canvas.drawText(
                            stringValue,
                            (cell.col * cellSizePixels) + (colInCell * noteSizePixels) + noteSizePixels / 2 - textWidth / 2f,
                            (cell.col * cellSizePixels) + (rowInCell * noteSizePixels) + noteSizePixels / 2 + textHeigth / 2f,
                            noteTextPaint
                        )
                    }
                } else {
                    val row = cell.row
                    val col = cell.col
                    val stringValue = cell.value.toString()
                    val paintToUse = if (cell.isStartingCell) startingCellTextPaint else textPaint
                    val textBounds = Rect()
                    paintToUse.getTextBounds(stringValue, 0, stringValue.length, textBounds)
                    val textWidth = paintToUse.measureText(stringValue)
                    val textHeidth = textBounds.height()

                    canvas.drawText(
                        stringValue,
                        (col * cellSizePixels) + cellSizePixels / 2 - textWidth / 2,
                        (row * cellSizePixels) + cellSizePixels / 2 + textHeidth / 2,
                        paintToUse
                    )
                }
            }
        }

        private fun fillCells(canvas: Canvas) {
            cells?.forEach {
                val row = it.row
                val col = it.col

                if (it.isStartingCell) {
                    fillCell(canvas, row, col, startingCellPaint)
                } else if (row == selectedRow && col == selectedCall) {
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

        fun updateMistakesCells(cell: Cell) {
            this.mistakesCell = cell
            this.mistakesBe = true
            invalidate()
        }

}

