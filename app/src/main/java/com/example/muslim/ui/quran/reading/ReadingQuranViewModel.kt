package com.example.muslim.ui.quran.reading

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.muslim.database.bookmark.SavedPage
import com.example.muslim.extension.Constant
import com.example.muslim.ui.base.activity.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReadingQuranViewModel(): BaseViewModel<Navigator>() {

  // create list of images to show in viewpager
    var images = MutableLiveData<List<Int>>() // list of images
     val mutableState = MutableStateFlow("Nothing")
   //  private val savedPageDao = QuranDataBase.getInstance(getApplication())?.savedPageDao()

     fun addPage(savedPage: SavedPage) {
         viewModelScope.launch(Dispatchers.IO){
           //  if (savedPageDao!!.insertNewPage(savedPage) >= 0) {
                 mutableState.emit(Constant.ADDING)
             //}

         }
     }
    fun clickOnBookmark(){
        navigator?.clickOnBookmark()
    }

}