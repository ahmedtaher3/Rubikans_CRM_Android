package com.devartlab.ui.trade

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.data.retrofit.INvnetoryMasterDatum
import com.devartlab.databinding.CallManagmentHomeItemBinding
import com.devartlab.databinding.InventoryLoadCarItemBinding
import com.devartlab.model.Summary

class InventoryLoadCarAdaptor(private val context: Context, private var myData: ArrayList<INvnetoryMasterDatum>, private var onInventoryTypeClick: OnInventoryTypeClick,
                              private val onCustomerSelect: OnTypeSelect, private val st: String) : RecyclerView.Adapter<InventoryLoadCarAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(InventoryLoadCarItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
        ))

    }

    interface OnInventoryTypeClick {

        fun onInventoryTypeClick(model: INvnetoryMasterDatum)
    }

    interface OnTypeSelect {
        fun setOnTypeSelect(model: Summary)

    }


    fun setMyData(myData: ArrayList<INvnetoryMasterDatum>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<INvnetoryMasterDatum> {
        return this.myData
    }

    fun addItem(model: INvnetoryMasterDatum) {
        this.myData.add(model)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]

        holder._binding?.serial?.text = model.trxSerial.toString()
        holder._binding?.storeName?.text = model.storName.toString()
        holder._binding?.date?.text = model.trxdate.toString()
        holder._binding?.moveName?.text = model.trxTypeDescription.toString()


        holder._binding?.checkBox?.isChecked = model.selected

        holder._binding?.checkBox?.setOnClickListener(View.OnClickListener {

            model.selected = holder._binding?.checkBox?.isChecked!!

        })



        holder.itemView.setOnClickListener {
            onInventoryTypeClick.onInventoryTypeClick(model)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var binding: InventoryLoadCarItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        var _binding: InventoryLoadCarItemBinding? = null

        init {
            this._binding = binding;


        }

    }
}

