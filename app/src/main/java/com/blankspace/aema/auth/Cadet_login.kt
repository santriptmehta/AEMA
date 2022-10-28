package com.blankspace.aema.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.blankspace.aema.AppMainActivityBeta
import com.blankspace.aema.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class cadet_login : AppCompatActivity() {
    lateinit var cadet_login_username : EditText
    lateinit var cadet_passward : EditText
    lateinit var cadet_login_button : Button
    private lateinit var auth : FirebaseAuth
    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadet_login)

        cadet_login_username = findViewById(R.id.cadet_username)
        cadet_passward = findViewById(R.id.cadet_passward)
        cadet_login_button = findViewById(R.id.cadet_loginbt)
        auth = Firebase.auth
        progressBar = findViewById(R.id.progress_bar)

        if(auth.currentUser != null){
            progressBar.visibility = View.VISIBLE
            var intent = Intent(this, AppMainActivityBeta::class.java)
            startActivity(intent)
            progressBar.visibility = View.VISIBLE
            finish()
        }else{
            cadet_login_button.setOnClickListener {
                progressBar.visibility = View.VISIBLE

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

                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{task ->

                        if(task.isSuccessful){
                            var intent = Intent(this, AppMainActivityBeta::class.java)
                            progressBar.visibility = View.GONE
                            startActivity(intent)
                            finish()
                            Toast.makeText(this,"successfull",Toast.LENGTH_LONG).show()
                        }else {
                            Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show()
                        }
                    }
            }

        }

    }

    fun register_switch(view: View) {
        var intent = Intent(this, register_form::class.java)
        startActivity(intent)
        finish()
    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            readln()
//        }
//    }


}