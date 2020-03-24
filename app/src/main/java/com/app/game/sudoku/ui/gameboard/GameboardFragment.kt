package com.app.game.sudoku.ui.gameboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.FragmentGameboardBinding

class GameboardFragment : Fragment() {
    private lateinit var gameboardViewModel: GameboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gameboardViewModel = ViewModelProvider(this).get(GameboardViewModel::class.java)
        val binding: FragmentGameboardBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_gameboard, container, false)

        return binding.root
    }

}