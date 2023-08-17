package com.example.muslim.ui.quran.soraa

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim.R
import com.example.muslim.databinding.FragmentQuranBinding
import com.example.muslim.databinding.FragmentSoraaBinding
import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.quran.adapter.SurahAdapters

class SoraaFragment : BaseFragment<FragmentSoraaBinding, SoraaViewModel>(),Navigator {
    lateinit var adapter:SurahAdapters
    override fun getLayoutID(): Int {
        return R.layout.fragment_soraa
    }

    override fun makeViewModelProvider(): SoraaViewModel {
        return SoraaViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.customShimmer.startShimmer()
        viewDataBinding.item=viewModel
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
            viewDataBinding.customShimmer.stopShimmer()
            viewDataBinding.customShimmer.visibility = View.GONE
            adapter.changeData(surah)
        }
    }

}