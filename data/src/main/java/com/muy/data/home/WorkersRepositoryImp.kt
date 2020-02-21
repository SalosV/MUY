package com.muy.data.home

import com.muy.data.DataApp
import com.muy.data.Service.MuyServiceManager.service
import com.muy.data.utils.NameWorkers.*
import com.muy.domain.home.Employee
import com.muy.domain.home.WorkersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkersRepositoryImp(
    application: DataApp
) : WorkersRepository {

    private val database = application.database

    override suspend fun getAllWorkers(): List<Employee> = withContext(Dispatchers.IO) {

        with(database.getWorkersDao()) {

            if (workersCounts() <= 0) {
                val response = service.getWorkers()
                if (response.isSuccessful) {
                    response.body()?.run {
                        val workersList: ArrayList<WorkerEntity> = arrayListOf()

                        this.carlosVelasquez.fullName = CARLOSVELAZQUES.fullName
                        this.mateoRodriguez.fullName = MATEORODRIGUEZ.fullName
                        this.germanRamirez.fullName = GERMANRAMIREZ.fullName
                        this.ricardoZuniga.fullName = RICARDOZUNIGA.fullName
                        this.oscarZuluaga.fullName = OSCARZUALUAGA.fullName
                        this.gerardoAlvarez.fullName = GERARDOALVAREZ.fullName
                        this.miguelCarvajal.fullName = MIGUELCARVAJAL.fullName
                        this.carlosBeltran.fullName = CARLOSBELTRAN.fullName
                        this.diegoBermeo.fullName = DIEGOBERMEO.fullName
                        this.fernandoVargas.fullName = FERNANDOVARGAS.fullName

                        workersList.add(this.carlosVelasquez.toDatabaseEntity())
                        workersList.add(this.mateoRodriguez.toDatabaseEntity())
                        workersList.add(this.germanRamirez.toDatabaseEntity())
                        workersList.add(this.ricardoZuniga.toDatabaseEntity())
                        workersList.add(this.oscarZuluaga.toDatabaseEntity())
                        workersList.add(this.gerardoAlvarez.toDatabaseEntity())
                        workersList.add(this.miguelCarvajal.toDatabaseEntity())
                        workersList.add(this.carlosBeltran.toDatabaseEntity())
                        workersList.add(this.diegoBermeo.toDatabaseEntity())
                        workersList.add(this.fernandoVargas.toDatabaseEntity())

                        insertWorkers(workersList)
                    }
                }
            }

            return@withContext getAllWorkers().map { it.toDomainModel() }
        }

    }

    override suspend fun findEmployee(id: Int): Employee = withContext(Dispatchers.IO){
        with(database.getWorkersDao()) {
            return@withContext findEmployee(id).run { toDomainModel() }
        }
    }

    override suspend fun updateFieldIsNew(statusIsNew: Boolean, idEmployee: Int) = withContext(Dispatchers.IO) {
        with(database.getWorkersDao()) {
            return@withContext updateEmployee(statusIsNew, idEmployee)
        }
    }

    override suspend fun filterWorkersNew(): List<Employee> = withContext(Dispatchers.IO) {
        with(database.getWorkersDao()) {
            return@withContext filterWorkersNew(true).map { it.toDomainModel() }
        }
    }

    override suspend fun orderSalary(): List<Employee> = withContext(Dispatchers.IO) {
        with(database.getWorkersDao()) {
            return@withContext orderSalary().map { it.toDomainModel() }
        }
    }
}