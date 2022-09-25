package com.blankspace.aema.form

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.blankspace.aema.Models.Maintainance
import com.blankspace.aema.R
import com.blankspace.aema.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class maintenance_form : AppCompatActivity() {
    lateinit var rollNo : EditText
    lateinit var hostelNo : EditText
    lateinit var location : EditText
    lateinit var defectHeading : EditText
    lateinit var description : EditText
    lateinit var submit_button : Button
    lateinit var fauth : FirebaseAuth
    lateinit var username : String
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
        submit_button.setOnClickListener {
            val rollNoText = rollNo.text.toString()
            val hostelNoText = hostelNo.text.toString()
            val locationText = location.text.toString()
            val defectHeadingText = defectHeading.text.toString()
            val descriptionText = description.text.toString()
            val user_name = username.toString()


            val collectionRef =  FirebaseFirestore.getInstance().collection("Maintainance")

            val auth = FirebaseAuth.getInstance()
            val user = Maintainance( name = user_name,email = UserUtils.user?.email.toString(),
                                     roll_no = rollNoText, hostel_no = hostelNoText, location = locationText, defect = defectHeadingText, description = descriptionText)

            collectionRef.add(user).addOnCompleteListener{
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