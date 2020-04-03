package com.app.game.sudoku.ui.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.game.sudoku.ui.dashboard.classic.ClassicStatFragment
import com.app.game.sudoku.ui.dashboard.dontclassic.TimeStatFragment

class DashboardAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> ClassicStatFragment()
            else -> return TimeStatFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "ClassicMode"
            else -> return "TimeMode"
        }
    }
}