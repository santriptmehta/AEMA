package com.blankspace.aema.adapters

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.aema.Models.UserIssue
import com.blankspace.aema.R
import com.blankspace.aema.fragment.detailComplaintView
import com.blankspace.aema.fragment.view_allComplaint
import com.blankspace.aema.pastRegistrationActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text

class pastActivity_adapter(options: FirestoreRecyclerOptions<UserIssue>, val context: Context): FirestoreRecyclerAdapter<UserIssue,
        pastActivity_adapter.MyViewHolder>(options) {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val author_name : TextView = itemView.findViewById(R.id.post_author_name)
        val author_defect : TextView = itemView.findViewById(R.id.defect_post_text)
        val author_hostel : TextView = itemView.findViewById(R.id.author_hostel_no)
        val dateTime : TextView = itemView.findViewById(R.id.post_time_date)
        val processStatus : ImageView = itemView.findViewById(R.id.process_status_icon)
        val typeIssue : TextView = itemView.findViewById(R.id.typeIssue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view_recycler,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: UserIssue) {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userRef = db.collection("Users")
        if (userId != null) {
            userRef.document(userId).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document.exists()) {
                        holder.author_name.text = "By ${document.getString("name")}"
                        Log.d(ContentValues.TAG, "${document.getString("name")}")
                    } else {
                        Log.d(ContentValues.TAG, "This does not exit")
                    }
                } else {
                    task.exception?.message?.let {
                        Log.d(ContentValues.TAG, it)
                    }
                }
            }
        }
        holder.author_defect.text = model.issueDefect
        holder.author_hostel.text = "Hostel no. ${model.userHostelno}"
        holder.dateTime.text = model.issueDataTime
        holder.typeIssue.text = "IssueType - ${model.issueType}"
        if (model.admin_resolved_checkbox) {
            holder.processStatus.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.greentick_icon
                )
            )
        }
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("defectTitle",model.issueDefect)
            bundle.putString("Hostelno",model.userHostelno)
            bundle.putString("Rollno",model.userRollno)
            bundle.putString("Location",model.issueLocation)
            bundle.putString("Description",model.issueDescribe)
            bundle.putString("dateTime",model.issueDataTime)
            bundle.putString("typeIssue",model.issueType)
            bundle.putString("admin_review_feedBack",model.admin_review_data)
            bundle.putBoolean("admin_resolved_checkBox",model.admin_resolved_checkbox)

            val transaction = (context as pastRegistrationActivity).supportFragmentManager.beginTransaction()
            val fragmentwo = detailComplaintView()
            fragmentwo.arguments = bundle
            transaction.replace(R.id.view_complaint_fragment,fragmentwo)
            transaction.addToBackStack(null)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }

    }
}

