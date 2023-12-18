package com.example.prakmb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.prakmb.databinding.ActivityDetailBinding
import com.example.prakmb.databinding.ActivityMainBinding

class Detail : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = this.intent
        if(intent!=null){
            val id = intent.getStringExtra("id")
            val title = intent.getStringExtra("title")
            val content = intent.getStringExtra("content")
            val date = intent.getStringExtra("date")
            val status = intent.getStringExtra("status")
            binding.detailTitle.text = title
            binding.detailContent.text = content
            binding.detailStatus.text = if (status.toString()== "1") "Finished" else "Ongoing"
        }

    }
}