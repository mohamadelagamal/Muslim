package com.example.muslim.ui.ziker

import android.R.attr.data
import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.muslim.R
import com.example.muslim.model.ziker.ZikerNames
import com.example.muslim.ui.base.activity.BaseViewModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException


class ZikerViewModel: BaseViewModel<Navigator>() {
fun gotoContnet(){
    navigator?.openContent()
}
 val zikerContent=MutableLiveData<List<ZikerNames>>()

    fun loadZikerList(fileName: String, context: Context): LiveData<List<ZikerNames>> {
        val jsonArrayString = getJsonArrayFromAssets(fileName, context)
        val gson = Gson()
        val listZikerType = object : TypeToken<List<ZikerNames>>() {}.type
        val zikerList: List<ZikerNames> = gson.fromJson(jsonArrayString, listZikerType)
        for (i in zikerList.indices) {
            val image = zikerList[i].image
            val id = context.resources.getIdentifier(image, "drawable", context.packageName)
            zikerList[i].image = id.toString()
        }
        zikerContent.value = zikerList
        return zikerContent
    }


    private fun getJsonArrayFromAssets(fileName: String, context: Context): String {
        return context.assets.open(fileName).use {
            it.bufferedReader().use { reader ->
                reader.readText()
            }
        }
    }
//    fun loadZikerList(context: Context):LiveData<List<ZikerNames>>{
//        val jsonFileString = getJsonDataFromAsset(context, "azkar.json")
//        val gson = Gson()
//        val listZikerType = object : TypeToken<List<ZikerNames>>() {}.type
//        val zikerList: List<ZikerNames> = gson.fromJson(jsonFileString, listZikerType)
//        for (i in zikerList.indices){
//
//            val image=zikerList[i].image
//            val id=context.resources.getIdentifier(image,"drawable",context.packageName)
//            zikerList[i].image=id.toString()
//        }
//
//        zikerContent.value=zikerList
//        return zikerContent
//    }
//    fun getJsonDataFromAsset(context: Context, fileName: String): String? {
//        val jsonString: String
//        try {
//            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
//        } catch (ioException: IOException) {
//            ioException.printStackTrace()
//            return null
//        }
//        return jsonString
//    }
}