package com.app.game.sudoku.back

import kotlin.math.pow
import kotlin.random.Random

class Sudoku(level: String) {
    private val n = 3
    private val SIZE  = 9
    private val EMPTY  = 0
    private val itter = 20

    private var LEVEL_CELL = 0

    private var baseGrid: Array<IntArray> =  Array(9) { IntArray(9) }

    init {
        var LEVEL_CELL_MIN = 0
        var LEVEL_CELL_MAX = 0
        when(level) {
            "easy" -> {
                LEVEL_CELL_MIN = 36
                LEVEL_CELL_MAX = 41
            }
            "medium" -> {
                LEVEL_CELL_MIN = 41
                LEVEL_CELL_MAX = 46
            }
            "hard" -> {
                LEVEL_CELL_MIN = 46
                LEVEL_CELL_MAX = 51
            }
        }
        initLevel(LEVEL_CELL_MIN, LEVEL_CELL_MAX)
    }

    private fun initLevel(levelCellMin: Int, levelCellMax: Int) {
        LEVEL_CELL = Random.nextInt(levelCellMin, levelCellMax)
    }


    private fun initSudokuGrid() {
        for (i in 0 until n * n) {
            for (j in 0 until n * n) {
                baseGrid[i][j] = ((i * n + i / n + j) % (n * n) + 1)
            }
        }
    }

    fun getSudoku(): Array<IntArray> {
        initSudokuGrid()
        mixSudokuGrid()
        createFinalSudokuBoard()
        return baseGrid
    }



    private fun mixSudokuGrid() {
        for (i in 0 until itter) {
            val randomСonversion = Random.nextInt(0, 5)

            when (randomСonversion) {
                0 -> transposeBaseGrid()
                1 -> swapRows()
                2 -> swapCols()
                3 -> swapRowsBlock()
                4 -> swapColsBlock()
                else -> transposeBaseGrid()
            }
        }
    }

    private fun createFinalSudokuBoard() {
        val cellLook: Array<IntArray> =  Array(9) { IntArray(9) {0} }
        var iterate = 0
        var difficult = n.toDouble().pow(4).toInt()

        while (iterate < LEVEL_CELL) {
            val i = Random.nextInt(0, n * n)
            val j = Random.nextInt(0, n * n)

            if (cellLook[i][j] == 0) {
                iterate++
                cellLook[i][j] = 1

                val temp = baseGrid[i][j]
                baseGrid[i][j] = 0
                difficult--

                val tableSolution = baseGrid.copyOf()
                if (!solve(tableSolution)) {
                    baseGrid[i][j] = temp
                    difficult++
                }
            }
        }
    }

    private fun solve(board: Array<IntArray>): Boolean {
        for (row in 0 until  SIZE) {
            for (col in 0 until SIZE) {
                if (board[row][col] == EMPTY) {
                    for (number in 1 until  SIZE + 1) {
                        if (isOk(row, col, number, board)) {
                            return true
                        }
                    }
                    return false;
                }
            }
        }

        return true;
    }

    private fun isOk(row: Int, col: Int, number: Int, board: Array<IntArray>): Boolean {
        return !isInRow(row, number, board) &&
                !isInCol(col, number, board) &&
                !isInBox(row, col, number, board);
    }

    private fun isInBox(row: Int, col: Int, number: Int, board: Array<IntArray>): Boolean {
        var r = row - row % n;
        var c = col - col % n;

        for (i in r until r + n)
        for (j in c until c + n)
        if (board[i][j] == number)
            return true;

        return false;
    }

    private fun isInCol(col: Int, number: Int , board: Array<IntArray>): Boolean {
        for (i in 0 until SIZE)
        if (board[i][col] == number)
            return true;

        return false;
    }

    private fun isInRow(row: Int, number: Int, board: Array<IntArray>): Boolean {
        for (i in 0 until SIZE)
        if (board[row][i] == number)
            return true;

        return false;
    }

    private fun transposeBaseGrid() {
        var temp = 0
        for (i in 0 until n * n) {
            for (j in i + 1  until n* n) {
                temp = baseGrid[i][j]
                baseGrid[i][j] = baseGrid[j][i]
                baseGrid[j][i] = temp
            }
        }
    }

    private fun transposeBaseGrid(grid: Array<IntArray>): Array<IntArray> {
        var temp = 0
        for (i in 0 until n * n) {
            for (j in i + 1  until n* n) {
                temp = grid[i][j]
                grid[i][j] = grid[j][i]
                grid[j][i] = temp
            }
        }
        return grid
    }

    private fun swapRows() {
        var randomArea = Random.nextInt(0, n)
        var randomeLine1 = Random.nextInt(0, n)

        var firstline = randomArea * n + randomeLine1

        var randomLine2 = Random.nextInt(0, n)
        while (randomeLine1 == randomLine2) randomLine2 = Random.nextInt(0, n)

        var secondLine =  randomArea * n + randomLine2

        var temp = baseGrid[firstline]
        baseGrid[firstline] = baseGrid[secondLine]
        baseGrid[secondLine] = temp
    }

    private fun swapRows(grid: Array<IntArray>): Array<IntArray> {
        var randomArea = Random.nextInt(0, n)
        var randomeLine1 = Random.nextInt(0, n)

        var firstline = randomArea * n + randomeLine1

        var randomLine2 = Random.nextInt(0, n)
        while (randomeLine1 == randomLine2) randomLine2 = Random.nextInt(0, n)

        var secondLine =  randomArea * n + randomLine2

        var temp = grid[firstline]
        grid[firstline] = grid[secondLine]
        grid[secondLine] = temp

        return grid
    }

    private fun swapCols() {
        baseGrid = transposeBaseGrid(baseGrid)
        baseGrid = swapRows(baseGrid)
        baseGrid = transposeBaseGrid(baseGrid)
    }

    private fun swapRowsBlock() {
        var randomArea1 = Random.nextInt(0, n)
        var randomArea2 = Random.nextInt(0, n)
        while (randomArea1 == randomArea2) randomArea2 = Random.nextInt(0, n)

        for (i in 0 until n) {
            var line1 = randomArea1 * n + i
            var line2 = randomArea2 * n + i
            var temp = baseGrid[line1]
            baseGrid[line1] = baseGrid[line2]
            baseGrid[line2] = temp
        }

    }

    private fun swapRowsBlock(grid: Array<IntArray>): Array<IntArray> {
        var randomArea1 = Random.nextInt(0, n)

        var randomArea2 = Random.nextInt(0, n)
        while (randomArea1 == randomArea2) randomArea2 = Random.nextInt(0, n)

        for (i in 0 until n) {
            var line1 = randomArea1 * n + i
            var line2 = randomArea2 * n + i
            var temp = grid[line1]
            grid[line1] = grid[line2]
            grid[line2] = temp
        }

        return grid
    }

    private fun swapColsBlock() {
        baseGrid = transposeBaseGrid(baseGrid)
        baseGrid = swapRowsBlock(baseGrid)
        baseGrid = transposeBaseGrid(baseGrid)
    }

    private fun printMat() {
        for (i in 0 until n * n) {
            for (j in 0 until n * n) {
                print("${baseGrid[i][j]} ")
            }
            println()
        }
    }

    private fun printMat(string: String) {
        println(string)
        for (i in 0 until n * n) {
            for (j in 0 until n * n) {
                print("${baseGrid[i][j]} ")
            }
            println()
        }
    }

    private fun printMat(string: String, array: Array<IntArray>) {
        println(string)
        for (i in 0 until n * n) {
            for (j in 0 until n * n) {
                print("${array[i][j]} ")
            }
            println()
        }
    }
}

