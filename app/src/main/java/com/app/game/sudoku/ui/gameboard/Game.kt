package com.app.game.sudoku.ui.gameboard

import android.os.Build
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.game.sudoku.back.Board
import com.app.game.sudoku.back.Cell
import com.app.game.sudoku.back.Sudoku
import android.widget.Chronometer as Chronometer


class Game(var level: String, var mode: Int) {
    private val SIZE = 9
    private val SIZE_MISS = 3


    lateinit var gameboardFragment: GameboardFragment

    // This is the number of milliseconds in a second
    val ONE_SECOND = 1000L
    // This is the total time of the game
    var GAME_TIMEDOWN: Long = 0L

    private lateinit var timerDown: CountDownTimer
    private lateinit var timer: Chronometer

    var _secondsUntil = MutableLiveData<Long>()
//    var secondsUntil: LiveData<Long>
//        get() = _secondsUntil
//        set(value) {
//            _secondsUntil.value = value.value
//        }

    // Event which triggers the end of the game
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    var _isWinGame = false


    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()
    var takingNotesLiveData = MutableLiveData<Boolean>()
    var highlightedKeysLiveData = MutableLiveData<Set<Int>>()
    var mistakesLiveData = MutableLiveData<Cell>()
    lateinit var mistakes: Cell
    var mistakesCountLiveData = MutableLiveData<String>()
    var countMiss = 0

    private var selectedRow = -1
    private var selectedCol = -1
    private var isTakingNots = false

    var board: Board
    private var grid: Array<IntArray> = Array(SIZE) { IntArray(SIZE) }

    init {
        when(level) {
            "easy" -> GAME_TIMEDOWN = 600 * ONE_SECOND
            "medium" -> GAME_TIMEDOWN = 1200 * ONE_SECOND
            "hard" -> GAME_TIMEDOWN = 1800 * ONE_SECOND
        }

        val sudoku = Sudoku()
        grid = sudoku.getSudoku()
        val cells = List(SIZE * SIZE) { i ->
            Cell(
                i / SIZE,
                i % SIZE,
                grid.get(i / SIZE).get(i % SIZE)
            )
        }
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
        mistakesCountLiveData.postValue(missToString(countMiss))

    }

    fun getBoardInArrayList() {
        val cells = board.cells
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun timerOn(chron: Chronometer) {
        this.timer = chron
        if (mode == 1) {
            timer.start()
            timer.setOnChronometerTickListener { timer ->
                _secondsUntil.value = (SystemClock.elapsedRealtime() - timer.base) / ONE_SECOND + 1
                Log.i("Timer CLASSIC", "${_secondsUntil.value}")
            }
        } else if (mode == 2) {
            countDown()
        }
    }

    private fun countDown() {
        this.timerDown = object : CountDownTimer(
            GAME_TIMEDOWN,
            ONE_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _secondsUntil.value = millisUntilFinished / ONE_SECOND
                Log.i("Game TIMER", "${_secondsUntil.value}")
            }
            override fun onFinish() {
                onGameFinishComplete()
                _isWinGame = false

            }
        }
        timerDown.start()
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
        checkMistakes(cell)

        if (countMiss == SIZE_MISS) {
            onGameFinishComplete()
            stopTimer()
            _isWinGame = false
        }
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
        cell.value = 0
        cellsLiveData.postValue(board.cells)
        checkMistakes(cell)
    }

    private fun checkMistakes(cell: Cell) {
        if (isInBoxMiss(cell) || isInColMiss(cell) || isInRowMiss(cell)) {
            mistakesCountLiveData.postValue(missToString(++countMiss))
            Log.i("Game", "MISTAKE IS  (${mistakes.row}; ${mistakes.col})")
            Log.i("Game", "MISTAKE IS ${mistakesCountLiveData}")
        } else {
            if (board.isBoardFull()) {
                stopTimer()
                _isWinGame = true
                onGameFinishComplete()
            }
        }
    }

    private fun isInBoxMiss(cell: Cell): Boolean {
        var r = selectedRow - selectedRow % 3;
        var c = selectedCol - selectedCol % 3;

        for (i in r until r + 3) {
            for (j in c until c + 3) {
                if (board.getCell(i, j).value == cell.value &&
                    (i != selectedRow && j != selectedCol) &&
                    board.getCell(i, j).value != 0) {
                    mistakes = cell
                    Log.i("Game Box", "${cell.value}")
                    return true
                }
            }
        }
        return false
    }

    private fun isInColMiss(cell: Cell): Boolean{
        for (i in 0 until SIZE) {
            if (board.getCell(i, selectedCol).value == cell.value &&
                i != selectedRow &&
                board.getCell(i, selectedCol).value != 0) {
                Log.i("Game Col", "${cell.value}")
                mistakes = cell
                return true
            }
        }
        return false
    }

    private fun isInRowMiss(cell: Cell): Boolean {
        for (i in 0 until SIZE) {
            if (board.getCell(selectedRow, i).value == cell.value &&
                i != selectedCol &&
                board.getCell(selectedRow, i).value != 0) {
                Log.i("Game Row", "${cell.value}")

                mistakes = cell
                return true
            }
        }
        return false
    }

    private fun missToString(countMiss: Int): String {
        return "${countMiss}/${SIZE_MISS}"
    }

    private fun onGameFinishComplete() {
        _eventGameFinish.value = true
    }

    fun stopTimer() {
        if (mode == 1) timer.stop()
        else {
            timerDown.cancel()
        }
    }
}