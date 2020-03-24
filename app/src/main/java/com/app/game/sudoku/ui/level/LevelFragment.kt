package com.app.game.sudoku.ui.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.app.game.sudoku.R

class LevelFragment : Fragment() {

    private lateinit var levelViewModel: LevelViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        levelViewModel =
                ViewModelProviders.of(this).get(LevelViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_level, container, false)
        return root
    }
}