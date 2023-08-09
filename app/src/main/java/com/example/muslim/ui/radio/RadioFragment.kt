package com.example.muslim.ui.radio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muslim.R
import com.example.muslim.databinding.FragmentRadioBinding
import com.example.muslim.ui.base.fragment.BaseFragment

class RadioFragment : BaseFragment<FragmentRadioBinding,RadioViewModel>(), Navigator {
    override fun getLayoutID(): Int {
        return R.layout.fragment_radio
    }

    override fun makeViewModelProvider(): RadioViewModel {
        return RadioViewModel()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.vmRadio=viewModel
        viewModel.navigator=this

    }


}