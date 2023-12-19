package com.example.prakmb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prakmb.data.Notes
import com.example.prakmb.data.dummData
import com.example.prakmb.databinding.ActivityFinishedNotesBinding
import com.example.prakmb.databinding.ActivityMainBinding
import java.util.Arrays

class FinishedNotesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFinishedNotesBinding
    private lateinit var listAdapter: ListAdapter


    fun buildRecycleView(){
        var notes = dummData().filter { note -> note.status == true }
        notes= ArrayList<Notes>(notes)
        //initialize adapter

        val notesAdapter= NotesListAdapter(notes){
                note->notesItemClicked(note)
        }

        binding.listview.adapter = notesAdapter
        binding.listview.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
    }
    private fun notesItemClicked(note: Notes) {
        startActivity(
            Intent(this@FinishedNotesActivity, Detail::class.java)
                .putExtra("notes", note)
        )
    }


    //Bot Navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishedNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buildRecycleView()
        //Set Create Nav
        binding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.add ->{
                    val intent =Intent(this,AddNotesActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
        //Bot Navigation
        binding.botNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home->{
                    val intent =Intent(this,MainActivity::class.java)
                    intent.clearStack()
                    startActivity(intent)
                }
                R.id.finished->{
                    val intent =Intent(this,FinishedNotesActivity::class.java)
                    intent.clearStack()
                    startActivity(intent)
                }
            }
            true
        }




    }
    fun Intent.clearStack() {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
}