package com.example.nammashasane

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.google.android.gms.location.LocationServices

class PhotoActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val CAMERA_REQUEST = 1
    private val GALLERY_REQUEST = 2

    private var latitude = 0.0
    private var longitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photo)

        imageView =
            findViewById(R.id.imageView)

        val btnCamera =
            findViewById<Button>(R.id.btnCamera)

        val btnGallery =
            findViewById<Button>(R.id.btnGallery)

        // Permissions
        if (
            checkSelfPermission(
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED ||

            checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                100
            )
        }

        // CAMERA BUTTON
        btnCamera.setOnClickListener {

            getLocationAndOpenCamera()
        }

        // GALLERY BUTTON
        btnGallery.setOnClickListener {

            getLocationAndOpenGallery()
        }
    }

    // GET LOCATION THEN OPEN CAMERA
    private fun getLocationAndOpenCamera() {

        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)

        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        Toast.makeText(
            this,
            "📍 Fetching Location...",
            Toast.LENGTH_SHORT
        ).show()

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->

                if (location != null) {

                    latitude =
                        location.latitude

                    longitude =
                        location.longitude

                    Toast.makeText(
                        this,
                        "✅ Location Captured",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Toast.makeText(
                        this,
                        "❌ GPS Location Not Found",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // OPEN CAMERA AFTER GPS
                val cameraIntent =
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                startActivityForResult(
                    cameraIntent,
                    CAMERA_REQUEST
                )
            }
    }

    // GET LOCATION THEN OPEN GALLERY
    private fun getLocationAndOpenGallery() {

        val fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)

        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        Toast.makeText(
            this,
            "📍 Fetching Location...",
            Toast.LENGTH_SHORT
        ).show()

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->

                if (location != null) {

                    latitude =
                        location.latitude

                    longitude =
                        location.longitude

                    Toast.makeText(
                        this,
                        "✅ Location Captured",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Toast.makeText(
                        this,
                        "❌ GPS Location Not Found",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // OPEN GALLERY AFTER GPS
                val galleryIntent =
                    Intent(Intent.ACTION_PICK)

                galleryIntent.type =
                    "image/*"

                startActivityForResult(
                    galleryIntent,
                    GALLERY_REQUEST
                )
            }
    }

    // HANDLE CAMERA & GALLERY RESULT
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )

        if (resultCode == Activity.RESULT_OK) {

            // CAMERA
            if (requestCode == CAMERA_REQUEST) {

                val photo =
                    data?.extras?.get("data") as? Bitmap

                if (photo != null) {

                    imageView.setImageBitmap(photo)

                    StoryHolder.bitmap =
                        photo

                    Toast.makeText(
                        this,
                        "📷 Camera Photo Selected",
                        Toast.LENGTH_SHORT
                    ).show()

                    openStory("camera")
                }
            }

            // GALLERY
            else if (requestCode == GALLERY_REQUEST) {

                val imageUri: Uri? =
                    data?.data

                if (imageUri != null) {

                    imageView.setImageURI(imageUri)

                    Toast.makeText(
                        this,
                        "🖼 Gallery Image Selected",
                        Toast.LENGTH_SHORT
                    ).show()

                    openStory(
                        imageUri.toString()
                    )
                }
            }
        }
    }

    // OPEN STORY ACTIVITY
    private fun openStory(
        imageData: String
    ) {

        val intent =
            Intent(this, StoryActivity::class.java)

        intent.putExtra(
            "IMAGE_DATA",
            imageData
        )

        intent.putExtra(
            "LAT",
            latitude
        )

        intent.putExtra(
            "LNG",
            longitude
        )

        startActivity(intent)
    }
}