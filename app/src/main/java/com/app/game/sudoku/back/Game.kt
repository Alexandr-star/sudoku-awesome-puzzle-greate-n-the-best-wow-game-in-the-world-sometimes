package com.app.game.sudoku.back

import androidx.lifecycle.MutableLiveData

class Game {

    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    var cellsLiveData = MutableLiveData<List<Cell>>()
    var takingNotesLiveData = MutableLiveData<Boolean>()
    var highlightedKeysLiveData = MutableLiveData<Set<Int>>()

    private var selectedRow = -1
    private var selectedCol = -1
    private var isTakingNots = false

    private val board: Board

    init {
        val cells = List(9 * 9) {i -> Cell(i / 9, i % 9, i % 9)}
        //cells[0].notes = mutableSetOf(1, 2, 3, 4, 5 ,6, 7, 8, 9)
        board = Board(9, cells)

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


}