package com.devartlab.ui.main.ui.trade.report

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.CustomerAcher
import com.devartlab.model.CustomerTrade
import com.devartlab.model.TradeDay


class ReportCustomersAdapter(private val context: Context, private var myData: ArrayList<CustomerAcher>, private val onCustomerSelect: OnCustomerReportSelect) : RecyclerView.Adapter<ReportCustomersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.customer_report_item, parent, false)
        return ViewHolder(view)
    }

    interface OnCustomerReportSelect {
        fun setOnCustomerSelect(model: CustomerAcher)
    }

    fun setMyData(myData: ArrayList<CustomerAcher>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<CustomerAcher> {
        return this.myData
    }

    fun addItem(model: CustomerAcher) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder.name?.text = model.name

        holder.itemView.setOnClickListener {
            onCustomerSelect.setOnCustomerSelect(model)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return myData.size
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        // each data item is just a string in this case
        var name: TextView? = null


        init {

            name = view.findViewById(R.id.name)


        }


    }


}


