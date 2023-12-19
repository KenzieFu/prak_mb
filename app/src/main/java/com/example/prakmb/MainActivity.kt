package com.example.prakmb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.prakmb.data.Notes
import com.example.prakmb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var notesList: ArrayList<Notes>

    var dataArrayList : List<Notes?>? =null
    val listNotes= listOf<Notes>(
        Notes(
            id = 1,
            title = "Go Shopping",
            date = "2023-12-01",
            content = "Go buy some milk and beef for dinner",
            status = false
        ),
        Notes(
            id = 2,
            title = "Study",
            date = "2023-10-01",
            content = "Go learn maths, and physics",
            status = false
        ),
        Notes(
            id = 3,
            title = "Movie",
            date = "2023-10-13",
            content = "Watch horror movie",
            status = true
        )
    )
    private fun catItemClicked(note: Notes) {
        startActivity(
            Intent(this@MainActivity, Detail::class.java)
                .putExtra("notes", note)
        )
    }
    fun buildRecycleView(){
        val notes = listNotes

        //initialize adapter

        val notesAdapter= NotesListAdapter(notes){
            note->
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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


        // List View Adapter and add onclick listener for each item of the list



//        dataArrayList=listNotes
//        val recycleAdapter = RecyclerView.Adapter(this,dataArrayList)
//        listAdapter = ListAdapter(this@MainActivity,dataArrayList)
//        binding.listview.adapter = listAdapter
//
//        binding.listview.isClickable = true
//
//        binding.listview.onItemClickListener= AdapterView.OnItemClickListener{
//            adapterView,view,i,l ->
//
//            val intent = Intent(this@MainActivity,Detail::class.java)
//            intent.putExtra("title", (dataArrayList as List<Notes>)[i].title)
//            intent.putExtra("status", (dataArrayList as List<Notes>)[i].status)
//            intent.putExtra("date", (dataArrayList as List<Notes>)[i].date)
//            intent.putExtra("content", (dataArrayList as List<Notes>)[i].content)
//            intent.putExtra("id", (dataArrayList as List<Notes>)[i].id)
//            startActivity(intent)
//        }
    }

    fun Intent.clearStack() {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }


}