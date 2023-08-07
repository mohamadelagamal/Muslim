package com.example.muslim.ui.home

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.muslim.R
import com.example.muslim.databinding.ActivityHomeBinding
import com.example.muslim.extension.uncheckAllItems
import com.example.muslim.ui.base.activity.BaseActivity
import com.example.muslim.ui.hades.HadesFragment
import com.example.muslim.ui.quran.QuranFragment
import com.example.muslim.ui.radio.RadioFragment
import com.example.muslim.ui.shalat.ShalatFragment
import com.example.muslim.ui.ziker.ZikerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(),Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vmHome=viewModel
        viewModel.navigator=this

        viewDataBinding.bottomNavigationView.background = null
        viewDataBinding.bottomNavigationView.menu.getItem(2).isEnabled = false // disable the middle item (index 2)
        switchFragments()


    }

    override fun getLayoutID(): Int {
        return R.layout.activity_home
    }

    override fun makeViewModelProvider(): HomeViewModel {
        return ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    fun switchFragments(){
        transactFragment(ShalatFragment())
        viewDataBinding.bottomNavigationView.uncheckAllItems()

        viewDataBinding.bottomNavigationView.setOnItemSelectedListener OnItemSelectedListener@{
            when (it.itemId) {
                R.id.quranNav -> {
                    transactFragment(QuranFragment())
                }
                R.id.hadesNav -> {
                    transactFragment(HadesFragment())
                }
                R.id.radioNav -> {
                    transactFragment(RadioFragment())
                }
                R.id.zikrNav -> {
                    transactFragment(ZikerFragment())
                }
            }
            return@OnItemSelectedListener true
        }
        viewDataBinding.fab.setOnClickListener {
            transactFragment(ShalatFragment())
        }

    }
    fun transactFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}