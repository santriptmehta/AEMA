package com.blankspace.aema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.blankspace.aema.complaint_form.complaint_view
import com.blankspace.aema.form.electrician_form
import com.blankspace.aema.form.maintenance_form
import com.blankspace.aema.form.plumbing_form
import com.blankspace.aema.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class home_page : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private var mFirebaseDatabaseInstance:FirebaseFirestore?=null
    private var userId:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val user_name_text : TextView = findViewById(R.id.user_text)
        val name = UserUtils.user?.email.toString()

        mFirebaseDatabaseInstance= FirebaseFirestore.getInstance()
        user_name_text.text = name
        println(name)
    }
    fun maintainance_butoon(view: View) {
        val intent = Intent(this, maintenance_form::class.java)
        startActivity(intent)
    }
    fun plumbing_button(view: View) {
        val intent = Intent(this, plumbing_form::class.java)
        startActivity(intent)
    }
    fun electrician_button(view: View) {
        val intent = Intent(this, electrician_form::class.java)
        startActivity(intent)
    }
    fun logOut(view: View) {
        auth = Firebase.auth
        Firebase.auth.signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun view_complaint(view: View) {
        val intent = Intent(this, complaint_view::class.java)
        startActivity(intent)
    }
}