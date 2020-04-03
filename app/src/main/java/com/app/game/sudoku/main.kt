package com.app.game.sudoku

import com.app.game.sudoku.back.Cell
import com.app.game.sudoku.back.Sudoku


fun main(args: Array<String>) {
    val sudoku = Sudoku()
    val grid = sudoku.getSudoku()
    val SIZE = 9

    val cells = List(SIZE * SIZE) {i -> Cell(i / SIZE, i % SIZE, grid.get(i / SIZE).get(i % SIZE)) }
    println("start")
    for (i in cells) {
        print("${i.value} ")
    }

    val rowArray = IntArray(SIZE * SIZE)
    val colArray = IntArray(SIZE * SIZE)
    val valueArray = IntArray(SIZE * SIZE)
    val startCellArray = BooleanArray(SIZE * SIZE)
    for ((i, cell) in cells.withIndex()) {
        rowArray[i] = cell.row
        colArray[i] = cell.col
        valueArray[i] = cell.value
        startCellArray[i] = cell.isStartingCell
    }
    val cells2 = List(SIZE * SIZE) {i ->
        Cell(
            rowArray[i],
            colArray[i],
            valueArray[i],
            startCellArray[i]
        )
    }
    println("finish")

    for (i in cells2) {
        print("${i.value} ")
    }
}

