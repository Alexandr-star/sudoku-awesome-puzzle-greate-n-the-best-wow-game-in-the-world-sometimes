package com.app.game.sudoku.ui.gameboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.game.sudoku.database.dao.GameStatDatabaseDao

class GameboardViewModelFactory(
    private val dao: GameStatDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameboardViewModel::class.java)) {
            return GameboardViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}