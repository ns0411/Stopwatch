package com.example.stopwatch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

class LapTimeAdapater(val context:Context,val lapTimeList:List<LapTimeModel> ): RecyclerView.Adapter<LapTimeAdapater.ViewHolderTime>()
{
    class ViewHolderTime(view: View): RecyclerView.ViewHolder(view) {
        val time: MaterialTextView=view.findViewById(R.id.Laptime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTime {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.lap_single_row,parent,false)
        return ViewHolderTime(view)
    }

    override fun getItemCount(): Int {
        return lapTimeList.size
    }


    override fun onBindViewHolder(holder: ViewHolderTime, position: Int) {
        val lapTime=lapTimeList[position]
        holder.time.text =lapTime.time
    }
}