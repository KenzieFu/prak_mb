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
import com.example.prakmb.data.Notes
import com.example.prakmb.databinding.ActivityMainBinding
import com.example.prakmb.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var listAdapter: ListAdapter

    var dataArrayList : List<Notes?>? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        // List View Adapter and add onclick listener for each item of the list
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