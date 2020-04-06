package com.app.game.sudoku.ui.gamevent

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EndGameViewModel  : ViewModel()  {

    val statusGame = MutableLiveData<String>()
    val mistakesGame = MutableLiveData<String>()
    val timeGame = MutableLiveData<String>()

    fun setFinishGameData(status: String, mistakes: String, time: Long) {
        statusGame.value = status
        mistakesGame.value = mistakes
        timeGame.value = convertTimeLongToString(time)
    }

    private fun convertTimeLongToString(timeLong: Long): String {
        val sec = timeLong % 60
        val min = (sec / 60) % 60
        val hour = (sec / (60 * 60)) % 24
        return String.format("%d:%02d:%02d", hour, min, sec)
    }
}