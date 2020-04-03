package com.app.game.sudoku.ui.gameboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.game.sudoku.back.Game
import com.app.game.sudoku.database.GameStatDatabase
import com.app.game.sudoku.database.dao.GameStatDatabaseDao
import com.app.game.sudoku.database.entity.GameStat
import kotlinx.coroutines.*

class GameboardViewModel(
    val dao: GameStatDatabaseDao
) : ViewModel(){
    lateinit var game: Game

    fun function(level: String, mode: Int) {
        game = Game(level, mode)
    }

    private var gameboarfViewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + gameboarfViewModelJob)

    private var nowGame = MutableLiveData<GameStat?>()
    private val games = dao.getAllGames()

    init {
        initializeTonight()
        Log.i("GameboardViewModel", "GameboardViewModel created")
    }

    private fun initializeTonight() {
        uiScope.launch {
            nowGame.value = getGameFromDatabase()
        }
    }

    private suspend fun getGameFromDatabase(): GameStat? {
        return withContext(Dispatchers.IO) {
            var game = dao.getLastAddedGame()
            game
        }
    }

    fun onStartTracking() {
        uiScope.launch {
            val newGameStat = GameStat(
                game_mode = game.mode,
                game_level = game.level,
                game_time = game._secondsUntil.value!!,
                game_win = game.isWinGame.value!!
            )
            Log.i("DAO", "${newGameStat.game_mode}")

            insert(newGameStat)
            nowGame.value = getGameFromDatabase()

        }

    }

    private suspend fun insert(newGameStat: GameStat) {
        withContext(Dispatchers.IO) {
            dao.insert(newGameStat)
            Log.i("DAO", "${dao.getLastAddedGame()?.game_mode}")
        }
    }




    override fun onCleared() {
        super.onCleared()
        gameboarfViewModelJob.cancel()
        Log.i("GameboardViewModel", "GameboardViewModel destroyed")
    }



}