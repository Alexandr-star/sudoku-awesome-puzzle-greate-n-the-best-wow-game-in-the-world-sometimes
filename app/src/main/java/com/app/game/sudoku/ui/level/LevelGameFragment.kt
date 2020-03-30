package com.app.game.sudoku.ui.level

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.FragmentLevelBinding
import java.util.ArrayList

class LevelGameFragment : Fragment() {
    private lateinit var levelGameViewModel: LevelGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLevelBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_level, container, false)

        levelGameViewModel = ViewModelProvider(this).get(LevelGameViewModel::class.java)

        val mode = arguments!!.getInt("modeSend")
        val bundleData = Bundle()


        binding.easyButton.setOnClickListener {v: View ->
            bundleData.putInt("mode", mode)
            bundleData.putInt("level", 1)
            v.findNavController().navigate(
                R.id.action_navigation_level_to_navigation_gameboard,
                bundleData
            )
        }

        binding.mediumButton.setOnClickListener {v: View ->
            bundleData.putInt("mode", mode)
            bundleData.putInt("level", 2)
            v.findNavController().navigate(
                R.id.action_navigation_level_to_navigation_gameboard,
                bundleData
            )
        }

        binding.hardButton.setOnClickListener {v: View ->
            bundleData.putInt("mode", mode)
            bundleData.putInt("level", 3)
            v.findNavController().navigate(
                R.id.action_navigation_level_to_navigation_gameboard,
                bundleData
            )
        }

        return binding.root
    }

}