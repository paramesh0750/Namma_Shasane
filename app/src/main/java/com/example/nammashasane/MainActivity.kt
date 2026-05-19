package com.example.nammashasane

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var cardPhoto: LinearLayout
    private lateinit var btnProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Initialize Views
        cardPhoto =
            findViewById(R.id.cardPhoto)

        btnProfile =
            findViewById(R.id.btnProfile)

        // OPEN PHOTO AI
        cardPhoto.setOnClickListener {

            val intent =
                Intent(this, PhotoActivity::class.java)

            startActivity(intent)
        }

        // OPEN PROFILE
        btnProfile.setOnClickListener {

            val intent =
                Intent(this, ProfileActivity::class.java)

            startActivity(intent)
        }
    }
}