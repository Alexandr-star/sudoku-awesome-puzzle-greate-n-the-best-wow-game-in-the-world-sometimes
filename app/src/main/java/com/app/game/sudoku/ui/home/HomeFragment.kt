package com.app.game.sudoku.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)
        return binding.root
    }
}
