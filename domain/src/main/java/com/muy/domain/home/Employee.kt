package com.muy.domain.home

import com.muy.domain.utils.EMPTY_BOOLEAN
import com.muy.domain.utils.EMPTY_INT
import com.muy.domain.utils.EMPTY_STRING

data class Employee(
    val id: Int = EMPTY_INT,
    val fullName: String = EMPTY_STRING,
    val position: String = EMPTY_STRING,
    val salary: String = EMPTY_STRING,
    val phone: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val upperRelation: Int = EMPTY_INT,
    val isNew: Boolean = EMPTY_BOOLEAN
)