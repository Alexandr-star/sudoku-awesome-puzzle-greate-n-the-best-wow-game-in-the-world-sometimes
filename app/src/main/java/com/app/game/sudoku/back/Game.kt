package com.app.game.sudoku.back

import android.graphics.drawable.LevelListDrawable
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.game.sudoku.back.Game.Companion.GAME_TIME
import com.app.game.sudoku.ui.gameboard.GameboardViewModel
import java.util.*

class Game(var level: String, var mode: Int) {
    private val SIZE = 9

    companion object {
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val GAME_TIME = 10 * ONE_SECOND
    }

    private val timer: CountDownTimer

    private var _secondsUntilEnd = MutableLiveData<Long>()
    val secondsUntilEnd: LiveData<Long>
        get() = _secondsUntilEnd

    // Event which triggers the end of the game
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()
    var takingNotesLiveData = MutableLiveData<Boolean>()
    var highlightedKeysLiveData = MutableLiveData<Set<Int>>()
    var mistakesLiveData = MutableLiveData<MutableList<Pair<Int, Int>>>()
    var mistakes: MutableList<Pair<Int, Int>> = arrayListOf()

    private var selectedRow = -1
    private var selectedCol = -1
    private var isTakingNots = false

    private val board: Board
    private var grid: Array<IntArray> = Array(SIZE) { IntArray(SIZE) }

    var time = 0
    var countMiss = 0

    init {
        val sudoku = Sudoku()
        grid = sudoku.getSudoku()


        val cells = List(SIZE * SIZE) {i -> Cell(i / SIZE, i % SIZE, grid.get(i / SIZE).get(i % SIZE))}

        board = Board(SIZE, cells)
        for (r in 0 until SIZE) {
            for (c in 0 until SIZE) {
                if (board.getCell(r, c).value != 0) {
                    board.getCell(r, c).isStartingCell = true
                }
            }
        }
        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        cellsLiveData.postValue(board.cells)
        takingNotesLiveData.postValue(isTakingNots)

        if (mode == 2) {
            println("game.mode ${mode} time")
            timer = object : CountDownTimer(
                GAME_TIME,
                ONE_SECOND
            ) {
                override fun onTick(millisUntilFinished: Long) {
                    _secondsUntilEnd.value = millisUntilFinished / ONE_SECOND
                }
                override fun onFinish() {
                    //TODO: установка флага о том, что игра завершена
                }
            }
            timer.start()
        } else {
            println("game.mode ${mode} classic")
            timer = object : CountDownTimer(
                GAME_TIME,
                ONE_SECOND
            ) {
                override fun onTick(millisUntilFinished: Long) {
                    _secondsUntilEnd.value = millisUntilFinished * ONE_SECOND
                }

                override fun onFinish() {
                    TODO("Not yet implemented")
                }
            }
            timer.start()
        }
    }


    fun handleInput(number: Int) {
        if (selectedRow == -1 || selectedCol == -1) return
        val cell = board.getCell(selectedRow, selectedCol)
        if (cell.isStartingCell) return

        if (isTakingNots) {
            if (cell.notes.contains(number)) {
                cell.notes.remove(number)
            } else {
                cell.notes.add(number)
            }
            highlightedKeysLiveData.postValue(cell.notes)
        } else {
            cell.value = number
        }
        cellsLiveData.postValue(board.cells)
    }

    fun updateSelectedCell(row: Int, col: Int) {
        val cell = board.getCell(row, col)
        if (!cell.isStartingCell) {
            selectedRow = row
            selectedCol = col
            selectedCellLiveData.postValue(Pair(row, col))

            if (isTakingNots) {
                highlightedKeysLiveData.postValue(cell.notes)
            }
        }
    }

    fun changeNoteTakingState() {
        isTakingNots = !isTakingNots
        takingNotesLiveData.postValue(isTakingNots)

        val currentNotes = if (isTakingNots) {
            board.getCell(selectedRow, selectedCol).notes
        } else {
            setOf<Int>()
        }
        highlightedKeysLiveData.postValue(currentNotes)
    }

    fun deleteNumInCell() {
        val cell = board.getCell(selectedRow, selectedCol)
        if (isTakingNots) {
            cell.notes.clear()
            highlightedKeysLiveData.postValue(setOf())
        } else {
            cell.value = 0
        }
        cellsLiveData.postValue(board.cells)
    }

    fun checkMistakes() {
        val cell = board.getCell(selectedRow, selectedCol)
        println(mistakes)
        isInBox(cell)
        isInCol(cell)
        isInRow(cell)
        mistakesLiveData.postValue(mistakes)

    }

    private fun isInBox(cell: Cell) {
        var r = selectedRow - selectedRow % 3;
        var c = selectedCol - selectedCol % 3;

        for (i in r until r + 3) {
            for (j in c until c + 3) {
                if (board.getCell(i, j).value == cell.value &&
                    (i == selectedRow && j == selectedCol)) {
                    mistakes.add(Pair(i, j))
                }
            }
        }

    }

    private fun isInCol(cell: Cell){
        for (i in 0 until SIZE) {
            if (board.getCell(i, selectedCol).value == cell.value && i != selectedRow) {
                mistakes.add(Pair(i, selectedCol))
            }
        }
    }

    private fun isInRow(cell: Cell) {
        for (i in 0 until SIZE) {
            if (board.getCell(selectedRow, i).value == cell.value && i != selectedCol) {
                mistakes.add(Pair(selectedRow, selectedCol))
            }
        }
    }
}