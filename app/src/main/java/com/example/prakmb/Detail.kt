package com.example.prakmb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.prakmb.data.NoteDetail
import com.example.prakmb.databinding.ActivityDetailBinding
import com.example.prakmb.databinding.ActivityMainBinding
import com.example.prakmb.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Detail : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    lateinit var list : NoteDetail
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = this.intent
        val noteIdIntent = intent.getStringExtra("note_id")!!

        // get note detail
        retrieveNoteDetail(noteIdIntent)
    }

    private fun retrieveNoteDetail(noteId: String) {
        RetrofitClient.instance.getNoteDetail(noteId)
            .enqueue(object: Callback<NoteDetail> {

                override fun onResponse(call: Call<NoteDetail>, response: Response<NoteDetail>) {
                    if (response.code() == 200) {
                        list = response.body()!!
                        Log.d("GET NOTE DETAIL", list.toString())

                        binding.detailTitle.text = list.title
                        binding.detailContent.text = list.content
                        binding.detailDate.text = list.date.toString()
                        binding.detailStatus.text = if(list.status!!) "Finished" else "Ongoing"
                    } else {
                        Toast.makeText(this@Detail, "Fail fetching from database response is not 200", Toast.LENGTH_LONG).show()
                        Log.d("GET NOTE ITEMS FAIL ${response.code()}", response.body().toString())
                    }
                }
                override fun onFailure(call: Call<NoteDetail>, t: Throwable) {
                    Toast.makeText(this@Detail, "Fail fetching from database onFailure", Toast.LENGTH_LONG).show()
                    Log.d("GET NOTE ITEMS FAIL", t.toString())
                }
            })
    }
}