package com.example.nammashasane

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertActivity : AppCompatActivity() {

    private lateinit var btnSave: Button
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_alert)

        val imageView =
            findViewById<ImageView>(R.id.imageView)

        val txtPlace =
            findViewById<TextView>(R.id.txtPlace)

        btnSave =
            findViewById(R.id.btnSave)

        btnSubmit =
            findViewById(R.id.btnSubmit)

        val imageUri =
            intent.getStringExtra("IMAGE_URI")

        val placeName =
            intent.getStringExtra("PLACE_NAME")

        // Show Image
        if (imageUri != null) {

            imageView.setImageURI(
                Uri.parse(imageUri)
            )
        }

        // Show Place Name
        txtPlace.text = placeName

        // SAVE REPORT
        btnSave.setOnClickListener {

            val alert = AlertEntity(

                imageUri = imageUri ?: "",

                placeName = placeName ?: "",

                issueType = "Damaged Heritage"
            )

            CoroutineScope(Dispatchers.IO).launch {

                DatabaseProvider
                    .getDatabase(this@AlertActivity)
                    .alertDao()
                    .insertAlert(alert)

                runOnUiThread {

                    Toast.makeText(
                        this@AlertActivity,
                        "✅ Report Saved",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // SEND REPORT
        btnSubmit.setOnClickListener {

            try {

                val message =
                    """
                    🚨 Heritage Damage Alert
                    
                    Place: $placeName
                    
                    Issue: Damaged Heritage
                    
                    Sent from Namma Shasane
                    """.trimIndent()

                val intent =
                    Intent(Intent.ACTION_SENDTO)

                intent.data =
                    Uri.parse("smsto:1234567890")//add valid number

                intent.putExtra(
                    "sms_body",
                    message
                )

                startActivity(intent)

            } catch (e: Exception) {

                Toast.makeText(
                    this,
                    "❌ SMS App Not Found",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}