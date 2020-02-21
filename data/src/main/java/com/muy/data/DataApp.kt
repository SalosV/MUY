package com.muy.data

import android.app.Application
import androidx.room.Room
import com.muy.data.database.MUYDatabase

open class DataApp : Application() {

    lateinit var database: MUYDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            MUYDatabase::class.java,
            "MUY-database")
            .fallbackToDestructiveMigration()
            .build()
    }
}