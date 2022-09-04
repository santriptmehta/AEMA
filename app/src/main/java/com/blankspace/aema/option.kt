package com.blankspace.aema

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class option : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
    }
    public fun m(void: Void) {
        var intent = Intent(this, dataform3::class.java)
        startActivity(intent)
    }
    public fun p(void: Void){
        var intent = Intent(this, dataform2::class.java)
        startActivity(intent)
    }
    public fun e(void: Void){
        var intent = Intent(this,dataform1::class.java)
        startActivity(intent)
    }

}