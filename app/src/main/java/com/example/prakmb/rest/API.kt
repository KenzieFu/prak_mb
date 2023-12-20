package com.example.prakmb.rest

import com.example.prakmb.data.NoteDetail
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

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteNotesDetail(
        @Field("notesId") notesId: String?
    ): Call<Response>
    @GET("detail.php")
    fun getDetails(
        @Query("notesId") notesId: Int?
    ):Call<NoteDetail>

@FormUrlEncoded
    @POST("updateStatus.php")
    fun updateStatus(
        @Field("notesId") notesId: Int?
    ):Call<Response>

    @GET("read_finished.php")
    fun getFinishedNotes(): Call<ArrayList<Notes>>

    // Edit Notes
    @FormUrlEncoded
    @POST("edit.php")
    fun editNoteDetail(
        @Field("title") title: String?,
        @Field("content") content: String?,
        @Field("date") date: String?,
    ): Call<Response>

    // Add new note
    @FormUrlEncoded
    @POST("add.php")
    fun addNoteDetail(
        @Field("title") title: String?,
        @Field("content") content: String?,
        @Field("date") date: String?,
    ): Call<Response>


}