package com.devartlab.ui.main.ui.callmanagement.trade.customerinvoice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.databinding.CustomerCollectCashBinding
import com.devartlab.databinding.EmployeeInvoiceItemBinding
import com.devartlab.model.CustomerInvoiceDetails
import java.util.*

class CustomerInvoiceCollectReportAdapter(private var context: Context, private var myData: ArrayList<CustomerInvoiceDetails>, private var onPayClick: OnPayClick) : RecyclerView.Adapter<CustomerInvoiceCollectReportAdapter.ViewHolder>() {


    fun setMyData(data: ArrayList<CustomerInvoiceDetails>) {
        myData.clear()
        myData.addAll(data)
        notifyDataSetChanged()
    }

    interface OnPayClick {
        fun setOnPayClick(model: CustomerInvoiceDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(

                CustomerCollectCashBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val model = myData[position]


        holder._binding?.date?.text = model.invoiceCreateDate.toString().take(10)
        holder._binding?.code?.text = model.invoiceId.toString()
        holder._binding?.totalValue?.text = model.totalValue.toString()

        holder._binding?.value?.text = model.totalReminder.toString() + "  " + context.getString(R.string.reminder)

        holder._binding?.pay?.setOnClickListener {
            onPayClick.setOnPayClick(model)
        }

    }

    override fun getItemCount(): Int {
        return myData.size
    }


    inner class ViewHolder(var binding: CustomerCollectCashBinding) :
            RecyclerView.ViewHolder(binding.root) {

        var _binding: CustomerCollectCashBinding? = null

        init {
            this._binding = binding;
        }

    }


}