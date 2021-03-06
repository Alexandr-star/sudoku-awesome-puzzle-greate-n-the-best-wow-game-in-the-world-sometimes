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
    private val CLASSIC_GAME = 1
    private val TIME_GAME = 2


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        val bundleData = Bundle()

        binding.classicGameButton.setOnClickListener {v: View ->
            bundleData.putInt("modeSend", CLASSIC_GAME)
            v.findNavController().navigate(
                R.id.action_navigation_menu_to_navigation_level,
                bundleData
            )
        }
        binding.timeGameButton.setOnClickListener {v: View ->
            bundleData.putInt("modeSend", TIME_GAME)
            v.findNavController().navigate(
                R.id.action_navigation_menu_to_navigation_level,
                bundleData
            )
        }
        return binding.root
    }
}
