package com.muy.data.home

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query

@Dao
interface WorkersDao {

    @Query("SELECT * FROM WorkerEntity")
    fun getAllWorkers(): List<WorkerEntity>

    @Query("SELECT COUNT(*) FROM WorkerEntity")
    fun workersCounts(): Int

    @Query("SELECT * FROM WorkerEntity WHERE id = :id")
    fun findEmployee(id: Int): WorkerEntity

    @Insert(onConflict = IGNORE)
    fun insertWorkers(workers: List<WorkerEntity>)

    @Query("UPDATE WorkerEntity SET isNew = :isNew WHERE id = :idEmployee")
    fun updateEmployee(isNew: Boolean, idEmployee: Int)

    @Query("SELECT * FROM WorkerEntity WHERE isNew=:isNew")
    fun filterWorkersNew(isNew: Boolean): List<WorkerEntity>

    @Query("SELECT * FROM WorkerEntity ORDER BY salary DESC")
    fun orderSalary(): List<WorkerEntity>

}