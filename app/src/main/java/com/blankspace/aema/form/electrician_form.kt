package com.blankspace.aema.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.blankspace.aema.Models.Electrical
import com.blankspace.aema.R
import com.blankspace.aema.utils.UserUtils
import com.google.firebase.firestore.FirebaseFirestore

class electrician_form : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electrician_form)

        val roll_elc : EditText = findViewById(R.id.roll_no_elc)
        val hostel_elc : EditText = findViewById(R.id.hostel_no_elc)
        val location_elc : EditText = findViewById(R.id.location_elc)
        val defect_elc : EditText = findViewById(R.id.defect_elc)
        val description_elc : EditText = findViewById(R.id.description_elc)
        val sumbmit_button : Button = findViewById(R.id.ele_submit_button)

        sumbmit_button.setOnClickListener {
            val roll = roll_elc.text.toString()
            val hostelno = hostel_elc.text.toString()
            val locationNo = location_elc.text.toString()
            val defect = defect_elc.text.toString()
            val description = description_elc.text.toString()

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

            val collectionRef = FirebaseFirestore.getInstance().collection("Electrical")

            val user = Electrical(name = UserUtils.user?.name.toString(),email = UserUtils.user?.email.toString(),
                roll_no = roll, hostel_no = hostelno, location = locationNo, defect = defect, description = description)


            collectionRef.add(user).addOnCompleteListener{
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