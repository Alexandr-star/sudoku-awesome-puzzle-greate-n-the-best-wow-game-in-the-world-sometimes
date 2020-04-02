package com.app.game.sudoku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.game.sudoku.database.dao.GameStatDatabaseDao
import com.app.game.sudoku.database.entity.GameStat

@Database(entities = [GameStat::class], version = 1)
abstract class GameStatDatabase : RoomDatabase() {
    abstract fun getGameStatDatabaseDao(): GameStatDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: GameStatDatabase? = null

        fun getInstance(context: Context): GameStatDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                        GameStatDatabase::class.java, "game_tracker_db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}