package com.blankspace.aema.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blankspace.aema.R

class detailComplaintView : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_complaint_view, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val issueType : TextView = view.findViewById(R.id.changeType)
        val dateTime : TextView = view.findViewById(R.id.dateTime)
        val hostelNo : TextView = view.findViewById(R.id.changeHostel)
        val rollNo : TextView = view.findViewById(R.id.changeRoll)
        val title : TextView = view.findViewById(R.id.changeTitle)
        val location : TextView = view.findViewById(R.id.changeLocation)
        val description : TextView = view.findViewById(R.id.changeDescription)
        val admin_feedback : TextView = view.findViewById(R.id.changeFeedback)


        issueType.text = arguments?.getString("typeIssue")
        dateTime.text =  arguments?.getString("dateTime")
        hostelNo.text = arguments?.getString("Hostelno")
        rollNo.text = arguments?.getString("Rollno")
        title.text = arguments?.getString("defectTitle")
        location.text = arguments?.getString("Location")
        description.text = arguments?.getString("Description")
        admin_feedback.text = arguments?.getString("admin_review_feedBack")


    }


}