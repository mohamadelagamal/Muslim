package com.example.muslim.ui.base.activity

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


abstract class BaseViewModel<N>: ViewModel(){
    var navigator:N?=null
    val showLoading = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<String>()
}