package com.example.muslim.ui.base.activity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


abstract class BaseViewModel<N>: ViewModel(){
    var navigator:N?=null
    val showLoading = MutableLiveData<Boolean>()
    val messageLiveData = MutableLiveData<String>()

}