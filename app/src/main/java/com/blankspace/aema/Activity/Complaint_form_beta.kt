package com.blankspace.aema.Activity

import android.content.ContentValues
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.blankspace.aema.AppMainActivityBeta
import com.blankspace.aema.Models.UserIssue
import com.blankspace.aema.R
import com.blankspace.aema.notification.NotificationData
import com.blankspace.aema.notification.PushNotification
import com.blankspace.aema.notification.RetrofitInstance
import com.blankspace.aema.utils.UserUtils
import com.blankspace.aemaadmin.model.Electrical
import com.blankspace.aemaadmin.model.Maintenance
import com.blankspace.aemaadmin.model.Plumbing
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

const val TOPIC = "/topics/myTopic2"

class complaint_form_beta : AppCompatActivity() {
    val TAG = "MainActivity"
    lateinit var progressBar: ProgressBar
    lateinit var userName : String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_form_beta)

        progressBar = findViewById(R.id.progress_bar)
        val checkBox_plum : CheckBox = findViewById(R.id.plum_checkBox)
        val checkBox_Elec : CheckBox = findViewById(R.id.Elec_checkBox)
        val checkBox_Maint : CheckBox = findViewById(R.id.maintain_checkBox)

        // checkBox activity to select one at a time
        checkBox_plum.setOnClickListener() {
            checkBox_plum.isChecked = true
            checkBox_Maint.isChecked = false
            checkBox_Elec.isChecked = false
        }
        checkBox_Elec.setOnClickListener() {
            checkBox_plum.isChecked = false
            checkBox_Maint.isChecked = false
            checkBox_Elec.isChecked = true
        }
        checkBox_Maint.setOnClickListener() {
            checkBox_plum.isChecked = false
            checkBox_Maint.isChecked = true
            checkBox_Elec.isChecked = false
        }

        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userRef = db.collection("Users")
        if(userId != null){
            userRef.document(userId).get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val document = task.result
                    if(document.exists()){
                        userName = "${document.getString("name")}"
                        Log.d(ContentValues.TAG,"${document.getString("name")}")
                    }else{
                        Log.d(ContentValues.TAG, "This does not exit")
                    }
                }else{
                    task.exception?.message?.let {
                        Log.d(ContentValues.TAG,it)
                    }
                }
            }
        }


        submitForm()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun submitForm(){
        // all layout declaration
        val issuerRollNo : EditText = findViewById(R.id.issuerRollNo)
        val issuerHostelNo : EditText = findViewById(R.id.issuerHostelNo)
        val issueLocation : EditText = findViewById(R.id.issuerLocation)
        val issueDefect : EditText = findViewById(R.id.issuerDefect)
        val issuerDescribe : EditText = findViewById(R.id.issuerDescribe)
        val issueSubmitButton : Button = findViewById(R.id.issueSubmitButton)
        val checkBox_plum : CheckBox = findViewById(R.id.plum_checkBox)
        val checkBox_Elec : CheckBox = findViewById(R.id.Elec_checkBox)
        val checkBox_Maint : CheckBox = findViewById(R.id.maintain_checkBox)



        issueSubmitButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val roll = issuerRollNo.text.toString()
            val hostelNo = issuerHostelNo.text.toString()
            val location = issueLocation.text.toString()
            val defect = issueDefect.text.toString()
            val describe = issuerDescribe.text.toString()
            val IssueId = "${FirebaseAuth.getInstance().currentUser?.email}${System.currentTimeMillis()}"

            var typeIssue = "Maintenance"

            if(checkBox_Elec.isChecked){
                typeIssue = "Electrical"
            }else if (checkBox_plum.isChecked){
                typeIssue = "Plumbing"
            }


            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val currTimeDate = current.format(formatter)

            issuerRollNo.error = null
            issuerHostelNo.error = null
            issueLocation.error = null
            issueDefect.error = null
            issuerDescribe.error = null

            if(TextUtils.isEmpty(roll)){
                issuerRollNo.error = "Required"
                progressBar.visibility = View.GONE
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(hostelNo)){
                issuerHostelNo.error = "Require"
                progressBar.visibility = View.GONE
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(location)){
                issueLocation.error = "Required"
                progressBar.visibility = View.GONE
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(defect)){
                issueDefect.error = "Required"
                progressBar.visibility = View.GONE
                return@setOnClickListener
            }





            if(checkBox_Elec.isChecked){
                val electricalIssueData = Electrical(id_issue = IssueId,name = userName, userEmail = "${FirebaseAuth.getInstance().currentUser?.email}", userRollno = roll,
                    userHostelno = hostelNo, issueLocation = location, issueDefect = defect,
                    issueType = typeIssue ,issueDescribe = describe, issueDataTime = currTimeDate)
                val elec_DataBaseRef = FirebaseFirestore.getInstance().collection("Electrical").document(IssueId)
                elec_DataBaseRef.set(electricalIssueData).addOnSuccessListener {
                    Toast.makeText(this, "Sent to Electrical", Toast.LENGTH_SHORT).show()
                }
                    .addOnFailureListener {
                        Toast.makeText(this, "Something Wrong, Try after SomeTime", Toast.LENGTH_SHORT).show()
                    }
            }
            if(checkBox_plum.isChecked){
                val plumbingIssueData = Plumbing(id_issue = IssueId,name = userName, userEmail = "${FirebaseAuth.getInstance().currentUser?.email}", userRollno = roll,
                    userHostelno = hostelNo, issueLocation = location, issueDefect = defect,
                    issueType = typeIssue ,issueDescribe = describe, issueDataTime = currTimeDate)
                val elec_DataBaseRef = FirebaseFirestore.getInstance().collection("Plumbing").document(IssueId)
                elec_DataBaseRef.set(plumbingIssueData).addOnSuccessListener {
                    Toast.makeText(this, "Sent to Plumbing", Toast.LENGTH_SHORT).show()
                }
                    .addOnFailureListener {
                        Toast.makeText(this, "Something Wrong, Try after SomeTime", Toast.LENGTH_SHORT).show()
                    }
            }
            if(checkBox_Maint.isChecked){
                val MaintainanceIssueData = Maintenance(id_issue = IssueId,name = userName ,userEmail = "${FirebaseAuth.getInstance().currentUser?.email}", userRollno = roll,
                    userHostelno = hostelNo, issueLocation = location, issueDefect = defect,
                    issueType = typeIssue ,issueDescribe = describe, issueDataTime = currTimeDate)
                val elec_DataBaseRef = FirebaseFirestore.getInstance().collection("Maintenance").document(IssueId)
                elec_DataBaseRef.set(MaintainanceIssueData).addOnSuccessListener {
                    Toast.makeText(this, "Sent to Maintenance", Toast.LENGTH_SHORT).show()
                }
                    .addOnFailureListener {
                        Toast.makeText(this, "Something Wrong, Try after SomeTime", Toast.LENGTH_SHORT).show()
                    }
            }

            val userIssueData = UserIssue(id_issue = IssueId,userEmail = "${FirebaseAuth.getInstance().currentUser?.email}", userRollno = roll,
            userHostelno = hostelNo, issueLocation = location, issueDefect = defect,
            issueType = typeIssue ,issueDescribe = describe, issueDataTime = currTimeDate)
            val collRef = FirebaseFirestore.getInstance().collection("UserData").document(FirebaseAuth.getInstance().currentUser?.email.toString()).collection("RegisteredComplaint").document(IssueId)

            collRef.set(userIssueData).addOnSuccessListener {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "Issue has been Registered", Toast.LENGTH_LONG).show()
                    var intent = Intent(this,AppMainActivityBeta::class.java)
                    startActivity(intent)
                val title = "AEMA NewComplaint"
                val message = "Shit New Message has arrive"

                    if(title.isNotEmpty() && message.isNotEmpty()){
                        PushNotification(
                            NotificationData(title,message),
                            TOPIC
                        ).also {
                            sendNotification(it)
                        }
                    }

                    UserUtils.getCurrentUser()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Something went wrong. Please try again", Toast.LENGTH_LONG).show()
                }
            }

        }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response)}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }
}
