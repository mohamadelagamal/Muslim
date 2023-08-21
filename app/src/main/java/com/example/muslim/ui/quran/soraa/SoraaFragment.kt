package com.example.muslim.ui.quran.soraa

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.muslim.R
import com.example.muslim.databinding.FragmentSoraaBinding
import com.example.muslim.extension.Constant
import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.quran.reading.ReadingQuranActivity
import com.example.muslim.ui.quran.soraa.adapter.SurahAdapters

class SoraaFragment : BaseFragment<FragmentSoraaBinding, SoraaViewModel>(),Navigator {
    lateinit var adapter: SurahAdapters
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
        adapter= SurahAdapters(null)
        viewDataBinding.RecyclerQuranNames.adapter=adapter
        adapter.onItemClickListener=object :SurahAdapters.OnItemClickListener{
            override fun onItemClick(item: String) {
                val intent = Intent(requireContext(), ReadingQuranActivity::class.java)
                intent.putExtra(Constant.SURA_ID, item)
                startActivity(intent)
            }

        }
    }

    private fun subscribeToLiveData() {

        viewModel.getSurahList(requireContext())
        viewModel.surahList.observe(viewLifecycleOwner) {surah->
            viewDataBinding.customShimmer.stopShimmer()
            viewDataBinding.customShimmer.visibility = View.GONE
            adapter.changeData(surah)        }
    }


}