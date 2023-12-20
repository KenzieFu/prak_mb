package com.example.prakmb

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.prakmb.data.NoteDetail
import com.example.prakmb.data.Response
import com.example.prakmb.databinding.ActivityDetailBinding
import com.example.prakmb.databinding.ActivityEditNotesBinding
import com.example.prakmb.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class EditNotesActivity : AppCompatActivity() {
    private lateinit var binding :ActivityEditNotesBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEditNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currentData: NoteDetail? =intent.getParcelableExtra("currentNotes")
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val date = LocalDate.parse(currentData!!.date, dateFormatter)


        val year= date.year
        val month=date.monthValue
        val day =date.dayOfMonth
        binding.edtDate.updateDate(year,month,day)
        binding.edtTime.hour =(currentData.date!!.substring(11,13)).toInt()
        binding.edtTime.minute =(currentData.date!!.substring(14,16)).toInt()
        binding.edtContent.setText(currentData.content)
        binding.edtTitle.setText(currentData.title)

        binding.cancelButton.setOnClickListener{
            this@EditNotesActivity.finish()
        }
        binding.submitButton.setOnClickListener{
            updateCountryDetail(currentData.id!!.toInt())
        }
    }

    private fun updateCountryDetail(notesId: Int) {


        val inputTitle = binding.edtTitle.text.toString().trim()
        val inputContent = binding.edtContent.text.toString().trim()
        val inputHour = binding.edtTime.hour.toString()
        val inputMinute = binding.edtTime.minute.toString()
        val inputDay = binding.edtDate.dayOfMonth.toString()
        val inputMonth = binding.edtDate.month.toString()
        val inputYear = binding.edtDate.year.toString()

        //validation data
        if (inputTitle.isEmpty()) {
            binding.edtTitle.error = "Field is empty"
        }
        if (inputContent.isEmpty()) {
            binding.edtContent.error = "Field is empty"
        }
        // parse date to YYYY:MM:DD hh:mm:ss Format
        val dateBuilder = StringBuilder()
        dateBuilder.append(inputYear).append(":").append(inputMonth).append(":").append(inputDay)
            .append(" ").append(inputHour).append(":").append(inputMinute).append(":").append("00")

        val inputDate = dateBuilder.toString()

        if (inputTitle.isNotEmpty() && inputContent.isNotEmpty() && inputDate.isNotEmpty()) {
            val updatedId = notesId
            RetrofitClient.instance.editNoteDetail(updatedId, inputTitle,inputContent,inputDate)
                .enqueue(object: Callback<Response> {
                    override fun onResponse(
                        call: Call<Response>,
                        response: retrofit2.Response<Response>
                    ) {
                        if (response.code() == 200) {
                            val resp = response.body()

                            if (resp!!.error) Toast.makeText(this@EditNotesActivity, resp.message + ", please try again later", Toast.LENGTH_LONG).show()

                            else {
                                Toast.makeText(this@EditNotesActivity, resp.message, Toast.LENGTH_SHORT).show()

                                startActivity(Intent(this@EditNotesActivity, MainActivity::class.java))

                                this@EditNotesActivity.finish()
                            }
                        } else {
                            Toast.makeText(this@EditNotesActivity, "Something wrong on server", Toast.LENGTH_LONG).show()
                            Log.d("EDIT Notes (${response.code()})", response.body().toString())
                        }
                    }

                    override fun onFailure(call: Call<Response>, t: Throwable) {
                        Toast.makeText(this@EditNotesActivity, "Something wrong on server...", Toast.LENGTH_LONG).show()
                        Log.d("EDIT Notes FAIL", t.toString())
                    }
                })
        } else {
            Toast.makeText(this@EditNotesActivity, "Fail editing notes data, field(s) is empty!", Toast.LENGTH_LONG).show()
        }
    }
}