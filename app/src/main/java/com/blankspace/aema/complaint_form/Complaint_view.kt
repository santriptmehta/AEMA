package com.blankspace.aema.complaint_form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.blankspace.aema.R

class complaint_view : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint_view)


        supportFragmentManager.beginTransaction()
            .replace(R.id.view_complaint_fragment,Fragment_plimbing())
            .commit()
    }

    private fun setFrag(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.view_complaint_fragment,fragment)
            .commit()
    }

    fun view_elec_frag(view: View) {
        setFrag(Fragment_electrical())
    }
    fun view_maint_frag(view: View) {

        setFrag(Fragment_maintainance())
    }
    fun view_plum_frag(view: View) {
        setFrag(Fragment_plimbing())
    }
}