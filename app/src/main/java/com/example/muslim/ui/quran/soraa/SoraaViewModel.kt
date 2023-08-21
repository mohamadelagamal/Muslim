package com.example.muslim.ui.quran.soraa

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.muslim.model.quran.SurahInfoItem
import com.example.muslim.ui.base.activity.BaseViewModel
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SoraaViewModel: BaseViewModel<Navigator>() {
    val surahList = MutableLiveData<List<SurahInfoItem>>()
    fun getSurahList(context: Context): LiveData<List<SurahInfoItem>> {
            val jsonString = context.assets.open("surah.json").bufferedReader().use { it.readText() }
            val listCountryType = object : TypeToken<List<SurahInfoItem>>() {}.type
            val surahInfoList = Gson().fromJson<List<SurahInfoItem>>(jsonString, listCountryType)

            // Store the function value in the LiveData parameter
            surahList.value = surahInfoList


        return surahList
    }

}