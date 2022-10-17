package com.blankspace.aema.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.aema.Models.Plumbing
import com.blankspace.aema.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlin.contracts.contract


class plumbing_adapter(options : FirestoreRecyclerOptions<Plumbing>, val context: Context): FirestoreRecyclerAdapter<Plumbing, plumbing_adapter.PlumMyViewHolder>(options){

    class PlumMyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author_name : TextView = itemView.findViewById(R.id.post_author_name)
        val author_defect : TextView = itemView.findViewById(R.id.defect_post_text)
        val author_hoste_no : TextView = itemView.findViewById(R.id.author_hostel_no)
        val dateTime : TextView = itemView.findViewById(R.id.post_time_date)
        val processStatus : ImageView = itemView.findViewById(R.id.process_status_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlumMyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view_recycler,parent,false)
        return PlumMyViewHolder(itemView)
    }

//    override fun onBindViewHolder(holder: PlumMyViewHolder, position: Int) {
//        holder.author_name.text = "by ${userList[position].name}"
//        holder.author_defect.text = userList[position].defect
//        holder.author_hoste_no.text = "Hostel no. ${userList[position].hostel_no}"
//        holder.dateTime.text = userList[position].dateTime
//
//        if(userList[position].admin_resolved_checkbox){
//            holder.processStatus.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.greentick_icon))
//        }
//
//    }

    override fun onBindViewHolder(holder: PlumMyViewHolder, position: Int, model: Plumbing) {
        holder.author_name.text = "by ${model.name}"
        holder.author_defect.text = model.defect
        holder.author_hoste_no.text = "Hostel no. ${model.hostel_no}"
        holder.dateTime.text = model.dateTime

        if(model.admin_resolved_checkbox == true){
            holder.processStatus.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.greentick_icon))
        }
    }
}