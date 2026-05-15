package com.example.nammashasane

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(),
    OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_maps)

        val mapFragment =
            supportFragmentManager
                .findFragmentById(R.id.map)
                    as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {

        mMap = googleMap

        // Receive Coordinates
        val latitude =
            intent.getDoubleExtra("LAT", 0.0)

        val longitude =
            intent.getDoubleExtra("LNG", 0.0)

        val location =
            LatLng(latitude, longitude)

        // Add Marker
        mMap.addMarker(
            MarkerOptions()
                .position(location)
                .title("Detected Historical Place")
        )

        // Move Camera
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                location,
                15f
            )
        )

        // Enable My Location
        if (
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            mMap.isMyLocationEnabled = true
        }

        // Hybrid Map
        mMap.mapType =
            GoogleMap.MAP_TYPE_HYBRID
    }
}