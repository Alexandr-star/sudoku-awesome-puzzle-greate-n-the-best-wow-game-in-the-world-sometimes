package com.app.game.sudoku.ui.dashboard.dontclassic

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.TimeStatFragmentBinding

class TimeStatFragment : Fragment() {

    private lateinit var timeStatViewModel: TimeStatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: TimeStatFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.time_stat_fragment, container, false
        )

        timeStatViewModel = ViewModelProvider(this).get(TimeStatViewModel::class.java)
        timeStatViewModel.gamesStatisticEasy.observe(viewLifecycleOwner, Observer { listStat ->
            binding.textViewGamesEasyData.text = listStat.component1()
            binding.textViewGamesWonEasyData.text = listStat.component2()
            binding.textViewGamesTimeEasyData.text = listStat.component3()
        })

        timeStatViewModel.gamesStatisticMedium.observe(viewLifecycleOwner, Observer { listStat ->
            binding.textViewGamesMediumData.text = listStat.component1()
            binding.textViewGamesWonMediumData.text = listStat.component2()
            binding.textViewGamesTimeMediumData.text = listStat.component3()
        })

        timeStatViewModel.gamesStatisticHard.observe(viewLifecycleOwner, Observer { listStat ->
            binding.textViewGamesHardData.text = listStat.component1()
            binding.textViewGamesWonHardData.text = listStat.component2()
            binding.textViewGamesTimeHardData.text = listStat.component3()
        })

        return binding.root
    }



}
