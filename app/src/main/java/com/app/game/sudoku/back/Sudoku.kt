package com.app.game.sudoku.back

import kotlin.random.Random

class Sudoku {
    private val n = 3
    private val itter = 10

    private var baseGrid: Array<IntArray> =  Array(9) { IntArray(9) }


     fun initSudokuGrid() {
        for (i in 0 until n * n) {
            for (j in 0 until n * n) {
                baseGrid[i][j] = ((i * n + i / n + j) % (n * n) + 1)
                print("${baseGrid[i][j]} ")
            }
            println()

        }
    }

    fun mixSudokuGrid() {
        for (i in 0 until itter) {
            var randomСonversion = Random.nextInt(0, 5)

            when (randomСonversion) {
                0 -> transposeBaseGrid()
                1 -> swapRows()
                2 -> swapCols()
                3 -> swapRowsBlock()
                4 -> swapColsBlock()
                else -> transposeBaseGrid()
            }
        }
        printMat("mixed")
    }

    fun transposeBaseGrid() {
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

    fun swapRows() {
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

    fun swapCols() {
        baseGrid = transposeBaseGrid(baseGrid)
        baseGrid = swapRows(baseGrid)
        baseGrid = transposeBaseGrid(baseGrid)
    }

    fun swapRowsBlock() {
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

    fun swapColsBlock() {
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
}