package com.example.muslim.ui.ziker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muslim.R
import com.example.muslim.databinding.FragmentZikerBinding
import com.example.muslim.model.ziker.ZikerNames
import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.ziker.namesAdapter.NamesAdapter

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
    val names_ziker= mutableListOf(
      ZikerNames(1,"أذكار الصباح",R.drawable.sun,"أذكار الصباح"),
      ZikerNames(133,"أذكار المساء",R.drawable.night,"أذكار المساء"),
      ZikerNames(3,"أذكار الاستيقاظ من النوم",R.drawable.wakeup,"أذكار الأستيقاظ"),
      ZikerNames(2,"أذكار النوم",R.drawable.sleep,"أذكار النوم"),
      ZikerNames(10,"دعاء الذهاب إلى المسجد",R.drawable.mosque,"أذكار المسجد"),
      ZikerNames(9,"الذكر عند دخول المنزل",R.drawable.home,"أذكار المنزل"),
      ZikerNames(6,"الذكر قبل الوضوء",R.drawable.wudhu,"أذكار الوضوء"),
      ZikerNames(27,"الأذكار بعد السلام من الصلاة",R.drawable.praying,"أذكار بعد الصلاة"),
      ZikerNames(115,"كيف يلبي المحرم في الحج أو العمرة ؟",R.drawable.hajj,"الحج والعمرة"),
      ZikerNames(69,"الدعاء قبل الطعام",R.drawable.restaurant,"الطعام"),
      ZikerNames(13,"أذكار الآذان",R.drawable.adzan,"الأذان")
    )
     var adapter= NamesAdapter(names_ziker)
    fun init(){
        viewDataBinding.recyclerZekrNames.adapter=adapter
    }

}