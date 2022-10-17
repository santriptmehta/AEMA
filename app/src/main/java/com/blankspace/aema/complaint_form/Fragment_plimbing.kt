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
import com.blankspace.aema.Models.Plumbing
import com.blankspace.aema.R
import com.blankspace.aema.adapters.electrical_adapter
import com.blankspace.aema.adapters.maintainance_adapter
import com.blankspace.aema.adapters.plumbing_adapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore


class Fragment_plimbing : Fragment() {
    lateinit var recyclerView: RecyclerView
//    lateinit var userList: ArrayList<Plumbing>
//    private lateinit var db: FirebaseFirestore
    private lateinit var adapter : plumbing_adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plimbing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val layoutManager = LinearLayoutManager(context)
//        recyclerView = view.findViewById(R.id.recycler_view_plumbing)
//        recyclerView.layoutManager = layoutManager
//        userList = arrayListOf()
//        db = FirebaseFirestore.getInstance()
//        db.collection("Plumbing").get()
//            .addOnSuccessListener {
//                if(!it.isEmpty){
//                    for(data in it.documents){
//                        val userElc :  Plumbing? = data.toObject(Plumbing::class.java)
//                        if(userElc!=null){
//                            userList.add(userElc)
//                        }
//                    }
//                    recyclerView.adapter = plumbing_adapter(userList)
//                }
//            }
//            .addOnFailureListener{
//                Toast.makeText(activity,it.toString(), Toast.LENGTH_LONG).show()
//            }
        recyclerView = view.findViewById(R.id.recycler_view_plumbing)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView(){
        val firestore = FirebaseFirestore.getInstance()
        val query = firestore.collection("Plumbing")
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Plumbing>().setQuery(query,Plumbing::class.java).build()

        context?.let {
            adapter = plumbing_adapter(recyclerViewOptions,it)
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