package com.blankspace.aema.complaint_form

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.aema.Models.Electrical
import com.blankspace.aema.Models.Maintainance
import com.blankspace.aema.R
import com.blankspace.aema.adapters.electrical_adapter
import com.blankspace.aema.adapters.maintainance_adapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

class Fragment_electrical : Fragment() {
    lateinit var recyclerView: RecyclerView
//    lateinit var userList: ArrayList<Electrical>
//    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: electrical_adapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_electrical, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val layoutManager = LinearLayoutManager(context)
//        recyclerView = view.findViewById(R.id.electrical_recycler_view)
//        recyclerView.layoutManager = layoutManager
//        userList = arrayListOf()
//        db = FirebaseFirestore.getInstance()
//        db.collection("Electrical").get()
//            .addOnSuccessListener {
//                if(!it.isEmpty){
//                    for(data in it.documents){
//                        val userElc :  Electrical? = data.toObject(Electrical::class.java)
//                        if(userElc!=null){
//                            userList.add(userElc)
//                        }
//                    }
//                    recyclerView.adapter = electrical_adapter(userList)
//                }
//            }
//            .addOnFailureListener{
//                Toast.makeText(activity,it.toString(), Toast.LENGTH_LONG).show()
//            }
        recyclerView = view.findViewById(R.id.electrical_recycler_view)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val firestore  =  FirebaseFirestore.getInstance()
        val query = firestore.collection("Electrical")
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Electrical>().setQuery(query,Electrical::class.java).build()

        context?.let {
            adapter = electrical_adapter(recyclerViewOptions,it)
        }
        if (this::adapter.isInitialized){
            recyclerView.adapter = adapter
        }
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}