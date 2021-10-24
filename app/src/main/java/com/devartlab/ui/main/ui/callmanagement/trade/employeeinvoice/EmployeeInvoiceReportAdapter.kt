package com.devartlab.ui.main.ui.callmanagement.trade.employeeinvoice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.databinding.EmployeeInvoiceItemBinding
import com.devartlab.model.EMPloyeeStoreInvoice
import java.util.*

class EmployeeInvoiceReportAdapter(context: Context, private var myData: ArrayList<EMPloyeeStoreInvoice>) : RecyclerView.Adapter<EmployeeInvoiceReportAdapter.ViewHolder>() {


    fun setMyData(data: ArrayList<EMPloyeeStoreInvoice>) {
        myData.clear()
        myData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                EmployeeInvoiceItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val model = myData[position]


        holder._binding?.name?.text = model.customerName.toString()
        holder._binding?.specialistAndClass?.text = model.speciality.toString() + " / " + model._class.toString()
        holder._binding?.discount?.text = model.discount.toString()
        holder._binding?.tax?.text = model.taxValue.toString()
        holder._binding?.totalValue?.text = model.totalValue.toString()
        holder._binding?.brick?.text = model.brickEnName.toString()

    }

    override fun getItemCount(): Int {
        return myData.size
    }


    inner class ViewHolder(var binding: EmployeeInvoiceItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        var _binding: EmployeeInvoiceItemBinding? = null

        init {
            this._binding = binding;
        }

    }


}