package com.example.muslim.ui.hades

import android.os.Bundle
import android.view.View
import com.example.muslim.R
import com.example.muslim.model.hadith.HadithDataBase
import com.example.muslim.databinding.FragmentHadesBinding
import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.hades.adapter.HadesAdapter

class HadesFragment : BaseFragment<FragmentHadesBinding, HadesViewModel>(), Navigator {

    val list = listOf(
        HadithDataBase(
            "صحيح البخاري",
        ),
        HadithDataBase(
            "صحيح مسلم ",
        ),
        HadithDataBase(
            "سنن النسائي",
        ),
        HadithDataBase(
            "سنن أبي داود",
        ),
        HadithDataBase(
            "سنن الترمذي",
        ),
        HadithDataBase(
            "سنن ابن ماجه",
        ),
        HadithDataBase(
            "موطأ مالك ",
        ),
        HadithDataBase(
            "سنن الدارمي",
        ),
        HadithDataBase(
            "مسند أحمد",
        ),
    )

    override fun getLayoutID(): Int {
        return R.layout.fragment_hades
    }

    override fun makeViewModelProvider(): HadesViewModel {
        return HadesViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.vmHades = viewModel
        viewModel.navigator = this
        val adapter = HadesAdapter(list)
        viewDataBinding.rvHades.adapter = adapter

    }
}