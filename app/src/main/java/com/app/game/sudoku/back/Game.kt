package com.app.game.sudoku.back

import android.graphics.drawable.LevelListDrawable
import androidx.lifecycle.MutableLiveData
import java.util.*

class Game() {
    private val SIZE = 9

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
    var level = "NULL"
    var mode = -1

    fun setSetting( level: Int,  mode: Int) {

        this.mode = mode
    }

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