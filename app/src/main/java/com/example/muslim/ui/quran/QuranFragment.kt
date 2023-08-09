package com.example.muslim.ui.quran

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muslim.R
import com.example.muslim.databinding.FragmentQuranBinding
import com.example.muslim.ui.base.fragment.BaseFragment

class QuranFragment : BaseFragment<FragmentQuranBinding,QuranViewModel>(),Navigator {
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

    }

}