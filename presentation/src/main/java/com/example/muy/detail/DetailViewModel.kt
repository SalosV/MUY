package com.example.muy.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.muy.utils.ScopedViewModel
import com.muy.domain.home.Employee
import com.muy.domain.home.WorkersUseCase
import com.muy.domain.utils.UPDATE_DATA
import kotlinx.coroutines.launch

class DetailViewModel(private val idEmployee: Int, private val useCase: WorkersUseCase) :
    ScopedViewModel() {

    private val _findEmployee = MutableLiveData<Employee>()
    val findEmployee: LiveData<Employee> get() = _findEmployee

    private val _dependents = MutableLiveData<ArrayList<String>>()
    val dependents: LiveData<ArrayList<String>> get() = _dependents

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> get() = _message

    private lateinit var workersData: List<Employee>
    private lateinit var employee: Employee

    init {
        launch {
            employee = useCase.findEmployee(idEmployee)
            workersData = useCase.getAllWorkers()

            val dependentsList = getDependents(employee.upperRelation)

            _findEmployee.value = employee
            _dependents.value = dependentsList
        }
    }

    private fun getDependents(upperRelation: Int): ArrayList<String> {
        val persons: ArrayList<String> = arrayListOf()

        for (employee in workersData) {
            if (employee.id != idEmployee && upperRelation < employee.upperRelation) persons.add(
                employee.fullName
            )
        }

        return persons
    }

    fun saveStatus(statusIsNew: Boolean) {
        launch {
            useCase.updateFieldIsNew(statusIsNew, idEmployee)
            _message.value = UPDATE_DATA
        }
    }
}