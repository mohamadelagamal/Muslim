package com.example.muslim.ui.hades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muslim.R
import com.example.muslim.database.HadesData
import com.example.muslim.databinding.FragmentHadesBinding
import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.hades.adapter.HadesAdapter

class HadesFragment : BaseFragment<FragmentHadesBinding, HadesViewModel>(), Navigator {

    val list = listOf(
        HadesData(
            "صحيح البخاري",
        ),
        HadesData(
            "صحيح مسلم ",
        ),
        HadesData(
            "سنن النسائي",
        ),
        HadesData(
            "سنن أبي داود",
        ),
        HadesData(
            "سنن الترمذي",
        ),
        HadesData(
            "سنن ابن ماجه",
        ),
        HadesData(
            "موطأ مالك ",
        ),
        HadesData(
            "سنن الدارمي",
        ),
        HadesData(
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