package com.example.prakmb

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.prakmb.data.Notes
import com.example.prakmb.databinding.ListNotesBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class NotesListAdapter(
    private val notes: ArrayList<Notes>,
    val itemClickListener: (Notes) -> Unit,
): RecyclerView.Adapter<NotesListAdapter.NotesViewHolder>() {

    // create an inner class with name NotesViewHolder
    // It takes a view argument, in which pass the generated class of cat_item.xml
    // ie ListNotesBinding and in the RecyclerView.ViewHolder(binding.root) pass it like this
    inner class NotesViewHolder(val binding: ListNotesBinding):
        RecyclerView.ViewHolder(binding.root) {
        // bind the items with each item of the list notes ArrayList<Notes>
        // which than will be shown in recycler view
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(note: Notes) = with(binding) {
            // Create date object from date
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(note!!.date, dateFormatter)

            // Get Hour and Minute from date
            val hourMinute = note.date?.substring(11,16)

            // Apply to layout
            day.text = date.dayOfMonth.toString()
            month.text = date.month.toString().substring(0,3)
            year.text = date.year.toString()
            title.text = note.title
            hour.text = hourMinute
            root.setOnClickListener { itemClickListener(note) }
        }
    }

    // inside the onCreateViewHolder inflate the view of NotesItemBinding
    // and return new ViewHolder object containing this layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = ListNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NotesViewHolder(binding)
    }



    // in OnBindViewHolder this is where we get the current item
    // and bind it to the layout
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val cat = notes[position]
        holder.bind(cat)
    }

    // return the size of notes ArrayList<Notes>
    override fun getItemCount() = notes.size
}