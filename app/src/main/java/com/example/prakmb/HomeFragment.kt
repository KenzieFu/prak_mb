package com.example.prakmb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.prakmb.data.Notes
import com.example.prakmb.databinding.ActivityMainBinding
import com.example.prakmb.databinding.FragmentHomeBinding
import com.example.prakmb.rest.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding : FragmentHomeBinding
    lateinit var recycleView:RecyclerView
    var act  = activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("On create view", "on create view")

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        buildRecycleView(arrayListOf())
        retrieveNotes()

        Log.d("GET NOTES ITEMS", "${act.toString()}")
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrieveNotes()
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun retrieveNotes() {
        RetrofitClient.instance.getNotes()
            .enqueue(object: Callback<ArrayList<Notes>> {
                override fun onResponse(call: Call<ArrayList<Notes>>, response: Response<ArrayList<Notes>>) {
                    if (response.code() == 200) {
                        val list = response.body()
                        Log.d("GET NOTES ITEMS", list.toString())

                        if (list!!.isEmpty()) {
                            Toast.makeText(act, "There is no country data to display", Toast.LENGTH_LONG).show()
                        } else {
                            //build recycle view
                            buildRecycleView(list)
                        }
                    } else {
                        Toast.makeText(act, "Fail fetching from database response is not 200", Toast.LENGTH_LONG).show()
                        Log.d("GET NOTES ITEMS FAIL ${response.code()}", response.body().toString())
                    }

                }
                override fun onFailure(call: Call<ArrayList<Notes>>, t: Throwable) {
                    Toast.makeText(act, "Fail fetching from database onFailure", Toast.LENGTH_LONG).show()
                    Log.d("GET NOTES ITEMS FAIL", t.toString())
                }
            })
    }

    private fun notesItemClicked(note: Notes) {
        // transfers note id to DetailActivity
        val noteId = note.id.toString()

        startActivity(
            Intent(act, Detail::class.java)
                .putExtra("note_id", noteId)
        )
    }
    fun buildRecycleView(notes: ArrayList<Notes> ){
        //initialize adapter
        Log.d("BROK", notes.toString())
        val notesAdapter= NotesListAdapter(notes){
                note->notesItemClicked(note)
        }
        notesAdapter.notifyDataSetChanged()
        binding.listview.apply {
            binding.listview.adapter= notesAdapter
            binding.listview.layoutManager = LinearLayoutManager(act)
            binding.listview.visibility=View.VISIBLE
        }



    }
}