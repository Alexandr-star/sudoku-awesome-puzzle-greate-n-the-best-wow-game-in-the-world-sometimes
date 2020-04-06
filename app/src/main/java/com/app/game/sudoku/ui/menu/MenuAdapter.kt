package com.app.game.sudoku.ui.menu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.app.game.sudoku.ui.dashboard.DashboardFragment
import com.app.game.sudoku.ui.home.HomeFragment

class MenuAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            else -> DashboardFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Sudoku"
            else -> return "Statistics"
        }
    }

}