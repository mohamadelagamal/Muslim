package com.example.muslim.ui.quran.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.muslim.ui.quran.juzz.JuzzFragment
import com.example.muslim.ui.quran.saved.SavedPageFragment
import com.example.muslim.ui.quran.soraa.SoraaFragment

class QuranHomeAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SoraaFragment()
            else -> SavedPageFragment()

        }
    }
}