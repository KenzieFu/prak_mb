package com.example.prakmb

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.prakmb.data.NoteDetail
import com.example.prakmb.data.Notes
import com.example.prakmb.databinding.ActivityDetailBinding
import com.example.prakmb.databinding.ActivityMainBinding
import com.example.prakmb.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Detail : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = this.intent
            val id:String = intent.getStringExtra("note_id").toString()
          retrieveNotesDetail(id.toInt())


    }
    private fun updateStatus(notesId: String) {
            RetrofitClient.instance.updateStatus(
             notesId.toInt()
            )
                .enqueue(object : Callback<com.example.prakmb.data.Response> {
                    override fun onResponse(
                        call: Call<com.example.prakmb.data.Response>,
                        response: Response<com.example.prakmb.data.Response>
                    ) {

                        if (response.code() == 200) {
                            val resp = response.body()

                            if (resp!!.error) Toast.makeText(
                                this@Detail,
                                resp.message + ", please try again later",
                                Toast.LENGTH_LONG
                            ).show()
                            else {
                                Toast.makeText(this@Detail, resp.message, Toast.LENGTH_SHORT)
                                    .show()

                                startActivity(Intent(this@Detail, MainActivity::class.java))

                                this@Detail.finish()
                            }
                        } else {
                            Toast.makeText(
                                this@Detail,
                                "Something wrong on server",
                                Toast.LENGTH_LONG
                            ).show()
                            Log.d("EDIT Notes (${response.code()})", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<com.example.prakmb.data.Response>, t: Throwable) {
                        Toast.makeText(
                            this@Detail,
                            "Something wrong on server...",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("EDIT Notes FAIL", t.toString())
                    }
                })

    }
    private fun deleteNotes(notesId: String) {
        RetrofitClient.instance.deleteNotesDetail(notesId)
            .enqueue(object: Callback<com.example.prakmb.data.Response> {
                override fun onResponse(
                    call: Call<com.example.prakmb.data.Response>,
                    response: Response<com.example.prakmb.data.Response>
                ) {
                    if (response.code() == 200) {
                        val resp = response.body()

                        if (resp!!.error) Toast.makeText(this@Detail, resp.message + ", please try again later", Toast.LENGTH_LONG).show()

                        else {
                            Toast.makeText(this@Detail, resp.message, Toast.LENGTH_SHORT).show()

                            startActivity(Intent(this@Detail, MainActivity::class.java))

                            this@Detail.finish()
                        }
                    } else {
                        Toast.makeText(this@Detail, "Something wrong on server", Toast.LENGTH_LONG).show()
                        Log.d("DELETE NOtes (${response.code()})", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<com.example.prakmb.data.Response>, t: Throwable) {
                    Toast.makeText(this@Detail, "Something wrong on server...", Toast.LENGTH_LONG).show()
                    Log.d("DELETE Notes FAIL", t.toString())
                }
            })
    }

    private fun retrieveNotesDetail(notesId: Int) {
        RetrofitClient.instance.getDetails(notesId)
            .enqueue(object: Callback<NoteDetail> {
                override fun onResponse(call: Call<NoteDetail>, response: Response<NoteDetail>) {
                    if (response.code() == 200) {
                        val list = response.body()!!
                        Log.d("GET Notes DETAIL", list.toString())

                        binding.detailTitle.text = list.title
                        binding.detailContent.text = list.content
                        binding.detailStatus.text = if (list.status.toString() === "1") "Finished" else "Ongoing"
                        binding.bfinish.setOnClickListener{
                            updateStatus(notesId.toString())

                        }
                        binding.bdel.setOnClickListener{
                            deleteNotes(notesId.toString())
                        }

                        if(list.status.toString()== "1"){
                            binding.bfinish.visibility=View.GONE

                        }


                    } else {
                        Toast.makeText(this@Detail, "Fail fetching from database response is not 200", Toast.LENGTH_LONG).show()
                        Log.d("GET Notes ITEMS FAIL ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<NoteDetail>, t: Throwable) {
                    Toast.makeText(this@Detail, "Fail fetching from database onFailure", Toast.LENGTH_LONG).show()
                    Log.d("GET Notes ITEMS FAIL", t.toString())
                }
            })
    }


}