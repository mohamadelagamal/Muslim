package com.example.muslim.ui.quran.saved

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muslim.database.bookmark.SavedPage
import com.example.muslim.database.bookmark.mydatabase.QuranDataBase
import com.example.muslim.ui.base.activity.BaseViewModel
import com.example.muslim.ui.quran.saved.adapter.SavedPageFragmentAdapter

class SavedViewModel:BaseViewModel<Navigator>() {


    val quranLiveData = MutableLiveData<List<SavedPage>>()

    fun dataQuran(context:Context){
        val savedPageDao = QuranDataBase.getInstance(context).savedPageDao()
        val savedPage = savedPageDao.getAllQuran()
        quranLiveData.value = savedPage
    }

}