package com.example.nammashasane

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class AlertEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val imageUri: String,

    val placeName: String,

    val issueType: String
)