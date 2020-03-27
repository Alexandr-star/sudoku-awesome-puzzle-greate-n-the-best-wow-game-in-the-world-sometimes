package com.app.game.sudoku.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    var GAME_MODE = 0


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        binding.classicGameButton.setOnClickListener {v: View ->
            v.findNavController().navigate(R.id.action_navigation_home_to_navigation_level)
        }
        binding.timeGameButton.setOnClickListener {v: View ->
            v.findNavController().navigate(R.id.action_navigation_home_to_navigation_level_time)
        }
        return binding.root
    }

    fun getMODE(): Int {
        return GAME_MODE
    }
}
