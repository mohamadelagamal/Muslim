package com.example.muslim.ui.quran

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.muslim.database.SurahInfoItem
import com.example.muslim.ui.base.activity.BaseViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuranViewModel:BaseViewModel<Navigator>() {
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