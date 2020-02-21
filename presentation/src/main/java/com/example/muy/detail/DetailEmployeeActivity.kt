package com.example.muy.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muy.R
import com.example.muy.databinding.ActivityDetailEmployeeBinding
import com.example.muy.utils.getViewModel
import com.example.muy.utils.snackBar
import com.muy.data.DataApp
import com.muy.data.home.WorkersRepositoryImp
import com.muy.domain.home.Employee
import com.muy.domain.home.WorkersUseCasesImpl

class DetailEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEmployeeBinding
    private lateinit var adapter: DependentsAdapter
    private lateinit var viewModel: DetailViewModel

    companion object {
        const val EMPLOYEE = "DetailActivity:employee"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_employee)

        configureToolbar()
        configureViewModel()
        configureViewModel()
        configureRecyclerView()

        viewModel.findEmployee.observe(this, Observer { informationEmployee(it) })
        viewModel.message.observe(this, Observer { binding.listDependents.snackBar(it) })

        binding.bnSave.setOnClickListener {

            val isNew: Boolean = binding.isNewSpinner.selectedItemPosition != 0

            viewModel.saveStatus(isNew)
        }
    }

    private fun configureViewModel() {
        viewModel =
            getViewModel { DetailViewModel(
                intent.getIntExtra(EMPLOYEE, -1),
                WorkersUseCasesImpl(WorkersRepositoryImp(application as DataApp))
            ) }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun configureToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        binding.toolbar.title = getString(R.string.detail)
    }

    private fun configureRecyclerView() {
        adapter = DependentsAdapter()
        binding.listDependents.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.listDependents.adapter = adapter

        viewModel.dependents.observe(this, Observer {  adapter.dependents = it })

    }

    private fun informationEmployee(employee: Employee) {

        employee.run {
            binding.nameText.text = fullName
            binding.emailText.text = email
            binding.phoneText.text = phone
            binding.salaryText.text = salary
            binding.positionText.text = position

            binding.isNewSpinner.setSelection(if (isNew) 1 else 0)
        }
    }
}
