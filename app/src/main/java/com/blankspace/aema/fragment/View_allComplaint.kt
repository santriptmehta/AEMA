package com.blankspace.aema.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankspace.aema.Models.UserIssue
import com.blankspace.aema.R
import com.blankspace.aema.adapters.pastActivity_adapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class view_allComplaint : Fragment() {
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: pastActivity_adapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_all_complaint, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.complaintView_recyclerView)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val firestore  =  FirebaseFirestore.getInstance()
        val currEmail = FirebaseAuth.getInstance()
        val query = firestore.collection("UserData").document(currEmail.currentUser?.email!!).collection("RegisteredComplaint")
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<UserIssue>().setQuery(query,
            UserIssue::class.java).build()

        context?.let {
            adapter = pastActivity_adapter(recyclerViewOptions,it)
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



//class adapterClass(options: FirestoreRecyclerOptions<UserIssue>, val context: Context,
//): FirestoreRecyclerAdapter<UserIssue, adapterClass.adapterMyViewHolder>(options){
//
//    class adapterMyViewHolder(itemview : View):RecyclerView.ViewHolder(itemview){
//        val author_name : TextView = itemView.findViewById(R.id.post_author_name)
//        val author_defect : TextView = itemView.findViewById(R.id.defect_post_text)
//        val author_hostel : TextView = itemView.findViewById(R.id.author_hostel_no)
//        val dateTime : TextView = itemView.findViewById(R.id.post_time_date)
//        val processStatus : ImageView = itemView.findViewById(R.id.process_status_icon)
//        val typeIssue : TextView = itemView.findViewById(R.id.typeIssue)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterMyViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_view_recycler,parent,false)
//        return adapterMyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: adapterMyViewHolder, position: Int, model: UserIssue) {
//        val db = FirebaseFirestore.getInstance()
//        val userId = FirebaseAuth.getInstance().currentUser?.uid
//        val userRef = db.collection("Users")
//        if (userId != null) {
//            userRef.document(userId).get().addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val document = task.result
//                    if (document.exists()) {
//                        holder.author_name.text = "By ${document.getString("name")}"
//                        Log.d(ContentValues.TAG, "${document.getString("name")}")
//                    } else {
//                        Log.d(ContentValues.TAG, "This does not exit")
//                    }
//                } else {
//                    task.exception?.message?.let {
//                        Log.d(ContentValues.TAG, it)
//                    }
//                }
//            }
//        }
//        holder.author_defect.text = model.issueDefect
//        holder.author_hostel.text = "Hostel no. ${model.userHostelno}"
//        holder.dateTime.text = model.issueDataTime
//        holder.typeIssue.text = "IssueType - ${model.issueType}"
//        if (model.admin_resolved_checkbox) {
//            holder.processStatus.setImageDrawable(
//                ContextCompat.getDrawable(
//                    context,
//                    R.drawable.greentick_icon
//                )
//            )
//        }
//    }
//}
