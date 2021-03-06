package com.app.game.sudoku.ui.dashboard.dontclassic

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.game.sudoku.statisticStore.StatisticPreference

class TimeStatViewModel : ViewModel() {
    var gamesStatisticEasy = MutableLiveData<List<String>>()
    var gamesStatisticMedium = MutableLiveData<List<String>>()
    var gamesStatisticHard = MutableLiveData<List<String>>()

    init {
        gamesStatisticEasy = MutableLiveData(parseStatistic(StatisticPreference.timeModeEasy))
        gamesStatisticMedium = MutableLiveData(parseStatistic(StatisticPreference.timeModeMedium))
        gamesStatisticHard = MutableLiveData(parseStatistic(StatisticPreference.timeModeHard))
    }

    private fun parseStatistic(stat: String): List<String>{
        val gamesStr = stat.substringBefore("/")
        val winsStr = stat.substringAfter("/").substringBeforeLast("/")
        val timeLong = stat.substringAfterLast("/").toLong()
        val timeLong2 = stat.substringAfterLast("/")
        val timeStr = convertTimeLongToString(timeLong)
        Log.i("ClassicStat", "games: ${gamesStr} wins: ${winsStr} timeLong: ${timeLong2}")
        return listOf(gamesStr, winsStr, timeStr)
    }


    private fun convertTimeLongToString(timeLong: Long): String {
        val sec = timeLong % 60
        val min = (sec / 60) % 60
        val hour = (sec / (60 * 60)) % 24
        return String.format("%d:%02d:%02d", hour, min, sec)
    }

    override fun onCleared() {
        super.onCleared()
    }
}
