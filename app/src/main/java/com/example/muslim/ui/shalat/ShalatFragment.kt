package com.example.muslim.ui.shalat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muslim.R
import com.example.muslim.databinding.FragmentShalatBinding
import com.example.muslim.ui.base.fragment.BaseFragment

class ShalatFragment : BaseFragment<FragmentShalatBinding,ShalatViewModel>(), Navigator {
    override fun getLayoutID(): Int {
        return R.layout.fragment_shalat
    }

    override fun makeViewModelProvider(): ShalatViewModel {
        return ShalatViewModel()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.vmShalat=viewModel
        viewModel.navigator=this

    }


}