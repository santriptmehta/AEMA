package com.blankspace.aema

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.blankspace.aema.Activity.complaint_form_beta
import com.blankspace.aema.auth.cadet_login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class AppMainActivityBeta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_main_beta)


        displayUserName()
    }

    fun registerButton(view: View) {
        val intent = Intent(this, complaint_form_beta::class.java)
        startActivity(intent)
    }
    fun pastRegisterButton(view: View) {
        val intent = Intent(this, pastRegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun displayUserName(){
        val currNameDisplay : TextView = findViewById(R.id.userTitle)
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val userRef = db.collection("Users")
        if(userId != null){
            userRef.document(userId).get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val document = task.result
                    if(document.exists()){
                        currNameDisplay.text = "${document.getString("name")}"
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
    }
//    fun displayNoOfcompaint(){
////        val countTitle : TextView = findViewById(R.id.userNameWithtitle)
////        val countNo : TextView = findViewById(R.id.countCompaint)
//        val firestore  =  FirebaseFirestore.getInstance()
//        val currEmail = FirebaseAuth.getInstance()
//        val productsRef = firestore.collection("UserData").document(currEmail.currentUser?.email!!).collection("RegisteredComplaint")
//
//        productsRef.get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                var count = 0
//                task.result?.let {
//                    for (snapshot in it) {
//                        count++
//                    }
//                }
//                print("count: $count")
//            } else {
//                task.exception?.message?.let {
//                    print(it)
//                }
//            }
//        }
//    }

    fun logoutAccount(view: View) {
        Firebase.auth.signOut()
        val intent = Intent(this, cadet_login::class.java)
        startActivity(intent)
        finish()
    }
}

//fun main(){
//    val firestore  =  FirebaseFirestore.getInstance()
//    val currEmail = FirebaseAuth.getInstance()
//    val productsRef = firestore.collection("UserData").document(currEmail.currentUser?.email!!).collection("RegisteredComplaint")
//
//    productsRef.get().addOnCompleteListener { task ->
//        if (task.isSuccessful) {
//            var count = 0
//            task.result?.let {
//                for (snapshot in it) {
//                    count++
//                }
//            }
//            print("count: $count")
//        } else {
//            task.exception?.message?.let {
//                print(it)
//            }
//        }
//    }
//}