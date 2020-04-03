package com.app.game.sudoku.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.app.game.sudoku.R
import com.app.game.sudoku.databinding.FragmentDashboardBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDashboardBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dashboard, container, false)

        val dashboardAdapter = DashboardAdapter(parentFragmentManager)

        val viewPager = binding.root.findViewById<ViewPager>(R.id.viewPager)
        val tabs = binding.root.findViewById<TabLayout>(R.id.tabs)
        viewPager.adapter = dashboardAdapter


        tabs.setupWithViewPager(viewPager)

        return binding.root
    }

}

