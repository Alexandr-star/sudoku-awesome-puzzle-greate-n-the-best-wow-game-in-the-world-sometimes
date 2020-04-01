package com.app.game.sudoku.ui.gameboard

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.game.sudoku.back.Cell
import com.app.game.sudoku.back.Game

class GameboardViewModel : ViewModel(){
    lateinit var game: Game

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



}