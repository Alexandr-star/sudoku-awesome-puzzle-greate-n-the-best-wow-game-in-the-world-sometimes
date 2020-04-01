package com.app.game.sudoku.back

class Board(val size: Int, val cells: List<Cell>) {
    fun getCell(row: Int, col: Int) = cells[row * size + col]

    fun isBoardFull(): Boolean {
        for (i in cells) {
            if (i.value == 0)
                return false
        }
        return true
    }
}