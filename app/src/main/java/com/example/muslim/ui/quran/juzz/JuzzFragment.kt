package com.example.muslim.ui.quran.juzz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muslim.R
import com.example.muslim.databinding.FragmentJuzzBinding
import com.example.muslim.model.quran.SurahInfoItem
import com.example.muslim.ui.base.fragment.BaseFragment
import com.example.muslim.ui.quran.juzz.adapter.SelectionQuranAdapter

class JuzzFragment : BaseFragment<FragmentJuzzBinding,JuzzViewModel>(),Navigator {

    val list = listOf<SurahInfoItem>(
        SurahInfoItem(index = "1", titleAr = "الجزء 1", title = "selection one",type = "pages", count = 1, place = "21"),
        SurahInfoItem(index = "2", titleAr = "الجزء 2", title = "selection two",type = "pages", count = 22, place = "41"),
        SurahInfoItem(index = "3", titleAr = "الجزء 3", title = "selection three",type = "pages", count = 42, place = "61"),
        SurahInfoItem(index = "4", titleAr = "الجزء 4", title = "selection four",type = "pages", count = 62, place = "81"),
        SurahInfoItem(index = "5", titleAr = "الجزء 5", title = "selection five",type = "pages", count = 82, place = "101"),
        SurahInfoItem(index = "6", titleAr = "الجزء 6", title = "selection six",type = "pages", count = 102, place = "121"),
        SurahInfoItem(index = "7", titleAr = "الجزء 7", title = "selection seven",type = "pages", count = 121, place = "141"),
        SurahInfoItem(index = "8", titleAr = "الجزء 8", title = "selection eight",type = "pages", count = 142, place = "161"),
        SurahInfoItem(index = "9", titleAr = "الجزء 9", title = "selection nine",type = "pages", count = 162, place = "181"),
        SurahInfoItem(index = "10", titleAr = "الجزء 10", title = "selection ten",type = "pages", count = 182, place = "201"),
        SurahInfoItem(index = "11", titleAr = "الجزء 11", title = "selection eleven",type = "pages", count = 202, place = "221"),
        SurahInfoItem(index = "12", titleAr = "الجزء 12", title = "selection twelve",type = "pages", count = 222, place = "241"),
        SurahInfoItem(index = "13", titleAr = "الجزء 13", title = "selection thirteen",type = "pages", count = 242, place = "261"),
        SurahInfoItem(index = "14", titleAr = "الجزء 14", title = "selection fourteen",type = "pages", count = 262, place = "281"),
        SurahInfoItem(index = "15", titleAr = "الجزء 15", title = "selection fifteen",type = "pages", count = 282, place = "301"),
        SurahInfoItem(index = "16", titleAr = "الجزء 16", title = "selection sixteen",type = "pages", count = 302, place = "321"),
        SurahInfoItem(index = "17", titleAr = "الجزء 17", title = "selection seventeen",type = "pages", count = 322, place = "341"),
        SurahInfoItem(index = "18", titleAr = "الجزء 18", title = "selection eighteen",type = "pages", count = 342, place = "361"),
        SurahInfoItem(index = "19", titleAr = "الجزء 19", title = "selection nineteen",type = "pages", count = 362, place = "381"),
        SurahInfoItem(index = "20", titleAr = "الجزء 20", title = "selection twenty",type = "pages", count = 382, place = "401"),
        SurahInfoItem(index = "21", titleAr = "الجزء 21", title = "selection twenty one",type = "pages", count = 402, place = "421"),
        SurahInfoItem(index = "22", titleAr = "الجزء 22", title = "selection twenty two",type = "pages", count = 422, place = "441"),
        SurahInfoItem(index = "23", titleAr = "الجزء 23", title = "selection twenty three",type = "pages", count = 442, place = "461"),
        SurahInfoItem(index = "24", titleAr = "الجزء 24", title = "selection twenty four",type = "pages", count = 462, place = "481"),
        SurahInfoItem(index = "25", titleAr = "الجزء 25", title = "selection twenty five",type = "pages", count = 482, place = "502"),
        SurahInfoItem(index = "26", titleAr = "الجزء 26", title = "selection twenty six",type = "pages", count = 502, place = "521"),
        SurahInfoItem(index = "27", titleAr = "الجزء 27", title = "selection twenty seven",type = "pages", count = 522, place = "541"),
        SurahInfoItem(index = "28", titleAr = "الجزء 28", title = "selection twenty eight",type = "pages", count = 542, place = "561"),
        SurahInfoItem(index = "29", titleAr = "الجزء 29", title = "selection twenty nine",type = "pages", count = 562, place = "581"),
        SurahInfoItem(index = "30", titleAr = "الجزء 30", title = "selection thirty",type = "pages", count = 582, place = "604"),
    )
    lateinit var adapter: SelectionQuranAdapter
    override fun getLayoutID(): Int = R.layout.fragment_juzz

    override fun makeViewModelProvider(): JuzzViewModel = JuzzViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.item=viewModel
        viewModel.navigator=this


        init()
    }

    private fun init() {
        adapter= SelectionQuranAdapter(list,requireContext())
        viewDataBinding.fragJuzzRv.adapter=adapter
    }

}