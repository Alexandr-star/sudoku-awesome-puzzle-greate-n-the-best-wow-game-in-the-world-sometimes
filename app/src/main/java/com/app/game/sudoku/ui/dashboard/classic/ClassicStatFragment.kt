package com.app.game.sudoku.ui.dashboard.classic

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.ClassicStatFragmentBinding

class ClassicStatFragment : Fragment() {

    private lateinit var viewModel: ClassicStatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding: ClassicStatFragmentBinding = DataBindingUtil.inflate(
        inflater, R.layout.classic_stat_fragment, container, false)

        viewModel = ViewModelProvider(this).get(ClassicStatViewModel::class.java)

        binding.text.text = "sdfsaassf"


        return binding.root
    }


}
