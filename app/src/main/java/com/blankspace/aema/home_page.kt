package com.blankspace.aema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class home_page : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
    }

    fun maintainance_butoon(view: View) {
        var intent = Intent(this, maintenance_form::class.java)
        startActivity(intent)
    }

    fun logOut(view: View) {
        auth = Firebase.auth
        Firebase.auth.signOut()
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun plumbing_button(view: View) {
        var intent = Intent(this, plumbing_form::class.java)
        startActivity(intent)
    }

    fun electrician_button(view: View) {
        var intent = Intent(this, electrician_form::class.java)
        startActivity(intent)
    }
}