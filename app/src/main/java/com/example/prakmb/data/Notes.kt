package com.example.prakmb.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date


@Parcelize
data class Notes(
    var id: Int?,
    var title:String?,
    var content:String?,
    var date: String?,
    var status :Boolean?
) : Parcelable

