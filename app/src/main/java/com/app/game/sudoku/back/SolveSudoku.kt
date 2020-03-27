//package com.app.game.sudoku.back
//
//class SolveSudoku(grid: Array<IntArray>) {
//
//    private val SIZE = 9
//    private val BOX_SIZE = 3
//    private val EMPTY_CELL = 0
//
//    private val CONSTRAINTS = 4
//
//    private val MIN_VALUE = 1
//    private val MAX_VALUE = SIZE
//
//    private val COVER_START_INDEX = 1
//
//    private var grid: Array<IntArray> =  Array(9) { IntArray(9) }
//    private var gridSolved: Array<IntArray> =  Array(9) { IntArray(9) }
//
//    init {
//        this.grid = grid
//    }
//
//    private fun indexInCoverGrid(row: Int, col: Int, num: Int): Int {
//        return (row - 1) * SIZE * SIZE + (col - 1) * SIZE + (num -1)
//    }
//
//    private fun createCovetGrid(): Array<IntArray> {
//        var coverGrid: Array<IntArray> = Array(SIZE * SIZE * MAX_VALUE) { IntArray(SIZE * SIZE * CONSTRAINTS) }
//
//        var header = 0
//        header = createCellConstraints(coverGrid, header);
//        header = createRowConstraints(coverGrid, header);
//        header = createColumnConstraints(coverGrid, header);
//        createBoxConstraints(coverGrid, header);
//
//        return coverGrid;
//    }
//
//    private fun createBoxConstraints(coverGrid: Array<IntArray>, header: Int) {
//
//    }
//
//    private fun createRowConstraints(coverGrid: Array<IntArray>, header: Int): Int {
//
//    }
//
//    private fun createCellConstraints(coverGrid: Array<IntArray>, header: Int): Int {
//
//    }
//
//    private fun createColumnConstraints(coverGrid: Array<IntArray>, header: Int): Int {
//
//    }
//}