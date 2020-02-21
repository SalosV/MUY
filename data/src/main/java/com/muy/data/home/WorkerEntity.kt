package com.muy.data.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.muy.domain.home.Employee
import com.muy.domain.utils.EMPTY_BOOLEAN
import com.muy.domain.utils.EMPTY_INT
import com.muy.domain.utils.EMPTY_STRING

@Entity
data class WorkerEntity(
    @PrimaryKey(autoGenerate = false) val id: Int = EMPTY_INT,
    val fullName: String = EMPTY_STRING,
    val position: String = EMPTY_STRING,
    val salary: Int = EMPTY_INT,
    val phone: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val upperRelation: Int = EMPTY_INT,
    val isNew: Boolean = EMPTY_BOOLEAN
)

fun WorkerEntity.toDomainModel() = Employee(
    id = id,
    fullName = fullName,
    position = position,
    salary = salary.toString(),
    phone = phone,
    email = email,
    upperRelation = upperRelation,
    isNew = isNew
)