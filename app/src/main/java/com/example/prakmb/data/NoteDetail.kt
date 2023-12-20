package com.example.prakmb.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteDetail(
    var id : Int? ,
    var title : String?,
    var content : String?,
    var date : String?,
    var status : Int?
) : Parcelable
