package com.example.muy.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.muy.utils.ScopedViewModel
import com.muy.data.utils.SearchAlgorithm
import com.muy.domain.home.Employee
import com.muy.domain.home.WorkersUseCase
import kotlinx.coroutines.launch
import java.util.*


class HomeViewModel(private val useCase: WorkersUseCase) : ScopedViewModel() {

    private lateinit var workersData: List<Employee>

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _workers = MutableLiveData<List<Employee>>()
    val workers: LiveData<List<Employee>> get() = _workers

    private val _navigateToEmployee = MutableLiveData<Int>()
    val navigateToEmployee: LiveData<Int> get() = _navigateToEmployee

    init {
        launch {
            _loading.value = true
            workersData = useCase.getAllWorkers()
            _workers.value = workersData
            _loading.value = false
        }
    }

    fun searchWorkers(query: String) {
        val workersFilter = arrayListOf<Employee>()

        if (query.isNotEmpty()) {

            for (employee in workersData) {
                employee.run {
                    if (SearchAlgorithm().search(fullName.toLowerCase(), query)) {
                        workersFilter.add(employee)
                    }
                }
            }
        } else {
            workersFilter.addAll(workersData)
        }

        _workers.value = workersFilter
    }

    fun onEmploeeClicked(employee: Employee) {
        _navigateToEmployee.value = employee.id
    }

    fun orderSalary() {
        launch {
            _workers.value = useCase.orderSalary()
        }
    }

    fun allWorkers() {
        _workers.value = workersData
    }

    fun filterWorkersNew() {
        launch {
            _workers.value = useCase.filterWorkersNew()
        }
    }

}