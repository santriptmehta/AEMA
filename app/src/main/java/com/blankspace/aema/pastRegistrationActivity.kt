package com.blankspace.aema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.aema.adapters.pastActivity_adapter
import com.blankspace.aema.fragment.view_allComplaint

class pastRegistrationActivity : AppCompatActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_past_registration)


//        recyclerView = findViewById(R.id.pastRegisterd_RecyclerView)
//        setUpRecyclerView()
         supportFragmentManager.beginTransaction()
             .replace(R.id.view_complaint_fragment, view_allComplaint())
             .commit()
    }

//    private fun setUpRecyclerView() {
//
//        val currEmail = FirebaseAuth.getInstance()
//        val firestore = FirebaseFirestore.getInstance()
//
//        val query = firestore.collection("UserData").document(currEmail.currentUser?.email!!).collection("RegisteredComplaint")
//        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<UserIssue>().setQuery(query,UserIssue::class.java).build()
//
//        adapter = pastActivity_adapter(recyclerViewOptions, this)
//        if(this::adapter.isInitialized){
//            recyclerView.adapter = adapter
//        }
//        recyclerView.layoutManager = LinearLayoutManager(this)
//    }

}

