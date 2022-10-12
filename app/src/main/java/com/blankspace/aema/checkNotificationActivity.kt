package com.blankspace.aema

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC = "/topics/myTopic2"

class checkNotificationActivity : AppCompatActivity() {
    lateinit var name1 : EditText
    lateinit var message1 : EditText
    lateinit var submit_button : Button

    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_notification)

        name1 = findViewById(R.id.name_text_person)
         message1 = findViewById(R.id.message_text_person)
        submit_button = findViewById(R.id.submit_button_notification)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        submit_button.setOnClickListener {
            val title = name1.text.toString()
            val message = message1.text.toString()

            if(title.isNotEmpty() && message.isNotEmpty()){
                PushNotification(
                    NotificationData(title,message),
                    TOPIC
                ).also {
                    sendNotification(it)
                }
            }
        }
    }

    private fun sendNotification(notification: PushNotification)= CoroutineScope(Dispatchers.IO).launch {

        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if(response.isSuccessful){
                Log.d(TAG,"Response: ${Gson().toJson(response)}")
            }else{
                Log.e(TAG, response.errorBody().toString())
            }
        }catch (e:Exception){
            Log.e(TAG, e.toString())
        }

    }
}