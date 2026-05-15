package com.example.nammashasane

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    private lateinit var cardPhoto: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // Initialize Views

        cardPhoto =
            findViewById(R.id.cardPhoto)

        // OPEN PHOTO AI
        cardPhoto.setOnClickListener {

            val intent =
                Intent(this, PhotoActivity::class.java)

            startActivity(intent)
        }

    }
}