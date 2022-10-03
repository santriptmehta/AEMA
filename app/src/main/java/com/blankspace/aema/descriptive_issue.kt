package com.blankspace.aema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class descriptive_issue : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descriptive_issue)

        val issue_title:TextView = findViewById(R.id.issue_title)
        val issue_hostelNo : TextView = findViewById(R.id.issue_hotelno)
        val issue_locationNo : TextView = findViewById(R.id.issue_locationNo)
        val issue_rollNo : TextView = findViewById(R.id.issue_rollNo)
        val issue_description:TextView = findViewById(R.id.issue_description)
        
    }
}