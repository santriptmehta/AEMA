package com.blankspace.aema.complaint_form

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.aema.Models.Maintainance
import com.blankspace.aema.R
import com.blankspace.aema.adapters.maintainance_adapter
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase

class Fragment_maintainance : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var userList: ArrayList<Maintainance>
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maintainance, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.maintainance_recyclerView)
        recyclerView.layoutManager = layoutManager
        userList = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db.collection("Maintainance").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val user :  Maintainance? = data.toObject(Maintainance::class.java)
                        if(user!=null){
                            userList.add(user)
                        }
                    }
                    recyclerView.adapter = maintainance_adapter(userList)
                }
            }
            .addOnFailureListener{
                Toast.makeText(activity,it.toString(),Toast.LENGTH_LONG).show()
            }
    }

}
