package com.example.prakmb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prakmb.data.Notes
import com.example.prakmb.data.dummData
import com.example.prakmb.databinding.ActivityMainBinding
import com.example.prakmb.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var notesList: ArrayList<Notes>



    private fun notesItemClicked(note: Notes) {
        startActivity(
            Intent(this@MainActivity, Detail::class.java)
                .putExtra("notes", note)
        )
    }
    fun buildRecycleView(){
        val notes = dummData()

        //initialize adapter

        val notesAdapter= NotesListAdapter(notes){
            note->notesItemClicked(note)
        }

        binding.listview.adapter = notesAdapter
        binding.listview.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //build recycle view
        buildRecycleView()
        // Retrieve Notes From API
        retrieveNotes()

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

    private fun retrieveNotes() {
        RetrofitClient.instance.getNotes()
            .enqueue(object: Callback<ArrayList<Notes>> {
                override fun onResponse(call: Call<ArrayList<Notes>>, response: Response<ArrayList<Notes>>) {
                    if (response.code() == 200) {
                        val list = response.body()
                        Log.d("GET NOTES ITEMS", list.toString())
                    } else {
                        Toast.makeText(this@MainActivity, "Fail fetching from database response is not 200", Toast.LENGTH_LONG).show()
                        Log.d("GET NOTES ITEMS FAIL ${response.code()}", response.body().toString())
                    }
                }
                override fun onFailure(call: Call<ArrayList<Notes>>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Fail fetching from database onFailure", Toast.LENGTH_LONG).show()
                    Log.d("GET NOTES ITEMS FAIL", t.toString())
                }
            })
    }



    fun Intent.clearStack() {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }


}