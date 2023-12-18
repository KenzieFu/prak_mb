package com.example.prakmb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date

class AddNotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        val submitButton: Button = findViewById(R.id.submitButton)
        val datePicker: DatePicker = findViewById(R.id.addDate)

        val backButton :Button = findViewById(R.id.cancel_button)

        backButton.setOnClickListener{
            finish()
        }

        submitButton.setOnClickListener {

            // Retrieve input values
            val title = findViewById<EditText>(R.id.addTitle).text.toString()
            val content = findViewById<EditText>(R.id.addContent).text.toString()

            // Retrieve selected date from DatePicker
            val day = datePicker.dayOfMonth
            val month = datePicker.month
            val year = datePicker.year

            // Format the date
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val formattedDate = dateFormat.format(Date(year, month, day))




            val message = "Notes Created"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}