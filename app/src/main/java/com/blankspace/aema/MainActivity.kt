package com.blankspace.aema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.blankspace.aema.auth.cadet_login
import com.blankspace.aema.notification.checkNotificationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    lateinit var cadet_button : Button
    private lateinit var auth : FirebaseAuth
    lateinit var text_notification : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        cadet_button = findViewById(R.id.caddet_button)
        text_notification = findViewById(R.id.text_notification)

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
        text_notification.setOnClickListener {
            val intent = Intent(this, checkNotificationActivity::class.java)
            startActivity(intent)
        }
        
        
    }



}