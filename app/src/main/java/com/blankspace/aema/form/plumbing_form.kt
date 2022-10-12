package com.blankspace.aema.form

import android.content.ContentValues
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.blankspace.aema.Models.Plumbing
import com.blankspace.aema.R
import com.blankspace.aema.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class plumbing_form : AppCompatActivity() {
    lateinit var userName : String
    lateinit var userEmail : String
    lateinit var fauth : FirebaseAuth
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plumbing_form)

        val plumRoll : EditText = findViewById(R.id.plumbingRoll)
        val plumHostelNo : EditText = findViewById(R.id.plumbingHostelNo)
        val plumLocation : EditText = findViewById(R.id.plumbingLocation)
        val plumDefect : EditText = findViewById(R.id.plumbingDefect)
        val plumDiscriptiom : EditText = findViewById(R.id.plumbingDescription)
        val plumButton : Button = findViewById(R.id.plumbingButton)

        val db = FirebaseFirestore.getInstance()
        fauth = FirebaseAuth.getInstance()
        val userId = fauth.currentUser?.uid
        val userRef = db.collection("Users")
        if (userId != null) {
            userRef.document(userId).get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val document = task.result
                    if(document.exists()){
                        userName = document.getString("name").toString()
                        userEmail = document.getString("email").toString()
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

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val currTimeDate = current.format(formatter)


        plumButton.setOnClickListener {
            val rollText = plumRoll.text.toString()
            val hostelNo = plumHostelNo.text.toString()
            val location = plumLocation.text.toString()
            val defect = plumDefect.text.toString()
            val discription = plumDiscriptiom.text.toString()
            val rand_id = "${userEmail}${System.currentTimeMillis()}"

            plumRoll.error = null
            plumHostelNo.error = null
            plumLocation.error = null
            plumDefect.error = null
            plumDiscriptiom.error = null

            if(TextUtils.isEmpty(rollText)){
                plumRoll.error = "Required"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(hostelNo)){
                plumHostelNo.error = "Require"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(location)){
                plumLocation.error = "Required"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(defect)){
                plumDefect.error = "Required"
                return@setOnClickListener
            }

            val collectionRef = FirebaseFirestore.getInstance().collection("Plumbing").document("${rand_id}")

            val userData = Plumbing(name = userName, email = userEmail, roll_no = rollText,id = rand_id, dateTime = currTimeDate,
                                     hostel_no = hostelNo, location = location, defect = defect, description = discription)

            collectionRef.set(userData).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this, "Plumbing Complaint Registered", Toast.LENGTH_LONG).show()
                    UserUtils.getCurrentUser()
                }else{
                    Toast.makeText(this, "Someting went wrong Please try again", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}