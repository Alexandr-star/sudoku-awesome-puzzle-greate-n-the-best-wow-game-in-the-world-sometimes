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

//    companion object {
//        // This is the number of milliseconds in a second
//        const val ONE_SECOND = 1000L
//        // This is the total time of the game
//        const val GAME_TIME = 10 * ONE_SECOND
//    }
//
//    private val timer: CountDownTimer
//
//    private var _secondsUntilEnd = MutableLiveData<Long>()
//    val secondsUntilEnd: LiveData<Long>
//        get() = _secondsUntilEnd
//
//    // Event which triggers the end of the game
//    private val _eventGameFinish = MutableLiveData<Boolean>()
//    val eventGameFinish: LiveData<Boolean>
//        get() = _eventGameFinish

    init {
        Log.i("GameboardViewModel", "GameboardViewModel created")

//        if (game.mode == 2) {
//            timer = object : CountDownTimer(GAME_TIME, ONE_SECOND) {
//                override fun onTick(millisUntilFinished: Long) {
//                    _secondsUntilEnd.value = millisUntilFinished / ONE_SECOND
//                }
//                override fun onFinish() {
//                    //TODO: установка флага о том, что игра завершена
//                }
//            }
//            timer.start()
//        } else {
//            println("game.mode ${game.mode}")
//            timer = object : CountDownTimer(GAME_TIME, ONE_SECOND) {
//                override fun onTick(millisUntilFinished: Long) {
//                    _secondsUntilEnd.value = millisUntilFinished * ONE_SECOND
//                }
//
//                override fun onFinish() {
//                    TODO("Not yet implemented")
//                }
//            }
//            timer.start()
//        }
    }




    override fun onCleared() {
        super.onCleared()
        Log.i("GameboardViewModel", "GameboardViewModel destroyed")
    }



}