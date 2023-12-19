package com.example.prakmb.data

import java.text.SimpleDateFormat
import java.util.Date
@Parc
data class Notes(
    var id: Int,
    var title:String,
    var content:String,
    var date: String,
    var status :Boolean
)
