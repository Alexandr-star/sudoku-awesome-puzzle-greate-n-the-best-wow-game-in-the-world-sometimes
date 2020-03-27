package com.app.game.sudoku.ui.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.app.game.sudoku.R
import com.app.game.sudoku.back.SettingGame
import com.app.game.sudoku.databinding.FragmentLevelBinding

class TimeLevelGameFragment {
    private lateinit var timeLevelGameViewModel: TimeLevelGameViewModel

    val EASY = 1
    val MEDIUM = 2
    val HARD = 3
    val CLASSIC: Boolean = true

    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLevelBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_level_time, container, false)

        binding.easyButton.setOnClickListener {v: View ->
            v.findNavController().navigate(R.id.action_navigation_level_time_to_navigation_gameboard)
            timeLevelGameViewModel.settingGame.setSetting(CLASSIC, EASY)

        }

        binding.mediumButton.setOnClickListener {v: View ->
            v.findNavController().navigate(R.id.action_navigation_level_time_to_navigation_gameboard)
            timeLevelGameViewModel.settingGame.setSetting(CLASSIC, MEDIUM)

        }

        binding.hardButton.setOnClickListener {v: View ->
            v.findNavController().navigate(R.id.action_navigation_level_time_to_navigation_gameboard)
            timeLevelGameViewModel.settingGame.setSetting(CLASSIC, HARD)

        }

        return binding.root
    }
}