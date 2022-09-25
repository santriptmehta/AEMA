package com.blankspace.aema

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.blankspace.aema.complaint_form.complaint_view
import com.blankspace.aema.form.electrician_form
import com.blankspace.aema.form.maintenance_form
import com.blankspace.aema.form.plumbing_form
import com.blankspace.aema.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class home_page : AppCompatActivity() {
     lateinit var fauth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val user_name_text : TextView = findViewById(R.id.user_text)
        user_name_text.text = "Hello Unknown"
        val db = FirebaseFirestore.getInstance()
        fauth = FirebaseAuth.getInstance()
        val userId = fauth.currentUser?.uid
        val userRef = db.collection("Users")
        if (userId != null) {
            userRef.document(userId).get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val document = task.result
                    if(document.exists()){
                        user_name_text.text = "Hello ${document.getString("name")}"
                        Log.d(TAG,"${document.getString("name")}")
                    }else{
                        Log.d(TAG, "This does not exit")
                    }
                }else{
                    task.exception?.message?.let {
                        Log.d(TAG,it)
                    }
                }
            }
        }
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
        fauth = Firebase.auth
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