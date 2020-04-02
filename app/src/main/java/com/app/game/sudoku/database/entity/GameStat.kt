package com.app.game.sudoku.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_stat_table")
data class GameStat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var gameId: Long = 0L,

    @ColumnInfo(name = "game_mode")
    val game_mode: Int = 0,

    @ColumnInfo(name = "game_level")
    var game_level: String = "-",

    @ColumnInfo(name = "game_win")
    var game_win: Boolean = false,

    @ColumnInfo(name = "time_game")
    var game_time: Long = 0L
)
