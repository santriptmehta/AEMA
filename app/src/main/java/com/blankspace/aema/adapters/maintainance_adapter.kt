package com.blankspace.aema.adapters

import com.blankspace.aema.Models.Maintainance
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.aema.R

class maintainance_adapter(private val userList:ArrayList<Maintainance>): RecyclerView.Adapter<maintainance_adapter.MyViewHolder>(){

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author_name : TextView = itemView.findViewById(R.id.post_author_name)
        val author_defect : TextView = itemView.findViewById(R.id.defect_post_text)
        val author_hoste_no : TextView = itemView.findViewById(R.id.author_hostel_no)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view_recycler,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.author_name.text = "by ${userList[position].name}"
        holder.author_defect.text = userList[position].defect
        holder.author_hoste_no.text = "Hostel no. ${userList[position].hostel_no}"

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}