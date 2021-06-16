package com.omninos.nani.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omninos.nani.R

class DeeplinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deeplink)

        val intent = intent
        val data = intent.data

        if (data != null) {
            print("Data :" + data.getQueryParameter("id"))
        }
    }
}
