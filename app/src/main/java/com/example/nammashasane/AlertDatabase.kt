package com.example.nammashasane

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AlertEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AlertDatabase : RoomDatabase() {

    abstract fun alertDao(): AlertDao
}