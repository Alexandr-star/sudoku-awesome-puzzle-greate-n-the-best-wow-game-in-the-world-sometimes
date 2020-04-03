package com.app.game.sudoku.ui.dashboard.dontclassic

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.TimeStatFragmentBinding

class TimeStatFragment : Fragment() {

    private lateinit var viewModel: TimeStatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: TimeStatFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.time_stat_fragment, container, false
        )

        viewModel = ViewModelProvider(this).get(TimeStatViewModel::class.java)

        binding.text.text = "slkdf"

        return binding.root
    }



}
