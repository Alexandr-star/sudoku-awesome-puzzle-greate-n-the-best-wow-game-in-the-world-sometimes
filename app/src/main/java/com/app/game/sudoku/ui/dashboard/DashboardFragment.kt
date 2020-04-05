package com.app.game.sudoku.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.FragmentDashboardBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment() {
    val sharedPref = activity?.getSharedPreferences(getString(R.string.pref_file_stat_game),
        Context.MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDashboardBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dashboard, container, false)

        val dashboardAdapter = DashboardAdapter(childFragmentManager)
        val viewPager = binding.root.findViewById<ViewPager>(R.id.viewPager)
        val tabs = binding.root.findViewById<TabLayout>(R.id.tabs)
        viewPager.adapter = dashboardAdapter
        tabs.setupWithViewPager(viewPager)

        return binding.root
    }


}

