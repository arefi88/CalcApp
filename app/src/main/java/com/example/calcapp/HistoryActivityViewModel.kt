package com.example.calcapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryActivityViewModel : ViewModel() {
    var recyclerListData:MutableLiveData<List<CalcResult>> = MutableLiveData()

    fun getCalcResultLiveData():MutableLiveData<List<CalcResult>> {
        return recyclerListData
    }

}