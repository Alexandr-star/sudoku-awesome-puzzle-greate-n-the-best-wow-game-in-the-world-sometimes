package com.app.game.sudoku.statisticStore

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString

object StatisticPreference {
    private const val NAME = "com.app.game.sudoku.STATISTIC_THIS_GAME"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    //Set return when null
    //SharedPreferences variables
    private val CLASSIC_MODE_EASY = Pair("classic_mode_easy", "0/0/0")
    private val CLASSIC_MODE_MEDIUM = Pair("classic_mode_medium", "0/0/0")
    private val CLASSIC_MODE_HARD = Pair("classic_mode_hard", "0/0/0")
    private val TIME_MODE_EASY = Pair("time_mode_easy", "0/0/0")
    private val TIME_MODE_MEDIUM = Pair("time_mode_medium", "0/0/0")
    private val tIME_MODE_HARD = Pair("time_mode_hard", "0/0/0")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    //an inline function to put variable and save it
    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

   // SharedPreferences variables getters/setters
    var classicModeEasy: String
        get() = preferences.getString(CLASSIC_MODE_EASY.first, CLASSIC_MODE_EASY.second) ?: ""
        set(value) = preferences.edit {
            it.putString(CLASSIC_MODE_EASY.first, value)
        }

    var classicModeMedium: String
        get() = preferences.getString(CLASSIC_MODE_MEDIUM.first, CLASSIC_MODE_MEDIUM.second) ?: ""
        set(value) = preferences.edit {
            it.putString(CLASSIC_MODE_MEDIUM.first, value)
        }

    var classicModeHard: String
        get() = preferences.getString(CLASSIC_MODE_HARD.first, CLASSIC_MODE_HARD.second) ?: ""
        set(value) = preferences.edit {
            it.putString(CLASSIC_MODE_HARD.first, value)
        }

    var timeModeEasy: String
        get() = preferences.getString(TIME_MODE_EASY.first, TIME_MODE_EASY.second) ?: ""
        set(value) = preferences.edit {
            it.putString(TIME_MODE_EASY.first, value)
        }

    var timeModeMedium: String
        get() = preferences.getString(TIME_MODE_MEDIUM.first, TIME_MODE_MEDIUM.second) ?: ""
        set(value) = preferences.edit {
            it.putString(TIME_MODE_MEDIUM.first, value)
        }

    var timeModeHard: String
        get() = preferences.getString(tIME_MODE_HARD.first, tIME_MODE_HARD.second) ?: ""
        set(value) = preferences.edit {
            it.putString(tIME_MODE_HARD.first, value)
        }
}