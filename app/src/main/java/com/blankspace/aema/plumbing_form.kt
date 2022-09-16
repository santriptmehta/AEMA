package com.blankspace.aema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.blankspace.aema.Models.Plumbing
import com.blankspace.aema.utils.UserUtils
import com.google.firebase.firestore.FirebaseFirestore

class plumbing_form : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plumbing_form)

        val plumRoll : EditText = findViewById(R.id.plumbingRoll)
        val plumHostelNo : EditText = findViewById(R.id.plumbingHostelNo)
        val plumLocation : EditText = findViewById(R.id.plumbingLocation)
        val plumDefect : EditText = findViewById(R.id.plumbingDefect)
        val plumDiscriptiom : EditText = findViewById(R.id.plumbingDescription)
        val plumButton : Button = findViewById(R.id.plumbingButton)

        plumButton.setOnClickListener {
            val rollText = plumRoll.text.toString()
            val hostelNo = plumHostelNo.text.toString()
            val location = plumLocation.text.toString()
            val defect = plumDefect.text.toString()
            val discription = plumDiscriptiom.text.toString()

            val collectionRef = FirebaseFirestore.getInstance().collection("Plumbing")

            val userData = Plumbing(name = UserUtils.user?.name.toString(), email = UserUtils.user?.email.toString(), roll_no = rollText,
                                     hostel_no = hostelNo, location = location, defect = defect, description = discription)

            collectionRef.add(userData).addOnCompleteListener{
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