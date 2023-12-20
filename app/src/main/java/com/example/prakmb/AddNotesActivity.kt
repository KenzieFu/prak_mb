package com.example.prakmb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.get
import com.example.prakmb.data.Response
import com.example.prakmb.databinding.ActivityAddNotesBinding
import com.example.prakmb.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.Date

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addTime.setIs24HourView(true)

        binding.cancelButton.setOnClickListener{
            finish()
        }

        binding.submitButton.setOnClickListener {
            addNoteDate()
        }
    }

    private fun addNoteDate() {
        // Retrieve All Inputs
        val inputTitle = binding.addTitle.text.toString().trim()
        val inputContent = binding.addContent.text.toString().trim()
        val inputHour = binding.addTime.hour.toString()
        val inputMinute = binding.addTime.minute.toString()
        val inputDay = binding.addDate.dayOfMonth.toString()
        val inputMonth = binding.addDate.month.toString()
        val inputYear = binding.addDate.year.toString()

        // Validate the inputs
        if (inputTitle.isEmpty()) {
            binding.addTitle.error = "Field is empty"
        }
        if (inputContent.isEmpty()) {
            binding.addContent.error = "Field is empty"
        }

        // parse date to YYYY:MM:DD hh:mm:ss Format
        val dateBuilder = StringBuilder()
        dateBuilder.append(inputYear).append(":").append(inputMonth).append(":").append(inputDay)
            .append(" ").append(inputHour).append(":").append(inputMinute).append(":").append("00")

        val inputDate = dateBuilder.toString()

        // Store new note
        if (inputTitle.isNotEmpty() && inputContent.isNotEmpty() && inputDate.isNotEmpty()) {
            RetrofitClient.instance.addNoteDetail(inputTitle, inputContent, inputDate)
                .enqueue(object: Callback<Response> {
                    override fun onResponse(
                        call: Call<Response>,
                        response: retrofit2.Response<Response>
                    ) {
                        if (response.code() == 200) {
                            val resp = response.body()

                            if (resp!!.error) Toast.makeText(this@AddNotesActivity, resp.message + ", please try again later", Toast.LENGTH_LONG).show()

                            else {
                                Toast.makeText(this@AddNotesActivity, resp.message, Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@AddNotesActivity, MainActivity::class.java))
                                this@AddNotesActivity.finish()
                            }
                        } else {
                            Toast.makeText(this@AddNotesActivity, "Something wrong on server", Toast.LENGTH_LONG).show()
//                            Log.d("ADD NOTE (${response.code()})", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Toast.makeText(this@AddNotesActivity, "Something wrong on server...", Toast.LENGTH_LONG).show()
//                        Log.d("ADD NOTE FAIL", t.toString())
                    }
                })
        } else {
            Toast.makeText(this@AddNotesActivity, "Fail adding new Note, field(s) is empty!", Toast.LENGTH_LONG).show()
        }



        // Debugging
//        Log.d("Title", inputTitle)
//        Log.d("Content", inputContent)
//        Log.d("Hour", inputHour)
//        Log.d("Minute", inputMinute)
//        Log.d("Day", inputDay)
//        Log.d("Month", inputMonth)
//        Log.d("Year", inputYear)
//        Log.d("InputDate", inputDate)
    }
}