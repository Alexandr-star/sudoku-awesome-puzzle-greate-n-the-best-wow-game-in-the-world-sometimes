package com.app.game.sudoku.ui.dashboard.classic

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.game.sudoku.statisticStore.StatisticPreference
import java.text.SimpleDateFormat
import java.util.*

class ClassicStatViewModel : ViewModel() {

    var gamesStatisticEasy = MutableLiveData<List<String>>()
    var gamesStatisticMedium = MutableLiveData<List<String>>()
    var gamesStatisticHard = MutableLiveData<List<String>>()

    init {
        gamesStatisticEasy = MutableLiveData(parseStatistic(StatisticPreference.classicModeEasy))
        gamesStatisticMedium = MutableLiveData(parseStatistic(StatisticPreference.classicModeMedium))
        gamesStatisticHard = MutableLiveData(parseStatistic(StatisticPreference.classicModeHard))
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