package com.blankspace.aema.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.blankspace.aema.Models.User
import com.blankspace.aema.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class register_form : AppCompatActivity() {
    companion object {
        const val TAG = "RegisterFragment"
    }
    private lateinit var progressVar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)
        val emailText: EditText = findViewById(R.id.email_text)
        val nameText: EditText = findViewById(R.id.name_text)
        val passwordText: EditText = findViewById(R.id.password_text)
        val confirmPasswordText: EditText = findViewById(R.id.confirm_password_text)
        val registerButton: Button = findViewById(R.id.register_button)
        val loginPage : TextView = findViewById(R.id.go_to_login)
        progressVar = findViewById(R.id.progress_bar)



        registerButton.setOnClickListener {
            progressVar.visibility= View.VISIBLE
            val email = emailText.text.toString()
            val name = nameText.text.toString()
            val password = passwordText.text.toString()
            val confirmPassword = confirmPasswordText.text.toString()


            emailText.error = null
            nameText.error = null
            passwordText.error = null
            confirmPasswordText.error = null

            if(TextUtils.isEmpty(name)){
                nameText.error = "Name is required"
                progressVar.visibility= View.GONE
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                passwordText.error = "Password is required"
                progressVar.visibility= View.GONE
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                confirmPasswordText.error = "Confirm password field is required"
                progressVar.visibility= View.GONE
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                confirmPasswordText.error = "Passwords do not match"
                progressVar.visibility= View.GONE
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailText.error = "Please enter a valid email address"
                progressVar.visibility= View.GONE
                return@setOnClickListener
            }


            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = User(auth.currentUser?.uid!!, name, email)
                        val firestore = FirebaseFirestore.getInstance().collection("Users")
                        firestore.document(auth.currentUser?.uid!!).set(user)
                            .addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    Toast.makeText(this,"Successfully Account Created.", Toast.LENGTH_LONG).show()
                                    progressVar.visibility= View.GONE
                                    val intent = Intent(this, cadet_login::class.java)
                                    startActivity(intent)

                                } else {
                                     Toast.makeText(this,"Something went wrong. Please try again.", Toast.LENGTH_LONG).show()
                                    progressVar.visibility= View.GONE
                                    Log.d(TAG, task.exception.toString())
                                }
                            }

                    } else {
                        Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show()
                        progressVar.visibility= View.GONE
                        Log.d(TAG, task.exception.toString())
                    }
                }

        }

        loginPage.setOnClickListener {
            var intent = Intent(this, cadet_login::class.java)
            startActivity(intent)
        }
    }

    fun loginFromHere(view: View) {
        var intent = Intent(this, cadet_login::class.java)
        startActivity(intent)
    }
}