package com.example.muslim.ui.hades

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muslim.R
import com.example.muslim.databinding.FragmentHadesBinding
import com.example.muslim.ui.base.fragment.BaseFragment

class HadesFragment : BaseFragment<FragmentHadesBinding,HadesViewModel>(),Navigator {


    override fun getLayoutID(): Int {
        return R.layout.fragment_hades
    }

    override fun makeViewModelProvider(): HadesViewModel {
        return HadesViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.vmHades=viewModel
        viewModel.navigator=this

    }
}