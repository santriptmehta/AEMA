package com.blankspace.aema.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.aema.Models.Plumbing
import com.blankspace.aema.R


class plumbing_adapter(private val userList:ArrayList<Plumbing>): RecyclerView.Adapter<plumbing_adapter.PlumMyViewHolder>(){

    class PlumMyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author_name : TextView = itemView.findViewById(R.id.post_author_name)
        val author_defect : TextView = itemView.findViewById(R.id.defect_post_text)
        val author_hoste_no : TextView = itemView.findViewById(R.id.author_hostel_no)
        val dateTime : TextView = itemView.findViewById(R.id.post_time_date)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlumMyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view_recycler,parent,false)
        return PlumMyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PlumMyViewHolder, position: Int) {
        holder.author_name.text = "by ${userList[position].name}"
        holder.author_defect.text = userList[position].defect
        holder.author_hoste_no.text = "Hostel no. ${userList[position].hostel_no}"
        holder.dateTime.text = userList[position].dateTime
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}