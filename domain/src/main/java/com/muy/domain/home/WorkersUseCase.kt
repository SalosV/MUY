package com.muy.domain.home

interface WorkersUseCase {
    suspend fun getAllWorkers(): List<Employee>
    suspend fun findEmployee(id: Int): Employee
    suspend fun updateFieldIsNew(statusIsNew: Boolean, idEmployee: Int)
    suspend fun filterWorkersNew(): List<Employee>
    suspend fun orderSalary(): List<Employee>
}