package com.app.game.sudoku.ui.gameboard

import android.util.Log
import androidx.lifecycle.ViewModel

class GameboardViewModel : ViewModel(){
    lateinit var game: Game
    private val STATISTIC_PREFERENCES = "statistic_game"

    fun function(level: String, mode: Int) {
        game = Game(level, mode)
    }

    init {
        Log.i("GameboardViewModel", "GameboardViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameboardViewModel", "GameboardViewModel destroyed")
    }

    fun saveStat() {
    }

}