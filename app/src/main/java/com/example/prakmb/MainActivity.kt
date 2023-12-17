package com.example.prakmb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import com.example.prakmb.data.Notes
import com.example.prakmb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var listAdapter: ListAdapter

    var dataArrayList : List<Notes?>? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            status = false
        )
        )

        dataArrayList=listNotes
        listAdapter = ListAdapter(this@MainActivity,dataArrayList)
        binding.listview.adapter = listAdapter
        binding.listview.isClickable = true

        binding.listview.onItemClickListener= AdapterView.OnItemClickListener{
            adapterView,view,i,l ->

            val intent = Intent(this@MainActivity,Detail::class.java)
            intent.putExtra("title", (dataArrayList as List<Notes>)[i].title)
            intent.putExtra("status", (dataArrayList as List<Notes>)[i].status)
            intent.putExtra("date", (dataArrayList as List<Notes>)[i].date)
            intent.putExtra("content", (dataArrayList as List<Notes>)[i].content)
            intent.putExtra("id", (dataArrayList as List<Notes>)[i].id)
            startActivity(intent)
        }
    }
}