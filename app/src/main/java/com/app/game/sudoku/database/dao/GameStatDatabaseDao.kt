package com.app.game.sudoku.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.game.sudoku.database.entity.GameStat

@Dao
interface GameStatDatabaseDao {

    @Insert
    fun insert(game: GameStat)

    @Update
    fun update(game: GameStat)

    @Query("SELECT * FROM game_stat_table WHERE id = :key")
    fun get(key: Long): GameStat?

    @Query("DELETE FROM game_stat_table")
    fun clear()

    @Query("SELECT * FROM game_stat_table ORDER BY id DESC")
    fun getAllGames(): LiveData<List<GameStat>>

    @Query("SELECT * FROM game_stat_table ORDER BY id DESC LIMIT 1")
    fun getLastAddedGame(): GameStat?
}