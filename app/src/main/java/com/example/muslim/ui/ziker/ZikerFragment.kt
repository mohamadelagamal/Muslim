package com.example.muslim.ui.ziker

import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muslim.R
import com.example.muslim.database.ziker.ZikerNames
import com.example.muslim.databinding.FragmentZikerBinding
import com.example.muslim.extension.Constant

import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.ziker.namesAdapter.NamesAdapter
import com.example.muslim.ui.ziker.zikerContent.ZikerContentActivity

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
        init()

    }

     var adapter= NamesAdapter(null)
    fun init(){
        viewDataBinding.recyclerZekrNames.adapter=adapter
        try {
            val fileName = "azkar.json"
            viewModel.loadZikerList(fileName,requireActivity())
            Log.e("data", "init: ${viewModel.zikerContent.value}")
        } catch (e: Exception) {
            Log.d("data", "init: ${e.message}")
        }
        try {
            viewModel.zikerContent.observe(viewLifecycleOwner){
                adapter.changeData(it)
            }
        } catch (e: Exception) {
            Log.e("data", "init: ${e.message}")
        }

        viewModel.gotoContnet()
    }

    override fun openContent() {
        adapter.onItemClickListener=object :NamesAdapter.OnItemClickListener{
            override fun onClick(pos: Int, item: ZikerNames) {
               val intent=Intent(requireContext(),ZikerContentActivity::class.java)
                //intent.putExtra(Constant.Extra_Ziker_pos,pos)
                intent.putExtra(Constant.Extra_Ziker_cate,item)
                startActivity(intent)
            }
        }
    }

}