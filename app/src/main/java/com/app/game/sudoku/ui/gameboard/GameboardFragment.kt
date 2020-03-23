package com.app.game.sudoku.ui.gameboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.game.sudoku.R

class GameboardFragment : Fragment() {
    private lateinit var gameboardViewModel: GameboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gameboardViewModel =
                ViewModelProviders.of(this).get(GameboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gameboard, container, false)
        return root
    }
}