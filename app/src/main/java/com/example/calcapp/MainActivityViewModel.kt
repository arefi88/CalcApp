package com.example.calcapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    val resultCalcLiveData:MutableLiveData<String> = MutableLiveData<String>()


    fun updateTv(str:String){
        resultCalcLiveData.value=str
    }
}