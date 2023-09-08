package com.example.muslim.application

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.muslim.database.bookmark.mydatabase.QuranDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MyApp: Application() {

    companion object{
        lateinit var instance: MyApp

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        QuranDataBase.getInstance(this)
    }




}