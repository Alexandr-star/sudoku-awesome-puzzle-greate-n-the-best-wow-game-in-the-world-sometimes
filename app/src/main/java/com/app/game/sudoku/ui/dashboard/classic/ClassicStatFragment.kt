package com.app.game.sudoku.ui.dashboard.classic

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.util.TimeUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.ClassicStatFragmentBinding
import com.app.game.sudoku.statisticStore.StatisticPreference
import java.text.SimpleDateFormat
import java.util.*

class ClassicStatFragment : Fragment() {

    private lateinit var classicStatViewModel: ClassicStatViewModel

    val sharedPref = activity?.getSharedPreferences(getString(R.string.pref_file_stat_game),Context.MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding: ClassicStatFragmentBinding = DataBindingUtil.inflate(
        inflater, R.layout.classic_stat_fragment, container, false)

        classicStatViewModel = ViewModelProvider(this).get(ClassicStatViewModel::class.java)

        classicStatViewModel.gamesStatisticEasy.observe(viewLifecycleOwner, Observer { listStat ->
            binding.textViewGamesEasyData.text = listStat.component1()
            binding.textViewGamesWonEasyData.text = listStat.component2()
            //val time = listStat.component3().toLong()
            binding.textViewGamesTimeEasyData.text = listStat.component3()
        })

        classicStatViewModel.gamesStatisticMedium.observe(viewLifecycleOwner, Observer { listStat ->
            binding.textViewGamesMediumData.text = listStat.component1()
            binding.textViewGamesWonMediumData.text = listStat.component2()
            //val time = listStat.component3().toLong()
            binding.textViewGamesTimeMediumData.text = listStat.component3()
        })

        classicStatViewModel.gamesStatisticHard.observe(viewLifecycleOwner, Observer { listStat ->
            binding.textViewGamesHardData.text = listStat.component1()
            binding.textViewGamesWonHardData.text = listStat.component2()
            binding.textViewGamesTimeHardData.text = listStat.component3()
        })


        return binding.root
    }




}