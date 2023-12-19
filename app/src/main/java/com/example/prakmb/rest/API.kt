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
    // Get all notes
    @GET("read.php")
    fun getNotes(): Call<ArrayList<Notes>>

    @GET("read_finished.php")
    fun getFinishedNotes(): Call<ArrayList<Notes>>

    // Add new note
    @FormUrlEncoded
    @POST("add.php")
    fun addNoteDetail(
        @Field("title") title: String?,
        @Field("content") content: String?,
        @Field("date") date: String?,
    ): Call<Response>
}