package com.example.muslim.ui.quran

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.muslim.R
import com.example.muslim.databinding.FragmentQuranBinding
import com.example.muslim.extension.Constant
import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.quran.adapter.QuranHomeAdapter
import com.example.muslim.ui.quran.reading.ReadingQuranActivity
import com.example.muslim.ui.quran.adapter.SurahAdapters
import com.example.muslim.ui.quran.juzz.JuzzFragment
import com.google.android.material.tabs.TabLayoutMediator


class QuranFragment : Fragment() {
    lateinit var adapter: QuranHomeAdapter

    private lateinit var binding: FragmentQuranBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuranBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = QuranHomeAdapter(requireActivity())
        binding.quranHomeViewpager.adapter = adapter
        setUpTabLayout()

    }

    private fun setUpTabLayout() {
        TabLayoutMediator(
            binding.fragQuranContainerTablayout,
            binding.quranHomeViewpager
        ) { tab, position ->
            when (position) {
//                0 -> tab.text = "السور"
//                1 -> tab.text = "الأجزاء"
//                else -> tab.text = "العلامات المرجعيه"
                0 -> tab.text = "Surah"
                1 -> tab.text = "Parts"
                else -> tab.text = "Bookmarks"
            }
        }.attach()
    }

}
