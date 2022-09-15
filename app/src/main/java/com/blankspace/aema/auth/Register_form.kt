package com.blankspace.aema.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.blankspace.aema.Models.User
import com.blankspace.aema.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class register_form : AppCompatActivity() {
    companion object {
        const val TAG = "RegisterFragment"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)
        val emailText: TextInputLayout = findViewById(R.id.email_text)
        val nameText: TextInputLayout = findViewById(R.id.name_text)
        val passwordText: TextInputLayout = findViewById(R.id.password_text)
        val confirmPasswordText: TextInputLayout = findViewById(R.id.confirm_password_text)
        val registerButton: Button = findViewById(R.id.register_button)
        val registerProgress: ProgressBar = findViewById(R.id.register_progress)



        registerButton.setOnClickListener {
            val email = emailText.editText?.text.toString()
            val name = nameText.editText?.text.toString()
            val password = passwordText.editText?.text.toString()
            val confirmPassword = confirmPasswordText.editText?.text.toString()

            emailText.error = null
            nameText.error = null
            passwordText.error = null
            confirmPasswordText.error = null


            if (TextUtils.isEmpty(password)) {
                passwordText.error = "Password is required"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                confirmPasswordText.error = "Confirm password field is required"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                confirmPasswordText.error = "Passwords do not match"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailText.error = "Please enter a valid email address"
                return@setOnClickListener
            }
            registerProgress.visibility = View.VISIBLE

            val auth = FirebaseAuth.getInstance()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = User(auth.currentUser?.uid!!, name, email)
                        val firestore = FirebaseFirestore.getInstance().collection("Users")
                        firestore.document(auth.currentUser?.uid!!).set(user)
                            .addOnCompleteListener { task2 ->
                                registerProgress.visibility = View.GONE
                                if (task2.isSuccessful) {
                                    val intent = Intent(this, cadet_login::class.java)
                                    startActivity(intent)
                                } else {
                                     Toast.makeText(this,"Something went wrong. Please try again.", Toast.LENGTH_LONG).show()
                                    Log.d(TAG, task.exception.toString())
                                }
                            }
                    } else {
                        registerProgress.visibility = View.GONE
                        Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_LONG).show()
                        Log.d(TAG, task.exception.toString())
                    }
                }


        }
    }

    fun loginFromHere(view: View) {
        var intent = Intent(this, cadet_login::class.java)
        startActivity(intent)
    }
}