package com.example.prakmb

import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.prakmb.data.Notes

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale


class ListAdapter(context: Context, dataArrayList:List<Notes?>?)
    :ArrayAdapter<Notes>(context,R.layout.list_notes,dataArrayList!!) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val listData = getItem(position)

        if(view == null){

            view = LayoutInflater
                .from(context)
                .inflate(R.layout.list_notes,parent,false)

        }


        val dataformat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = LocalDate.parse(listData!!.date)

        val listday = view!!.findViewById<TextView>(R.id.day)
        val listMonth = view.findViewById<TextView>(R.id.month)
        val listYear = view.findViewById<TextView>(R.id.year)
        val listTitle =view.findViewById<TextView>(R.id.title)

        listday.text=date.dayOfMonth.toString()
        listMonth.text =date.month.toString().substring(0,3)
        listYear.text=(date.year).toString()
        listTitle.text=listData!!.title

        return view



        
    }

}