package com.example.prakmb.rest

import com.example.prakmb.data.Notes
import com.example.prakmb.data.Response
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface API {
    // Get all data
    @GET("read.php")
    fun getNotes(): Call<ArrayList<Notes>>

//    @FormUrlEncoded
//    @POST("add.php")
//    fun addNoteDetail(
//        @Field("country_name") country_name: String?,
//        @Field("country_continent") country_continent: String?,
//        @Field("country_population") country_population: String?,
//        @Field("country_description") country_description: String?
//    ): Call<Response>
}