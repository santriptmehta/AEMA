package com.blankspace.aema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var cadet_button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cadet_button = findViewById(R.id.caddet_button)

        cadet_button.setOnClickListener {
            var intent = Intent(this,dataform1::class.java)
            startActivity(intent)
        }

    }
}