package com.app.game.sudoku.ui.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.FragmentLevelBinding

class LevelFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLevelBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_level, container, false)

        binding.easyButton.setOnClickListener {v: View ->
            v.findNavController().navigate(R.id.action_navigation_level_to_navigation_gameboard)
        }

        binding.mediumButton.setOnClickListener {v: View ->
            v.findNavController().navigate(R.id.action_navigation_level_to_navigation_gameboard)
        }

        binding.hardButton.setOnClickListener {v: View ->
            v.findNavController().navigate(R.id.action_navigation_level_to_navigation_gameboard)
        }

        return binding.root
    }
}