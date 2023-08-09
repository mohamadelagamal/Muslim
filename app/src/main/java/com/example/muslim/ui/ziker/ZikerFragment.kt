package com.example.muslim.ui.ziker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muslim.R
import com.example.muslim.databinding.FragmentZikerBinding
import com.example.muslim.ui.base.fragment.BaseFragment

class ZikerFragment : BaseFragment<FragmentZikerBinding,ZikerViewModel>() , Navigator{
    override fun getLayoutID(): Int {
        return R.layout.fragment_ziker
    }

    override fun makeViewModelProvider(): ZikerViewModel {
        return ZikerViewModel()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.vmZiker=viewModel
        viewModel.navigator=this

    }


}