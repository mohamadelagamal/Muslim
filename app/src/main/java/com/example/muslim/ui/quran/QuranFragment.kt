package com.example.muslim.ui.quran

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.example.muslim.R
import com.example.muslim.databinding.FragmentQuranBinding
import com.example.muslim.model.SurahInfoItem
import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.quran.surah.SurahAdapters


class QuranFragment : BaseFragment<FragmentQuranBinding,QuranViewModel>(),Navigator {
    lateinit var adapter:SurahAdapters
    override fun getLayoutID(): Int {
        return R.layout.fragment_quran
    }

    override fun makeViewModelProvider(): QuranViewModel {
        return QuranViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.vmQuran=viewModel
        viewModel.navigator=this
        subscribeToLiveData()
        init()


    }

    private fun init() {
        adapter=SurahAdapters(null)
        viewDataBinding.RecyclerQuranNames.adapter=adapter
    }

    private fun subscribeToLiveData() {
        viewModel.getSurahList(requireContext())
            viewModel.surahList.observe(viewLifecycleOwner) {surah->
               adapter.changeData(surah)
            }
    }

}