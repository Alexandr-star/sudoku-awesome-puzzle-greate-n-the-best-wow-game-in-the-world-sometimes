package com.app.game.sudoku

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.game.sudoku.database.GameStatDatabase
import com.app.game.sudoku.database.dao.GameStatDatabaseDao
import com.app.game.sudoku.database.entity.GameStat

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var sleepDao: GameStatDatabaseDao
    private lateinit var db: GameStatDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, GameStatDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        sleepDao = db.getGameStatDatabaseDao()
    }

//    @After
//    @Throws(IOException::class)
//    fun closeDb() {
//        db.close()
//    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val night = GameStat()
        sleepDao.insert(night)
        val lastGame = sleepDao.getLastAddedGame()
        assertEquals(lastGame?.game_level, "-")
    }
}
