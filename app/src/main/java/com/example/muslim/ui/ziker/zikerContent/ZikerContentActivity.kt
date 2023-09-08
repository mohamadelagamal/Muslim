package com.example.muslim.ui.ziker.zikerContent

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.muslim.R
import com.example.muslim.databinding.ActivityZikerContentBinding
import com.example.muslim.extension.Constant
import com.example.muslim.model.ziker.ZikerNames
import com.example.muslim.ui.base.activity.BaseActivity
import com.example.muslim.ui.ziker.zikerContent.contentAdapter.ZikerContentAdapter

class ZikerContentActivity:BaseActivity<ActivityZikerContentBinding,ZikerContentViewModel>(),Navigator {
    lateinit var adapter: ZikerContentAdapter
    var zikerContentList:ZikerNames?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // zekrId=intent.getIntExtra(Constant.Extra_Ziker_pos,0)

        try {
            zikerContentList=intent.getSerializableExtra(Constant.Extra_Ziker_cate) as ZikerNames
        } catch (e: Exception) {
            Log.e("data", "onCreate: ${e.message}")
        }
        viewDataBinding.vmZiker=viewModel
        viewModel.navigator=this
        init()

    }

    override fun getLayoutID(): Int {
        return R.layout.activity_ziker_content
    }

    override fun makeViewModelProvider(): ZikerContentViewModel {
       return ViewModelProvider(this)[ZikerContentViewModel::class.java]
    }

fun init(){
adapter= ZikerContentAdapter(null)
    viewDataBinding.RecyclerZekrContent.adapter=adapter
    try {
        adapter.changeData(zikerContentList?.arry)
    } catch (e: Exception) {
        Log.e("data", "init: ${e.message}")
    }

}

}