package com.example.muy.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muy.R
import com.example.muy.databinding.DependentItemBinding
import com.example.muy.utils.bindingInflate
import kotlin.properties.Delegates

class DependentsAdapter : RecyclerView.Adapter<DependentsAdapter.ViewHolder>() {

    var dependents : ArrayList<String> by Delegates.observable(
        arrayListOf(),
        {_, _, _ -> notifyDataSetChanged()}
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.dependent_item, false))

    override fun getItemCount(): Int = dependents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = dependents[position]
        holder.dataBinding.nameText.text = name
    }

    class ViewHolder(val dataBinding: DependentItemBinding): RecyclerView.ViewHolder(dataBinding.root) {

    }
}