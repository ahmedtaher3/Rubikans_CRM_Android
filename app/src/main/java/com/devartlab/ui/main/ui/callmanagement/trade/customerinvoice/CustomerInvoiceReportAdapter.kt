package com.devartlab.ui.main.ui.callmanagement.trade.customerinvoice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.databinding.EmployeeInvoiceItemBinding
import com.devartlab.model.CustomerInvoiceDetails
import java.util.*

class CustomerInvoiceReportAdapter(context: Context, private var myData: ArrayList<CustomerInvoiceDetails>) : RecyclerView.Adapter<CustomerInvoiceReportAdapter.ViewHolder>() {


    fun setMyData(data: ArrayList<CustomerInvoiceDetails>) {
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
        holder._binding?.date?.text = model.invoiceCreateDate.toString().take(10)
        holder._binding?.paid?.text = model.totalPaid.toString().take(10)
        holder._binding?.reminder?.text = model.totalReminder.toString().take(10)
        holder._binding?.netValue?.text = model.totalValue.toString().take(10)
        holder._binding?.image?.setImageResource(R.drawable.ic_payment)

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