package com.example.calcapp

import android.os.Parcel
import android.os.Parcelable

data class CalcResult(val operation: String?,val result:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(operation)
        parcel.writeString(result)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CalcResult> {
        override fun createFromParcel(parcel: Parcel): CalcResult {
            return CalcResult(parcel)
        }

        override fun newArray(size: Int): Array<CalcResult?> {
            return arrayOfNulls(size)
        }
    }
}


