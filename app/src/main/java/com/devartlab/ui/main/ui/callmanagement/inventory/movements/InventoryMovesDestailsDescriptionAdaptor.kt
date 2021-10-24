package com.devartlab.ui.trade

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.INvnetoryTrxDetail
import com.devartlab.databinding.InventoryDetailsItemBinding
import com.devartlab.databinding.InventoryInventoryIteamBinding
import com.devartlab.databinding.InventoryLoadCarItemBinding
import com.devartlab.model.Summary

class InventoryMovesDestailsDescriptionAdaptor(private val context: Context, private var myData: ArrayList<INvnetoryTrxDetail>, private var onInventoryTypeClick: OnInventoryTypeClick) : RecyclerView.Adapter<InventoryMovesDestailsDescriptionAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            InventoryDetailsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ))

    }

    interface OnInventoryTypeClick {

        fun onInventoryTypeClick(model: INvnetoryTrxDetail)
    }

    interface OnTypeSelect {
        fun setOnTypeSelect(model: Summary)
    }

    fun setMyData(myData: ArrayList<INvnetoryTrxDetail>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<INvnetoryTrxDetail> {
        return this.myData
    }

    fun addItem(model: INvnetoryTrxDetail) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]


        holder._binding?.name?.text = model.itemArName.toString()
        holder._binding?.unit?.text = model.unitArName.toString()
        holder._binding?.count?.text = model.qty.toString()

        holder.itemView.setOnClickListener {

            onInventoryTypeClick.onInventoryTypeClick(model)

        }


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