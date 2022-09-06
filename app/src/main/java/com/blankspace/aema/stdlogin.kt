package com.blankspace.aema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class stdlogin : AppCompatActivity() {
    lateinit var cadet_login_username : EditText
    lateinit var cadet_passward : EditText
    lateinit var cadet_login_button : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stdlogin)

        cadet_login_username = findViewById(R.id.cadet_username)
        cadet_passward = findViewById(R.id.cadet_passward)
        cadet_login_button = findViewById(R.id.cadet_loginbt)


        cadet_login_button.setOnClickListener {
            val email = cadet_login_username.text.toString()
            val password = cadet_passward.text.toString()

            cadet_login_username.error = null
            cadet_passward.error = null

            if(TextUtils.isEmpty(email)){
                cadet_login_button.error = "Email is Required"
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(password)){
                cadet_passward.error = "Password is Required"
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                cadet_login_username.error = "Please enter a valid email address"
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{task ->

                    if(task.isSuccessful){
                        var intent = Intent(this, option::class.java)
                        startActivity(intent)
                        Toast.makeText(this,"successfull",Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }


}