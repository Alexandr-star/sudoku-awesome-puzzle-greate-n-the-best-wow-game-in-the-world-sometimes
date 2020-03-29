package com.app.game.sudoku.back

import android.graphics.drawable.LevelListDrawable
import androidx.lifecycle.MutableLiveData

class Game {
    private var mode = false
    private var level = 0
    private var countErrors: Int = 0
    private val SIZE = 9

    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()
    var takingNotesLiveData = MutableLiveData<Boolean>()
    var highlightedKeysLiveData = MutableLiveData<Set<Int>>()
    var mistakesLiveData = MutableLiveData<MutableList<Pair<Int, Int>>>()

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

    fun setSettingGame(mode: Boolean, level: Int) {
        this.mode = mode
        this.level = level
    }


    fun checkMistakes() {
        val cell = board.getCell(selectedRow, selectedCol)
        var mistakes: MutableList<Pair<Int, Int>> = arrayListOf()
        println(mistakes)
        isInBox(cell, mistakes)
        isInCol(cell, mistakes)
        isInRow(cell, mistakes)
        mistakesLiveData.postValue(mistakes)

    }

    private fun isInBox(cell: Cell, mistakes: MutableList<Pair<Int, Int>>) {
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

    private fun isInCol(cell: Cell, mistakes: MutableList<Pair<Int, Int>>){
        for (i in 0 until SIZE) {
            if (board.getCell(i, selectedCol).value == cell.value && i != selectedRow) {
                mistakes.add(Pair(i, selectedCol))
            }
        }
    }

    private fun isInRow(cell: Cell, mistakes: MutableList<Pair<Int, Int>>) {
        for (i in 0 until SIZE) {
            if (board.getCell(selectedRow, i).value == cell.value && i != selectedCol) {
                mistakes.add(Pair(selectedRow, selectedCol))
            }
        }
    }
}