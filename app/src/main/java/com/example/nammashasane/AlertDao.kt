package com.example.nammashasane

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlertDao {

    @Insert
    suspend fun insertAlert(alert: AlertEntity)

    @Query("SELECT * FROM alerts")
    suspend fun getAllAlerts(): List<AlertEntity>
}