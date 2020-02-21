package com.muy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muy.data.home.WorkerEntity
import com.muy.data.home.WorkersDao

@Database(
    entities = [WorkerEntity::class],
    version = 1)
abstract class MUYDatabase: RoomDatabase() {
    abstract fun getWorkersDao() : WorkersDao
}