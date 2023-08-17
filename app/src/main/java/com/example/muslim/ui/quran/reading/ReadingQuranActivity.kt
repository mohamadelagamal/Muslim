package com.example.muslim.ui.quran.reading

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.muslim.R
import com.example.muslim.databinding.ActivityReadingQuranBinding
import com.example.muslim.extension.Constant
import com.example.muslim.ui.base.activity.BaseActivity

class ReadingQuranActivity : BaseActivity<ActivityReadingQuranBinding,ReadingQuranViewModel>(),Navigator {

    lateinit var suraId:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        suraId=intent.getStringExtra(Constant.SURA_ID).toString()
        viewDataBinding.vmQuranReading=viewModel
        viewModel.navigator=this



    }

    override fun getLayoutID(): Int = R.layout.activity_reading_quran

    override fun makeViewModelProvider(): ReadingQuranViewModel = ViewModelProvider(this).get(ReadingQuranViewModel::class.java)

}