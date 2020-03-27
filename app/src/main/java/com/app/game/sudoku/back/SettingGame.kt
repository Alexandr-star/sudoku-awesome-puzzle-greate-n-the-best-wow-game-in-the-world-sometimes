package com.app.game.sudoku.back

class SettingGame() {
    private var level: Int = 0
    private var mode: Boolean = false

    fun setSetting(mode: Boolean, level: Int) {
        this.mode = mode
        this.level = level
    }

    fun getLevel(): Int {
        return this.level
    }

    fun getMode(): Boolean {
        return this.mode
    }
}