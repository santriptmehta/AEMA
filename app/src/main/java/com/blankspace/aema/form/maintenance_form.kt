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
import com.blankspace.aema.Models.Maintainance
import com.blankspace.aema.R
import com.blankspace.aema.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class maintenance_form : AppCompatActivity() {
    lateinit var rollNo : EditText
    lateinit var hostelNo : EditText
    lateinit var location : EditText
    lateinit var defectHeading : EditText
    lateinit var description : EditText
    lateinit var submit_button : Button
    lateinit var fauth : FirebaseAuth
    lateinit var username : String
    lateinit var userEmail : String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintenance_form)

        rollNo = findViewById(R.id.cadet_roll_no)
        hostelNo = findViewById(R.id.hostel_no)
        location = findViewById(R.id.location)
        defectHeading = findViewById(R.id.defect)
        description = findViewById(R.id.desctiption)
        submit_button = findViewById(R.id.submit_button)


        val db = FirebaseFirestore.getInstance()
        fauth = FirebaseAuth.getInstance()
        val userId = fauth.currentUser?.uid
        val userRef = db.collection("Users")
        if (userId != null) {
            userRef.document(userId).get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val document = task.result
                    if(document.exists()){
                        username = document.getString("name").toString()
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
        submit_button.setOnClickListener {
            val rollNoText = rollNo.text.toString()
            val hostelNoText = hostelNo.text.toString()
            val locationText = location.text.toString()
            val defectHeadingText = defectHeading.text.toString()
            val descriptionText = description.text.toString()
            val rand_id = "${userEmail}${System.currentTimeMillis()}"

            rollNo.error = null
            hostelNo.error = null
            location.error = null
            defectHeading.error = null
            description.error = null

            if(TextUtils.isEmpty(rollNoText)){
                rollNo.error = "Required"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(hostelNoText)){
                hostelNo.error = "Require"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(locationText)){
                location.error = "Required"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(defectHeadingText)){
                defectHeading.error = "Required"
                return@setOnClickListener
            }


            val collectionRef =  FirebaseFirestore.getInstance().collection("Maintainance").document("${rand_id}")

            val auth = FirebaseAuth.getInstance()
            val user = Maintainance( name = username,email = userEmail,id = rand_id, dateTime = currTimeDate,
                                     roll_no = rollNoText, hostel_no = hostelNoText, location = locationText, defect = defectHeadingText, description = descriptionText)

            collectionRef.set(user).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this, "Maintainance updated.", Toast.LENGTH_LONG).show()
                    UserUtils.getCurrentUser()
                } else {
                    Toast.makeText(this,"Something went wrong. Please try again.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}