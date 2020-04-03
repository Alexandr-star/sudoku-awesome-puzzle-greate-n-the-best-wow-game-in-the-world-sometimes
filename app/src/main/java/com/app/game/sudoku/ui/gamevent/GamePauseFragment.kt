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
import com.app.game.sudoku.databinding.FragmentGameboardBinding
import com.app.game.sudoku.databinding.FragmentPausegameBinding

class GamePauseFragment : Fragment() {
    private lateinit var gamePauseViewModel: GamePauseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPausegameBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pausegame, container, false)

        gamePauseViewModel = ViewModelProvider(this).get(GamePauseViewModel::class.java)


        return binding.root
    }
}