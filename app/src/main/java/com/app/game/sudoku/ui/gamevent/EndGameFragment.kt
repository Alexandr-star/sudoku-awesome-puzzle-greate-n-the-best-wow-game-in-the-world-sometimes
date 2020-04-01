package com.app.game.sudoku.ui.gamevent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.FragmentGameEndBinding

class EndGameFragment  : Fragment() {
    private lateinit var endGameViewModel: EndGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGameEndBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_end, container, false)

        endGameViewModel = ViewModelProvider(this).get(EndGameViewModel::class.java)


        binding.closeGameButton.setOnClickListener { v: View ->
            v.findNavController().navigate(
                R.id.action_navigation_end_to_navigation_home
            )
        }


        return binding.root
    }
}