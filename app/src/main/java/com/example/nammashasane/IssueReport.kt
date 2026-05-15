package com.example.nammashasane

data class IssueReport(
    val imageUri: String,
    val story: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "NEW"
)