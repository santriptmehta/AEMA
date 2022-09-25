package com.blankspace.aema.complaint_form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.aema.Models.Electrical
import com.blankspace.aema.Models.Maintainance
import com.blankspace.aema.R
import com.blankspace.aema.adapters.electrical_adapter
import com.blankspace.aema.adapters.maintainance_adapter
import com.google.firebase.firestore.FirebaseFirestore

class Fragment_electrical : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var userList: ArrayList<Electrical>
    private lateinit var myAdapter: maintainance_adapter
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_electrical, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.electrical_recycler_view)
        recyclerView.layoutManager = layoutManager
        userList = arrayListOf()
        db = FirebaseFirestore.getInstance()
        db.collection("Electrical").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val userElc :  Electrical? = data.toObject(Electrical::class.java)
                        if(userElc!=null){
                            userList.add(userElc)
                        }
                    }
                    recyclerView.adapter = electrical_adapter(userList)
                }
            }
            .addOnFailureListener{
                Toast.makeText(activity,it.toString(), Toast.LENGTH_LONG).show()
            }
    }

}