package com.muy.data.home

import com.google.gson.GsonBuilder
import com.muy.data.DataApp
import com.muy.data.Service.MuyServiceManager.service
import com.muy.domain.home.Employee
import com.muy.domain.home.WorkersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

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
                        val gsonBuilder = GsonBuilder()
                        gsonBuilder.serializeNulls()

                        val gson = gsonBuilder.create()
                        val information: String = gson.toJson(this)

                        val json = JSONObject(information)
                        val iterator = json.keys()

                        while (iterator.hasNext()) {
                            val key = iterator.next() as String
                            val data = json.getJSONObject(key)

                            val employee = WorkerEntity(
                                id = data.getInt("id"),
                                fullName =  key,
                                position = data.getString("position"),
                                salary = data.getInt("salary"),
                                phone = data.getString("phone"),
                                email = data.getString("email"),
                                upperRelation = data.getInt("upperRelation")
                            )

                            insertWorkers(employee)
                        }
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