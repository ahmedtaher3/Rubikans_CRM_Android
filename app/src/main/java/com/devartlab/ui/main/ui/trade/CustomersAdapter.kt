package com.devartlab.ui.main.ui.trade

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devartlab.R
import com.devartlab.model.CustomerTrade


class CustomersAdapter(private val context: Context, private var myData: ArrayList<CustomerTrade>, private val onCustomerSelect: OnCustomerSelect) : RecyclerView.Adapter<CustomersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        view = LayoutInflater.from(context).inflate(R.layout.customer_trade_item, parent, false)
        return ViewHolder(view)
    }

    interface OnCustomerSelect{
        fun setOnCustomerSelect(model: CustomerTrade)
    }

    fun setMyData(myData: ArrayList<CustomerTrade>) {
        this.myData = myData
        notifyDataSetChanged()
    }

    fun getMyData(): ArrayList<CustomerTrade> {
        return this.myData
    }

    fun addItem(model: CustomerTrade) {
        this.myData.add(model)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = myData[position]



        holder.name?.text = model.name
        holder.credit?.text = model.credit.toString()

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
        var credit: TextView? = null


        init {
            credit = view.findViewById(R.id.credit)
            name = view.findViewById(R.id.name)


        }


    }


}