package com.example.muy.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muy.R
import com.example.muy.databinding.WorkerItemBinding
import com.example.muy.utils.bindingInflate
import com.muy.domain.home.Employee
import kotlin.properties.Delegates

class WorkersAdapter(private val listener: (Employee) -> Unit) : RecyclerView.Adapter<WorkersAdapter.ViewHolder>() {

    var workers : List<Employee> by Delegates.observable(
        emptyList(),
        {_, _, _ -> notifyDataSetChanged()}
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.worker_item, false))


    override fun getItemCount(): Int = workers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = workers[position]
        holder.dataBinding.employee = employee
        holder.itemView.setOnClickListener { listener(employee) }
    }

    class ViewHolder(val dataBinding: WorkerItemBinding) : RecyclerView.ViewHolder(dataBinding.root)
}