package com.example.nammashasane

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var txtName: TextView
    private lateinit var txtEmail: TextView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        txtName =
            findViewById(R.id.txtName)

        txtEmail =
            findViewById(R.id.txtEmail)

        btnLogout =
            findViewById(R.id.btnLogout)

        // User Data
        txtName.text =
            "Paramesh"

        txtEmail.text =
            "parameshdece2026@gmail.com"

        // LOGOUT
        btnLogout.setOnClickListener {

            val intent =
                Intent(this, LoginActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }
    }
}