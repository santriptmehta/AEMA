package com.blankspace.aema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class option : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
    }

    fun maintainance_butoon(view: View) {
        var intent = Intent(this, dataform3::class.java)
        startActivity(intent)
    }

    fun plumbing_button(view: View) {
        var intent = Intent(this, dataform2::class.java)
        startActivity(intent)
    }
    fun electrician_button(view: View) {
        var intent = Intent(this, dataform1::class.java)
        startActivity(intent)
    }

}