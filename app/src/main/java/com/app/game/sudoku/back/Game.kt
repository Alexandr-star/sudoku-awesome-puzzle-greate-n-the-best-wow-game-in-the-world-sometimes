package com.app.game.sudoku.back

import android.os.Build
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.game.sudoku.ui.gameboard.GameboardFragment
import android.widget.Chronometer as Chronometer


class Game(var level: String, var mode: Int) {
    private val SIZE = 9
    private val SIZE_MISS = 3

    lateinit var gameboardFragment: GameboardFragment

    companion object {
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val GAME_TIME_EASY = 600 * ONE_SECOND
        const val GAME_TIME_MEDIUM = 1200 * ONE_SECOND
        const val GAME_TIME_HARD = 1800 * ONE_SECOND
    }

    private lateinit var timerDown: CountDownTimer
    private lateinit var timer: Chronometer

    private var _secondsUntil = MutableLiveData<Long>()
    val secondsUntil: LiveData<Long>
        get() = _secondsUntil

    // Event which triggers the end of the game
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()
    var takingNotesLiveData = MutableLiveData<Boolean>()
    var highlightedKeysLiveData = MutableLiveData<Set<Int>>()
    var mistakesLiveData = MutableLiveData<Cell>()
    lateinit var mistakes: Cell
    var mistakesCountLiveData = MutableLiveData<String>()
    private var countMiss = 0

    private var selectedRow = -1
    private var selectedCol = -1
    private var isTakingNots = false

    private val board: Board
    private var grid: Array<IntArray> = Array(SIZE) { IntArray(SIZE) }

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
        mistakesCountLiveData.postValue(missToString(countMiss))


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
            when(level) {
                "easy" -> countDown(GAME_TIME_EASY)
                "medium" -> countDown(GAME_TIME_MEDIUM)
                "hard" -> countDown(GAME_TIME_HARD)
            }
        }
    }

    private fun countDown(levelTime: Long) {
        this.timerDown = object : CountDownTimer(
            levelTime,
            ONE_SECOND
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _secondsUntil.value = millisUntilFinished / ONE_SECOND
                Log.i("Game TIMER", "${_secondsUntil.value}")
            }
            override fun onFinish() {
                onGameFinishComplete()
            }
        }
        timerDown.start()
    }

    fun stopTimer() {
        timer.stop()
        timerDown.onFinish()
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    private fun missToString(countMiss: Int): String {
        return "${countMiss}/${SIZE_MISS}"
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
            _eventGameFinish.value = true
            timer.stop()
            timerDown.onFinish()
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
                _eventGameFinish.value = true
                timer.stop()
                timerDown.onFinish()
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
}
