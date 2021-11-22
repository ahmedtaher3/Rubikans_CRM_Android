package com.devartlab.ui.main.ui.callmanagement.trade.offlineinvoice

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.data.room.trademaster.TradeMasterEntity
import com.devartlab.databinding.EmployeeInvoiceItemBinding

class OfflineReportAdapter(context: Context, private var myData: MutableList<TradeMasterEntity>) : RecyclerView.Adapter<OfflineReportAdapter.ViewHolder>() {


    fun setMyData(data: List<TradeMasterEntity>) {
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


        holder._binding?.name?.text = model.name.toString()
        holder._binding?.specialistAndClass?.text =model.specialty.toString() + " / " + model._class.toString()
        holder._binding?.discount?.text = model.TotalInvoiceDiscount.toString()
        holder._binding?.tax?.text = model.TotalInvoiceTax.toString()
        holder._binding?.totalValue?.text = model.TotalInvoiceNet.toString()
        holder._binding?.netValue?.text = model.TotalInvoiceNet.toString()
        holder._binding?.brick?.text = model.brick.toString()
        holder._binding?.date?.text = model.AddDateTime.toString().take(10)

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