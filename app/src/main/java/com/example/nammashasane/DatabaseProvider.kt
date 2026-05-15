package com.example.nammashasane

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var database: AlertDatabase? = null

    fun getDatabase(
        context: Context
    ): AlertDatabase {

        if (database == null) {

            database =
                Room.databaseBuilder(
                    context,
                    AlertDatabase::class.java,
                    "alert_db"
                ).build()
        }

        return database!!
    }
}