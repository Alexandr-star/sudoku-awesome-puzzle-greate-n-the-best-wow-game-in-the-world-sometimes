package com.app.game.sudoku.ui.menu

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager

import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.FragmentMenuBinding
import com.google.android.material.tabs.TabLayout

class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentMenuBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu, container, false
        )

        val menuAdapter = MenuAdapter(childFragmentManager)
        val viewPager = binding.root.findViewById<ViewPager>(R.id.viewPager)
        val tabs = binding.root.findViewById<TabLayout>(R.id.tabs)
        viewPager.adapter = menuAdapter
        tabs.setupWithViewPager(viewPager)

        return binding.root
    }



}
