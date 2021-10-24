package com.devartlab.ui.main.ui.callmanagement.inventory.balance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.VanStoctaking
import com.devartlab.databinding.InventoryDetailsItemBinding
import com.devartlab.model.Summary

class InventoryBalanceAdaptor(private val context: Context, private var myData: ArrayList<VanStoctaking>) : RecyclerView.Adapter<InventoryBalanceAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            InventoryDetailsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ))
    }

    interface OnInventoryTypeClick {

        fun onInventoryTypeClick(model: VanStoctaking)
    }

    interface OnTypeSelect{
        fun setOnTypeSelect(model: Summary)
    }

    fun setMyData(myData: ArrayList<VanStoctaking>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<VanStoctaking> {
        return this.myData
    }

    fun addItem(model: VanStoctaking) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]

        holder._binding?.name?.text = model.itemArName.toString()
        holder._binding?.unit?.text = model.unitArName.toString()
        holder._binding?.count?.text = model.qty.toString()

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: InventoryDetailsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var _binding: InventoryDetailsItemBinding? = null

        init {
            this._binding = binding;



        }


    }



}