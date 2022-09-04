package com.blankspace.aema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class stdlogin : AppCompatActivity() {
    lateinit var cadet_login_username : EditText
    lateinit var cadet_passward : EditText
    lateinit var cadet_button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stdlogin)

    }
}