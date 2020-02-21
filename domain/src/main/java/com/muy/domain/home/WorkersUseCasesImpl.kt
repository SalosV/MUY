package com.muy.domain.home

class WorkersUseCasesImpl(
    private val workerRepository: WorkersRepository
) : WorkersUseCase {
    override suspend fun getAllWorkers(): List<Employee> = workerRepository.getAllWorkers()
    override suspend fun findEmployee(id: Int): Employee = workerRepository.findEmployee(id)
    override suspend fun updateFieldIsNew(statusIsNew: Boolean, idEmployee: Int) =
        workerRepository.updateFieldIsNew(statusIsNew, idEmployee)

    override suspend fun filterWorkersNew(): List<Employee> = workerRepository.filterWorkersNew()
    override suspend fun orderSalary(): List<Employee> = workerRepository.orderSalary()
}