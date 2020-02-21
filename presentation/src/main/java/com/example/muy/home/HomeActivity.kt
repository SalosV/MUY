package com.example.muy.home

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muy.detail.DetailEmployeeActivity
import com.example.muy.R
import com.example.muy.databinding.ActivityMainBinding
import com.example.muy.utils.getViewModel
import com.example.muy.utils.snackBar
import com.example.muy.utils.startActivity
import com.muy.data.DataApp
import com.muy.data.home.WorkersRepositoryImp
import com.muy.domain.home.WorkersUseCasesImpl
import com.muy.domain.utils.EMPTY_STRING

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: WorkersAdapter
    private lateinit var searchAction: MenuItem
    private lateinit var closeAction: MenuItem
    private lateinit var moneyFilterAction: MenuItem
    private var isOrderMoney = false
    private var isNewWorkers = false

    private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        configureViewModel()
        configureToolbar()
        configureRecyclerView()
        eventListenerSearch()

        viewModel.navigateToEmployee.observe(
            this,
            Observer { startActivity<DetailEmployeeActivity> {
                putExtra(DetailEmployeeActivity.EMPLOYEE, it)
            } })

        binding.filterNew.setOnClickListener {

            if (!isNewWorkers) {
                viewModel.filterWorkersNew()
                isNewWorkers = true
                binding.listOfWorkers.snackBar(getString(R.string.workers_new))
            } else {
                viewModel.allWorkers()
                isNewWorkers = false
                binding.listOfWorkers.snackBar(getString(R.string.workers_all))
            }
        }
    }

    private fun configureViewModel() {
        viewModel =
            getViewModel { HomeViewModel(WorkersUseCasesImpl(WorkersRepositoryImp(application as DataApp))) }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.title = getString(R.string.app_name)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)

        menu?.let {
            searchAction = menu.findItem(R.id.action_search)
            closeAction = menu.findItem(R.id.action_cancel)
            moneyFilterAction = menu.findItem(R.id.action_money)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_search -> {
                toolbar.title = getString(R.string.empty)
                binding.searchEdit.visibility = VISIBLE
                moneyFilterAction.isVisible = false
                searchAction.isVisible = false
                closeAction.isVisible = true
                showKeyboard(binding.searchEdit)
            }
            R.id.action_cancel -> {
                toolbar.title = getString(R.string.app_name)
                binding.searchEdit.visibility = GONE
                moneyFilterAction.isVisible = true
                searchAction.isVisible = true
                closeAction.isVisible = false
                binding.searchEdit.setText(EMPTY_STRING)
                hideSoftKeyboard(binding.searchEdit)
            }
            R.id.action_money -> {

                if (!isOrderMoney) {
                    moneyFilterAction.icon =
                        ContextCompat.getDrawable(this, R.drawable.ic_attach_money_white_24dp)
                    viewModel.orderSalary()
                    isOrderMoney = true
                } else {
                    moneyFilterAction.icon =
                        ContextCompat.getDrawable(this, R.drawable.ic_money_off_white_24dp)
                    viewModel.allWorkers()
                    isOrderMoney = false
                }

            }
        }

        return true
    }

    private fun configureRecyclerView() {
        adapter = WorkersAdapter(viewModel::onEmploeeClicked)
        binding.listOfWorkers.layoutManager = LinearLayoutManager(this)
        binding.listOfWorkers.adapter = adapter

        viewModel.workers.observe(this, Observer {
            adapter.workers = it
        })
    }

    private fun eventListenerSearch() {
        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val search = binding.searchEdit.text.toString()

                viewModel.searchWorkers(search.toLowerCase().trim())
            }
        })
    }

    fun showKeyboard(editText: EditText) {
        editText.requestFocus()
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun hideSoftKeyboard(editText: EditText) {
        editText.clearFocus()
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}

