package com.blankspace.aema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.blankspace.aema.auth.cadet_login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var cadet_button : Button
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cadet_button = findViewById(R.id.caddet_button)
        auth = Firebase.auth

        if(auth.currentUser != null){
            var intent = Intent(this, home_page::class.java)
            startActivity(intent)
            finish()
        }
        cadet_button.setOnClickListener {
            var intent = Intent(this, cadet_login::class.java)
            startActivity(intent)
            finish()
        }

    }


}