package com.app.game.sudoku.ui.gameboard

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.game.sudoku.back.Game
import com.app.game.sudoku.back.SettingGame

class GameboardViewModel : ViewModel(){
    var game = Game()
    var setting = SettingGame(game.level, game.mode)

    init {
        Log.i("GameboardViewModel", "GameboardViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameboardViewModel", "GameboardViewModel destroyed")
    }
}