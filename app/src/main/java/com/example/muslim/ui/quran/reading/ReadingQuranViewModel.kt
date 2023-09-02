package com.example.muslim.ui.quran.reading

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.muslim.database.bookmark.SavedPage
import com.example.muslim.database.bookmark.mydatabase.QuranDataBase
import com.example.muslim.extension.Constant
import com.example.muslim.ui.base.activity.BaseViewModel
import com.example.muslim.ui.quran.reading.adapter.ReadingQuranAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReadingQuranViewModel(): BaseViewModel<Navigator>() {

//  // create list of images to show in viewpager
     val mutableState = MutableStateFlow("Nothing")

     fun addPage(context: Context,savedPage: SavedPage) {
        // insert savedPage to savedPageDao and check if it inserted or not
         val savedPageDao = QuranDataBase.getInstance(context).savedPageDao()
         viewModelScope.launch(Dispatchers.Default){
                if (savedPageDao.insertQuran(savedPage) >= 0) {
                    mutableState.emit(Constant.ADDING)
                    savedPageDao.insertQuran(savedPage)
                }

         }
     }
    fun clickOnBookmark(){
    }

    // move to parts screen
    fun clickOnParts(){
        navigator?.openParts()
    }
    fun clickReadingQuran(){
        navigator?.openReadingQuran()
    }
    fun clickAbout(){
        navigator?.openAbout()
    }
    fun clickSearch(){
        navigator?.openSearch()
    }
}