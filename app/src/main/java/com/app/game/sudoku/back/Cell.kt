package com.app.game.sudoku.back

class Cell(val row: Int,
           val col: Int,
           var value: Int,
           var isStartingCell: Boolean = false,
          var notes: MutableSet<Int> = mutableSetOf<Int>()) {
}