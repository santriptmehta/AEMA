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
import com.blankspace.aema.Models.Electrical
import com.blankspace.aema.Models.User
import com.blankspace.aema.R
import com.blankspace.aema.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class electrician_form : AppCompatActivity() {
    lateinit var fauth : FirebaseAuth
    lateinit var username : String
    lateinit var userEmail : String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electrician_form)

        val roll_elc : EditText = findViewById(R.id.roll_no_elc)
        val hostel_elc : EditText = findViewById(R.id.hostel_no_elc)
        val location_elc : EditText = findViewById(R.id.location_elc)
        val defect_elc : EditText = findViewById(R.id.defect_elc)
        val description_elc : EditText = findViewById(R.id.description_elc)
        val sumbmit_button : Button = findViewById(R.id.ele_submit_button)

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
        sumbmit_button.setOnClickListener {
            val roll = roll_elc.text.toString()
            val hostelno = hostel_elc.text.toString()
            val locationNo = location_elc.text.toString()
            val defect = defect_elc.text.toString()
            val description = description_elc.text.toString()
            val rand_id = "${userEmail}${System.currentTimeMillis()}"

            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            val currTimeDate = current.format(formatter)

            roll_elc.error = null
            hostel_elc.error = null
            location_elc.error = null
            defect_elc.error = null
            description_elc.error = null

            if(TextUtils.isEmpty(roll)){
                roll_elc.error = "Required"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(hostelno)){
                hostel_elc.error = "Require"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(locationNo)){
                location_elc.error = "Required"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(defect)){
                defect_elc.error = "Required"
                return@setOnClickListener
            }


            val collectionRef = FirebaseFirestore.getInstance().collection("Electrical").document("${rand_id}")



            val user = Electrical(name = username,email = userEmail , dateTime = currTimeDate,
                roll_no = roll, hostel_no = hostelno, location = locationNo, defect = defect, description = description, id = rand_id)


            collectionRef.set(user).addOnCompleteListener{
                if(it.isSuccessful){
                    Toast.makeText(this, "Electrical updated", Toast.LENGTH_LONG).show()
                    UserUtils.getCurrentUser()
                }else{
                    Toast.makeText(this, "Someting went wrong. Please try again", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}
//@RequiresApi(Build.VERSION_CODES.O)
//fun main(args: Array<String>) {
//
//    val current = LocalDateTime.now()
//
//    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
//    val formatted = current.format(formatter)
//
//    println("Current Date is: $formatted")
//
//}